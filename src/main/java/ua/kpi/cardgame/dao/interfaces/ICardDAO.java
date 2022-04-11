package ua.kpi.cardgame.dao.interfaces;

import ua.kpi.cardgame.entities.Card;
import ua.kpi.cardgame.entities.CardType;

import java.sql.SQLException;
import java.util.List;

public interface ICardDAO {
    Card getCardById(int id) throws SQLException;
    Card createCard(CardType type, String resource) throws SQLException;
    void deleteCardById(int id) throws SQLException;
    boolean updateCardResource(Card card, String resource) throws SQLException;
    List<Card> getAllCards() throws SQLException;
    List<Card> getAllCardsByType(CardType type) throws SQLException;
}
