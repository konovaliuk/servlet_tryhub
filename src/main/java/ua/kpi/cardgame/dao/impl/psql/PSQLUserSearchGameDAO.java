package ua.kpi.cardgame.dao.impl.psql;

import ua.kpi.cardgame.dao.interfaces.UserSearchGameDAO;
import ua.kpi.cardgame.entities.UserSearchGame;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PSQLUserSearchGameDAO implements UserSearchGameDAO {
    private final PSQLController controller;
    private static final String SELECT = "SELECT * FROM cardGame.users_search_game";
    private static final String INSERT = "INSERT INTO cardGame.users_search_game (user_id) VALUES (?)";
    private static final String DELETE = "DELETE FROM cardGame.users_search_game WHERE user_id = ?";

    public PSQLUserSearchGameDAO() {
        controller = PSQLController.getInstance();
    }

    @Override
    public void startUserSearchGame(int userId) throws SQLException {
        stopUserSearchGame(userId);
        PreparedStatement ps = controller.getPreparedStatement(INSERT);

        ps.setInt(1, userId);
        ps.executeUpdate();

        ps.close();
    }

    @Override
    public void stopUserSearchGame(int userId) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(DELETE);

        ps.setInt(1, userId);
        ps.executeUpdate();

        ps.close();
    }

    @Override
    public List<UserSearchGame> getAllUserSearchGame() throws SQLException {
        List<UserSearchGame> usersSearchGame = new ArrayList<>();
        PreparedStatement ps = controller.getPreparedStatement(SELECT);
        ResultSet rs;

        rs = ps.executeQuery();

        while (rs.next()) {
            usersSearchGame.add(new UserSearchGame(rs.getInt(1), rs.getTimestamp(2)));
        }

        rs.close();
        ps.close();

        return usersSearchGame;
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
