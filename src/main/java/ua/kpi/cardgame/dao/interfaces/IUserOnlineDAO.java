package ua.kpi.cardgame.dao.interfaces;

import ua.kpi.cardgame.entities.UserOnlineStatus;

import java.util.List;

public interface IUserOnlineDAO {
    void setUserOnline(int userId);
    void setUserOffline(int userId);
    List<UserOnlineStatus> getAllOnlineUsers();
}