package ua.kpi.cardgame.dao.interfaces;

import ua.kpi.cardgame.entities.UserOnlineStatus;

import java.sql.SQLException;
import java.util.List;

public interface IUserOnlineDAO {
    void setUserOnline(int userId) throws SQLException;
    void setUserOffline(int userId) throws SQLException;
    List<UserOnlineStatus> getAllOnlineUsers() throws SQLException;
}