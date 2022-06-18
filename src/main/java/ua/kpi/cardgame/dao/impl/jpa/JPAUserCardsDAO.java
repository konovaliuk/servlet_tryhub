package ua.kpi.cardgame.dao.impl.jpa;

import ua.kpi.cardgame.dao.interfaces.IUserCardsDAO;
import ua.kpi.cardgame.entities.UserCard;
import ua.kpi.cardgame.entities.jpa.Card;
import ua.kpi.cardgame.entities.jpa.GameSession;
import ua.kpi.cardgame.entities.jpa.User;
import ua.kpi.cardgame.entities.jpa.UserCardKey;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class JPAUserCardsDAO implements IUserCardsDAO {
    private final EntityManager entityManager;

    public JPAUserCardsDAO(EntityManager entityManager) {
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
    public boolean addUserCards(List<UserCard> userCards) throws SQLException {
        for (UserCard userCard : userCards) {
            if (!addUserCard(userCard)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addUserCard(UserCard userCard) throws SQLException {
        GameSession gameSession = entityManager.find(GameSession.class, userCard.getSessionId());
        User user = entityManager.find(User.class, userCard.getUserId());
        Card card = entityManager.find(Card.class, userCard.getCardId());

        ua.kpi.cardgame.entities.jpa.UserCard entity = new ua.kpi.cardgame.entities.jpa.UserCard(
            gameSession, user, card
        );

        try {
            startTransaction();
            entityManager.persist(entity);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            return false;
        }

        return true;
    }

    @Override
    public List<UserCard> getUserCards(int sessionId, int userId) throws SQLException {
        User user = entityManager.find(User.class, userId);
        GameSession gameSession = entityManager.find(GameSession.class, sessionId);
        TypedQuery<ua.kpi.cardgame.entities.jpa.UserCard> query = entityManager.createNamedQuery(
                "UserCard.findAll", ua.kpi.cardgame.entities.jpa.UserCard.class
        ).setParameter("user", user).setParameter("session", gameSession);
        List<ua.kpi.cardgame.entities.jpa.UserCard> results = query.getResultList();

        return results.stream().map((card) -> {
            return new ua.kpi.cardgame.entities.UserCard(
                    card.getGameSession().getSessionId(), card.getUser().getUserId(), card.getCard().getCardId()
            );
        }).toList();
    }

    @Override
    public void deleteAllUserCards(int sessionId, int userId) throws SQLException {
        User user = entityManager.find(User.class, userId);
        GameSession gameSession = entityManager.find(GameSession.class, sessionId);
        TypedQuery<ua.kpi.cardgame.entities.jpa.UserCard> query = entityManager.createNamedQuery(
                "UserCard.findAll", ua.kpi.cardgame.entities.jpa.UserCard.class
        ).setParameter("user", user).setParameter("session", gameSession);
        List<ua.kpi.cardgame.entities.jpa.UserCard> results = query.getResultList();

        startTransaction();
        for (ua.kpi.cardgame.entities.jpa.UserCard userCard : results) {
            entityManager.remove(userCard);
        }
        commitTransaction();
    }

    @Override
    public void deleteUserCard(UserCard userCard) throws SQLException {
        User user = entityManager.find(User.class, userCard.getUserId());
        GameSession gameSession = entityManager.find(GameSession.class, userCard.getSessionId());
        Card card = entityManager.find(Card.class, userCard.getCardId());
        ua.kpi.cardgame.entities.jpa.UserCard entity = entityManager.find(
            ua.kpi.cardgame.entities.jpa.UserCard.class, new UserCardKey(gameSession, user, card)
        );

        startTransaction();
        entityManager.remove(entity);
        commitTransaction();
    }
}
