package ua.kpi.cardgame.dao.impl;

import ua.kpi.cardgame.dao.interfaces.GameSessionDAO;
import ua.kpi.cardgame.entities.GameSession;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PSQLGameSessionDAO implements GameSessionDAO {
    private final PSQLController controller;
    private static final String SELECT = "SELECT * FROM cardGame.game_sessions";
    private static final String INSERT = "INSERT INTO cardGame.game_sessions (stage, leader_id, condition_id, event_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_STAGE= "UPDATE cardGame.game_sessions SET stage = ? WHERE session_id = ?";
    private static final String UPDATE_LEADER = "UPDATE cardGame.game_sessions SET leader_id = ? WHERE session_id = ?";
    private static final String UPDATE_CONDITION = "UPDATE cardGame.game_sessions SET condition_id = ? WHERE session_id = ?";
    private static final String UPDATE_EVENT = "UPDATE cardGame.game_sessions SET event_id = ? WHERE session_id = ?";
    private static final String DELETE = "DELETE FROM cardGame.game_sessions WHERE session_id = ?";

    public PSQLGameSessionDAO() {
        controller = PSQLController.getInstance();
    }

    @Override
    public GameSession createSession(int stage, int leader_id, int condition_id, int event_id) throws SQLException {
        GameSession gameSession = null;
        PreparedStatement ps = controller.getPreparedStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, stage);
        ps.setInt(2, leader_id);
        ps.setInt(3, condition_id);
        ps.setInt(4, event_id);
        int rowsAffected = ps.executeUpdate();

        if (rowsAffected == 1) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                gameSession = new GameSession(
                        rs.getInt(1), stage, leader_id,
                        condition_id, rs.getTimestamp(5), event_id);
            }
            rs.close();
        }

        ps.close();

        return gameSession;
    }

    @Override
    public GameSession getSessionById(int id) throws SQLException {
        GameSession gameSession = null;
        PreparedStatement ps = controller.getPreparedStatement(SELECT + " WHERE session_id = ?");

        ps.setInt(1, id);
        ResultSet gameSessions = ps.executeQuery();

        if (gameSessions.next()) {
            gameSession = new GameSession(
                    gameSessions.getInt(1), gameSessions.getInt(2), gameSessions.getInt(3),
                    gameSessions.getInt(4), gameSessions.getTimestamp(5), gameSessions.getInt(6));
        }

        gameSessions.close();
        ps.close();

        return gameSession;
    }

    @Override
    public void deleteSessionById(int id) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(DELETE);

        ps.setInt(1, id);
        ps.executeUpdate();

        ps.close();
    }

    @Override
    public int updateStage(GameSession session, int stage) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(UPDATE_STAGE);

        ps.setInt(1, stage);
        ps.setInt(2, session.getSessionId());

        if (ps.executeUpdate() == 1) {
            session.setStage(stage);
        }

        ps.close();

        return stage;
    }

    @Override
    public int updateLeader(GameSession session, int leaderId) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(UPDATE_LEADER);

        ps.setInt(1, leaderId);
        ps.setInt(2, session.getSessionId());

        if (ps.executeUpdate() == 1) {
            session.setLeaderId(leaderId);
        }

        ps.close();

        return leaderId;
    }

    @Override
    public int updateCondition(GameSession session, int conditionId) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(UPDATE_CONDITION);

        ps.setInt(1, conditionId);
        ps.setInt(2, session.getSessionId());

        if (ps.executeUpdate() == 1) {
            session.setConditionId(conditionId);
        }

        ps.close();

        return conditionId;
    }

    @Override
    public int updateEvent(GameSession session, int eventId) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(UPDATE_EVENT);

        ps.setInt(1, eventId);
        ps.setInt(2, session.getSessionId());

        if (ps.executeUpdate() == 1) {
            session.setEventId(eventId);
        }

        ps.close();

        return eventId;
    }

    @Override
    public List<GameSession> getAllSessions() throws SQLException {
        List<GameSession> gameSessions = new ArrayList<>();
        PreparedStatement ps = controller.getPreparedStatement(SELECT);
        ResultSet rs;

        rs = ps.executeQuery();

        while (rs.next()) {
            gameSessions.add(new GameSession(
                    rs.getInt(1), rs.getInt(2), rs.getInt(3),
                    rs.getInt(4), rs.getTimestamp(5), rs.getInt(6)));
            }

        rs.close();
        ps.close();

        return gameSessions;
    }
}
