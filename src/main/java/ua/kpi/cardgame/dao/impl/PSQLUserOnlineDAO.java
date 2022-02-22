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
    private static final String SELECT = "SELECT * FROM cardGame.users_online";
    private static final String INSERT = "INSERT INTO cardGame.users_online (user_id) VALUES (?)";
    private static final String DELETE = "DELETE FROM cardGame.users_online WHERE user_id = ?";

    public PSQLUserOnlineDAO() {
        controller = new PSQLController();
    }

    private void updateUserOnlineStatus(String action, int userId) {
        PreparedStatement ps = controller.getPreparedStatement(action);

        try {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            controller.closePreparedStatement(ps);
        }
    }

    @Override
    public void setUserOnline(int userId) {
        updateUserOnlineStatus(INSERT, userId);
    }

    @Override
    public void setUserOffline(int userId) {
        updateUserOnlineStatus(DELETE, userId);
    }

    @Override
    public List<UserOnlineStatus> getAllOnlineUsers() {
        List<UserOnlineStatus> userOnlineStatuses = new ArrayList<>();
        PreparedStatement ps = controller.getPreparedStatement(SELECT);
        ResultSet rs = null;

        try {
            rs = ps.executeQuery();

            while (rs.next()) {
                userOnlineStatuses.add(new UserOnlineStatus(rs.getInt(1), rs.getTimestamp(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            controller.closeResultSet(rs);
            controller.closePreparedStatement(ps);
        }

        return userOnlineStatuses;
    }
}
