package ua.kpi.cardgame.dao.impl;

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
    private static final String SELECT = "SELECT * FROM \"card-game\".users";
    private static final String INSERT = "INSERT INTO \"card-game\".users (login, password) VALUES (?, ?)";
    private static final String UPDATE_RATE = "UPDATE \"card-game\".users SET rate = ? WHERE user_id = ?";
    private static final String DELETE = "DELETE FROM \"card-game\".users WHERE user_id = ?";

    public PSQLUserDAO() {
        controller = new PSQLController();
    }

    @Override
    public User getUserById(int id) {
        User user = null;
        PreparedStatement ps = controller.getPreparedStatement(SELECT + " WHERE user_id = ?");

        try {
            ps.setInt(1, id);
            ResultSet users = ps.executeQuery();

            if (users.next()) {
                user = new User(
                        users.getInt(1), users.getString(2),
                        users.getString(3), users.getInt(4)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            controller.closePreparedStatement(ps);
        }

        return user;
    }

    @Override
    public User createUser(String login, String password) {
        User user = null;
        PreparedStatement ps = controller.getPreparedStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

        try {
            ps.setString(1, login);
            ps.setString(2, password);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    user = new User(rs.getInt(1), login, password, 0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public void deleteUserById(int id) {
        PreparedStatement ps = controller.getPreparedStatement(DELETE);

        try {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateUserRate(User user, int rate) {
        PreparedStatement ps = controller.getPreparedStatement(UPDATE_RATE);

        try {
            ps.setInt(1, rate);
            ps.setInt(2, user.getUserId());

            if (ps.executeUpdate() == 1) {
                user.setRate(rate);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList();
        PreparedStatement ps = controller.getPreparedStatement(SELECT);

        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new User(
                        rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public List<User> getUsersWithRateBetween(int from, int to) {
        List<User> users = new ArrayList();
        PreparedStatement ps = controller.getPreparedStatement(SELECT+" WHERE rate BETWEEN "+from+" and "+to);

        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new User(
                        rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
