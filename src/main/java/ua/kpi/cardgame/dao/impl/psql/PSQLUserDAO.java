package ua.kpi.cardgame.dao.impl.psql;

import ua.kpi.cardgame.dao.interfaces.IUserDAO;
import ua.kpi.cardgame.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PSQLUserDAO implements IUserDAO {
    private final PSQLController controller;

    private static final String COLUMN_ID = "user_id";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_RATE = "rate";

    private static final String SELECT = "SELECT * FROM cardGame.users";
    private static final String INSERT = "INSERT INTO cardGame.users (login, password) VALUES (?, ?)";
    private static final String UPDATE_RATE = "UPDATE cardGame.users SET rate = ? WHERE user_id = ?";
    private static final String UPDATE_PASSWORD = "UPDATE cardGame.users SET password = ? WHERE user_id = ?";
    private static final String DELETE = "DELETE FROM cardGame.users WHERE user_id = ?";

    public PSQLUserDAO() {
        controller = PSQLController.getInstance();
    }

    @Override
    public User getUserById(int id) throws SQLException {
        User user = null;

        PreparedStatement ps = controller.getPreparedStatement(SELECT + " WHERE user_id = ?");
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            user = new User(
                rs.getInt(COLUMN_ID), rs.getString(COLUMN_LOGIN),
                rs.getString(COLUMN_PASSWORD), rs.getInt(COLUMN_RATE)
            );
        }

        rs.close();
        ps.close();

        return user;
    }

    @Override
    public User getUserByLogin(String login) throws SQLException {
        User user = null;

        PreparedStatement ps = controller.getPreparedStatement(SELECT + " WHERE login = ?");
        ps.setString(1, login);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            user = new User(
                rs.getInt(COLUMN_ID), rs.getString(COLUMN_LOGIN),
                rs.getString(COLUMN_PASSWORD), rs.getInt(COLUMN_RATE)
            );
        }

        rs.close();
        ps.close();

        return user;
    }

    @Override
    public User createUser(String login, String password) throws SQLException {
        User user = null;

        PreparedStatement ps = controller.getPreparedStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, login);
        ps.setString(2, password);

        if (ps.executeUpdate() == 1) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                user = new User(rs.getInt(COLUMN_ID), login, password, 0);
            }
            rs.close();
        }

        ps.close();

        return user;
    }

    @Override
    public void deleteUserById(int id) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(DELETE);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    private boolean updateUserField(String sql, User user, Object field) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(sql);
        ps.setObject(1, field);
        ps.setInt(2, user.getUserId());
        int rowsAffected = ps.executeUpdate();
        ps.close();

        if (rowsAffected == 1) {
            return true;
        }

        return false;
    }

    @Override
    public boolean updateUserRate(User user, int rate) throws SQLException {
        if (updateUserField(UPDATE_RATE, user, rate)) {
            user.setRate(rate);
            return true;
        }

        return false;
    }

    @Override
    public boolean updateUserPassword(User user, String password) throws SQLException {
        if (updateUserField(UPDATE_PASSWORD, user, password)) {
            user.setPassword(password);
            return true;
        }

        return false;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement ps = controller.getPreparedStatement(SELECT);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            users.add(new User(
                rs.getInt(COLUMN_ID), rs.getString(COLUMN_LOGIN),
                rs.getString(COLUMN_PASSWORD), rs.getInt(COLUMN_RATE)
            ));
        }

        rs.close();
        ps.close();

        return users;
    }

    @Override
    public List<User> getUsersWithRateBetween(int from, int to) throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement ps = controller.getPreparedStatement(SELECT + " WHERE rate BETWEEN " + from + " and " + to);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            users.add(new User(
                rs.getInt(COLUMN_ID), rs.getString(COLUMN_LOGIN),
                rs.getString(COLUMN_PASSWORD), rs.getInt(COLUMN_PASSWORD)
            ));
        }

        rs.close();
        ps.close();

        return users;
    }

    @Override
    public void rollbackTransaction() {
        controller.rollbackTransaction();
    }

    @Override
    public void commitTransaction() throws SQLException {
        controller.commitTransaction();
    }
}