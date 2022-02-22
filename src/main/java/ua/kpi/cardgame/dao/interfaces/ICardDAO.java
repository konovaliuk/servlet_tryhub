package ua.kpi.cardgame.dao.interfaces;

import ua.kpi.cardgame.entities.Card;
import ua.kpi.cardgame.entities.CardType;

import java.util.List;

public interface ICardDAO {
    Card getCardById(int id);
    Card createCard(CardType type, String resource);
    void deleteCardById(int id);
    boolean updateCardResource(Card card, String resource);
    List<Card> getAllCards();
    List<Card> getAllCardsByType(CardType type);
}
