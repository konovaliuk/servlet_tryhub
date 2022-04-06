package ua.kpi.cardgame.dao.interfaces;

import ua.kpi.cardgame.entities.UserCard;

import java.sql.SQLException;
import java.util.List;

public interface IUserCardsDAO {
    boolean addUserCards(List<UserCard> userCards) throws SQLException;
    boolean addUserCard(UserCard userCard) throws SQLException;
    List<UserCard> getUserCards(int sessionId, int userId) throws SQLException;
    void deleteAllUserCards(int sessionId, int userId) throws SQLException;
    void deleteUserCard(UserCard userCard) throws SQLException;
}
