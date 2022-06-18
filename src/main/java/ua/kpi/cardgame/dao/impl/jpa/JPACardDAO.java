package ua.kpi.cardgame.dao.impl.jpa;

import ua.kpi.cardgame.dao.interfaces.ICardDAO;
import ua.kpi.cardgame.entities.Card;
import ua.kpi.cardgame.entities.CardType;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class JPACardDAO implements ICardDAO {
    private final EntityManager entityManager;

    public JPACardDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void startTransaction() {
        EntityTransaction transaction = entityManager.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
    }

    @Override
    public void rollbackTransaction() {
        EntityTransaction transaction = entityManager.getTransaction();
        if (transaction.isActive()) {
            transaction.rollback();
        }
    }

    @Override
    public void commitTransaction() throws SQLException {
        EntityTransaction transaction = entityManager.getTransaction();
        if (transaction.isActive()) {
            transaction.commit();
        }
    }

    @Override
    public Card getCardById(int id) throws SQLException {
        ua.kpi.cardgame.entities.jpa.Card card = entityManager.find(ua.kpi.cardgame.entities.jpa.Card.class, id);

        if (card != null) {
            return new Card(card.getCardId(), CardType.valueOf(card.getType().toString()), card.getResource());
        }

        return null;
    }

    @Override
    public Card createCard(CardType type, String resource) throws SQLException {
        ua.kpi.cardgame.entities.jpa.Card card = new ua.kpi.cardgame.entities.jpa.Card();

        card.setType(ua.kpi.cardgame.entities.jpa.CardType.valueOf(type.toString().toUpperCase()));
        card.setResource(resource);

        startTransaction();
        entityManager.persist(card);
        commitTransaction();

        return new Card(card.getCardId(), CardType.valueOf(card.getType().toString()), card.getResource());
    }

    @Override
    public void deleteCardById(int id) throws SQLException {
        ua.kpi.cardgame.entities.jpa.Card card = entityManager.find(ua.kpi.cardgame.entities.jpa.Card.class, id);

        startTransaction();
        entityManager.remove(card);
        commitTransaction();
    }

    @Override
    public boolean updateCardResource(Card card, String resource) throws SQLException {
        ua.kpi.cardgame.entities.jpa.Card entity = entityManager.find(ua.kpi.cardgame.entities.jpa.Card.class, card.getCardId());

        entity.setResource(resource);
        try {
            startTransaction();
            entityManager.merge(entity);
            commitTransaction();
        } catch (Exception e) {
            return false;
        }

        card.setResource(resource);
        return true;
    }

    @Override
    public List<Card> getAllCards() throws SQLException {
        TypedQuery<ua.kpi.cardgame.entities.jpa.Card> query = entityManager.createNamedQuery(
            "Card.findAll", ua.kpi.cardgame.entities.jpa.Card.class
        );
        List<ua.kpi.cardgame.entities.jpa.Card> results = query.getResultList();
        return results.stream().map((card) -> {
            return new ua.kpi.cardgame.entities.Card(
                card.getCardId(), CardType.valueOf(card.getType().toString().toUpperCase()), card.getResource()
            );
        }).toList();
    }

    @Override
    public List<Card> getAllCardsByType(CardType type) throws SQLException {
        TypedQuery<ua.kpi.cardgame.entities.jpa.Card> query = entityManager.createNamedQuery(
                "Card.findAllByType", ua.kpi.cardgame.entities.jpa.Card.class
        ).setParameter("type", ua.kpi.cardgame.entities.jpa.CardType.valueOf(type.toString().toUpperCase()));
        List<ua.kpi.cardgame.entities.jpa.Card> results = query.getResultList();
        return results.stream().map((card) -> {
            return new Card(card.getCardId(), CardType.valueOf(card.getType().toString()), card.getResource());
        }).toList();
    }
}
