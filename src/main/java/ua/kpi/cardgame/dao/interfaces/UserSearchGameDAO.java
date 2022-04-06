package ua.kpi.cardgame.dao.interfaces;

import ua.kpi.cardgame.entities.UserSearchGame;

import java.sql.SQLException;
import java.util.List;

public interface UserSearchGameDAO {
    void startUserSearchGame(int userId) throws SQLException;
    void stopUserSearchGame(int userId) throws SQLException;
    List<UserSearchGame> getAllUserSearchGame() throws SQLException;
}
