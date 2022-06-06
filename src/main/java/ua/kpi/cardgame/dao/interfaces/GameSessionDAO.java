package ua.kpi.cardgame.dao.interfaces;

import ua.kpi.cardgame.entities.GameSession;

import java.sql.SQLException;
import java.util.List;

public interface GameSessionDAO extends DAO {
    GameSession createSession(int stage, int leader_id, int condition_id, int event_id) throws SQLException;
    GameSession getSessionById(int id) throws SQLException;
    void deleteSessionById(int id) throws SQLException;
    int updateStage(GameSession session, int stage) throws SQLException;
    int updateLeader(GameSession session, int leaderId) throws SQLException;
    int updateCondition(GameSession session, int conditionId) throws SQLException;
    int updateEvent(GameSession session, int eventId) throws SQLException;
    List<GameSession> getAllSessions() throws SQLException;
}
