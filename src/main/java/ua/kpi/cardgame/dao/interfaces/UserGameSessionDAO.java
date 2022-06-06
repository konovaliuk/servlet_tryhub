package ua.kpi.cardgame.dao.interfaces;

import ua.kpi.cardgame.entities.UserGameSession;

import java.sql.SQLException;
import java.util.List;

public interface UserGameSessionDAO extends DAO {
    void setUserGameSession(int userId, int sessionId) throws SQLException;
    void deleteUserGameSession(int userId) throws SQLException;
    int updateUserSessionChoice(UserGameSession userGameSession, int choice) throws SQLException;
    void deleteAllBySessionId(int sessionId) throws SQLException;
    List<UserGameSession> getUsersBySessionId(int sessionId) throws SQLException;
}
