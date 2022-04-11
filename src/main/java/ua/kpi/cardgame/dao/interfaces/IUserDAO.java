package ua.kpi.cardgame.dao.interfaces;

import ua.kpi.cardgame.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    User getUserById(int id) throws SQLException;
    User createUser(String login, String password) throws SQLException;
    void deleteUserById(int id) throws SQLException;
    boolean updateUserRate(User user, int rate) throws SQLException;
    boolean updateUserPassword(User user, String password) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    List<User> getUsersWithRateBetween(int from, int to) throws SQLException;
}