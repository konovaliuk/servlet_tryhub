package ua.kpi.cardgame.dao.impl;

import ua.kpi.cardgame.dao.interfaces.UserGameSessionDAO;
import ua.kpi.cardgame.entities.UserGameSession;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PSQLUserGameSessionDAO implements UserGameSessionDAO {
    private final PSQLController controller;
    private static final String SELECT = "SELECT * FROM cardGame.users_game_session";
    private static final String INSERT = "INSERT INTO cardGame.users_game_session (user_id, session_id) VALUES (?, ?)";
    private static final String UPDATE_CHOICE = "UPDATE cardGame.users_game_session SET user_choice = ? WHERE user_id = ?";
    private static final String DELETE_USER = "DELETE FROM cardGame.users_game_session WHERE user_id = ?";
    private static final String DELETE_SESSION = "DELETE FROM cardGame.users_game_session WHERE session_id = ?";

    public PSQLUserGameSessionDAO() {
        controller = PSQLController.getInstance();
    }

    @Override
    public void setUserGameSession(int userId, int sessionId) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(INSERT);

        ps.setInt(1, userId);
        ps.setInt(2, sessionId);
        ps.executeUpdate();

        ps.close();
    }

    @Override
    public void deleteUserGameSession(int userId) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(DELETE_USER);

        ps.setInt(1, userId);
        ps.executeUpdate();

        ps.close();
    }

    @Override
    public int updateUserSessionChoice(UserGameSession userGameSession, int choice) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(UPDATE_CHOICE);

        ps.setInt(1, choice);
        ps.setInt(2, userGameSession.getUserId());

        if (ps.executeUpdate() == 1) {
            userGameSession.setUserChoice(choice);
        }

        ps.close();

        return choice;
    }

    @Override
    public void deleteAllBySessionId(int sessionId) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(DELETE_SESSION);

        ps.setInt(1, sessionId);
        ps.executeUpdate();

        ps.close();
    }

    @Override
    public List<UserGameSession> getUsersBySessionId(int sessionId) throws SQLException {
        List<UserGameSession> userGameSessions = new ArrayList<>();
        PreparedStatement ps = controller.getPreparedStatement(SELECT + " WHERE session_id = ?");
        ps.setInt(1, sessionId);

        ResultSet rs;

        rs = ps.executeQuery();

        while (rs.next()) {
            userGameSessions.add(new UserGameSession(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
        }

        rs.close();
        ps.close();

        return userGameSessions;
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
