package ua.kpi.cardgame.dao.impl;

import ua.kpi.cardgame.dao.interfaces.IUserOnlineDAO;
import ua.kpi.cardgame.entities.UserOnlineStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PSQLUserOnlineDAO implements IUserOnlineDAO {
    private final PSQLController controller;

    private static final String COLUMN_ID = "user_id";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String SELECT = "SELECT * FROM cardGame.users_online";
    private static final String INSERT = "INSERT INTO cardGame.users_online (user_id) VALUES (?)";
    private static final String DELETE = "DELETE FROM cardGame.users_online WHERE user_id = ?";

    public PSQLUserOnlineDAO() {
        controller = PSQLController.getInstance();
    }

    private void updateUserOnlineStatus(String action, int userId) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(action);
        ps.setInt(1, userId);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void setUserOnline(int userId) throws SQLException {
        updateUserOnlineStatus(INSERT, userId);
    }

    @Override
    public void setUserOffline(int userId) throws SQLException {
        updateUserOnlineStatus(DELETE, userId);
    }

    @Override
    public List<UserOnlineStatus> getAllOnlineUsers() throws SQLException {
        List<UserOnlineStatus> userOnlineStatuses = new ArrayList<>();
        PreparedStatement ps = controller.getPreparedStatement(SELECT);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            userOnlineStatuses.add(new UserOnlineStatus(rs.getInt(COLUMN_ID), rs.getTimestamp(COLUMN_TIMESTAMP)));
        }

        rs.close();
        ps.close();

        return userOnlineStatuses;
    }
}