package ua.kpi.cardgame.dao.interfaces;

import ua.kpi.cardgame.entities.User;

import java.util.List;

public interface IUserDAO {
    User getUserById(int id);
    User createUser(String login, String password);
    void deleteUserById(int id);
    boolean updateUserRate(User user, int rate);
    boolean updateUserPassword(User user, String password);
    List<User> getAllUsers();
    List<User> getUsersWithRateBetween(int from, int to);
}