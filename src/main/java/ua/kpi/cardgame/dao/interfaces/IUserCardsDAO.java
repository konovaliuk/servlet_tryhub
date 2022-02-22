package ua.kpi.cardgame.dao.interfaces;

import ua.kpi.cardgame.entities.UserCard;

import java.util.List;

public interface IUserCardsDAO {
    boolean addUserCards(List<UserCard> userCards);
    boolean addUserCard(UserCard userCard);
    List<UserCard> getUserCards(int sessionId, int userId);
    void deleteAllUserCards(int sessionId, int userId);
    void deleteUserCard(UserCard userCard);
}
