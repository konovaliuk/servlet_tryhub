package ua.kpi.cardgame.dao.impl.jpa;

import ua.kpi.cardgame.dao.interfaces.UserGameSessionDAO;
import ua.kpi.cardgame.entities.UserGameSession;
import ua.kpi.cardgame.entities.jpa.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class JPAUserGameSession implements UserGameSessionDAO {
    private final EntityManager entityManager;

    public JPAUserGameSession(EntityManager entityManager) {
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
    public void setUserGameSession(int userId, int sessionId) throws SQLException {
        User user = entityManager.find(User.class, userId);
        GameSession gameSession = entityManager.find(GameSession.class, sessionId);
        ua.kpi.cardgame.entities.jpa.UserGameSession userGameSession = new ua.kpi.cardgame.entities.jpa.UserGameSession();

        userGameSession.setUser(user);
        userGameSession.setGameSession(gameSession);

        startTransaction();
        entityManager.persist(userGameSession);
        commitTransaction();
    }

    @Override
    public void deleteUserGameSession(int userId) throws SQLException {
        User user = entityManager.find(User.class, userId);
        ua.kpi.cardgame.entities.jpa.UserGameSession userGameSession = entityManager.find(
            ua.kpi.cardgame.entities.jpa.UserGameSession.class, user
        );

        startTransaction();
        entityManager.remove(userGameSession);
        commitTransaction();
    }

    @Override
    public int updateUserSessionChoice(UserGameSession userGameSession, int choice) throws SQLException {
        try {
            GameSession gameSession = entityManager.find(GameSession.class, userGameSession.getSessionId());
            User user = entityManager.find(User.class, userGameSession.getUserId());
            Card card = entityManager.find(Card.class, choice);
            ua.kpi.cardgame.entities.jpa.UserGameSession entity = entityManager.find(
                ua.kpi.cardgame.entities.jpa.UserGameSession.class, new UserGameSessionKey(gameSession, user)
            );

            entity.setUserChoice(card);
            startTransaction();
            entityManager.merge(entity);
            commitTransaction();

            userGameSession.setUserChoice(choice);
        }catch (Exception e) {
            e.printStackTrace();
        }
    return choice;
    }

    @Override
    public void deleteAllBySessionId(int sessionId) throws SQLException {
        GameSession gameSession = entityManager.find(GameSession.class, sessionId);
        TypedQuery<ua.kpi.cardgame.entities.jpa.UserGameSession> query = entityManager.createNamedQuery(
                "UserGameSession.findAllBySession", ua.kpi.cardgame.entities.jpa.UserGameSession.class
        ).setParameter("session", gameSession);
        List<ua.kpi.cardgame.entities.jpa.UserGameSession> results = query.getResultList();

        startTransaction();
        for (ua.kpi.cardgame.entities.jpa.UserGameSession userGameSession : results) {
            entityManager.remove(userGameSession);
        }
        commitTransaction();
    }

    @Override
    public List<UserGameSession> getUsersBySessionId(int sessionId) throws SQLException {
        GameSession gameSession = entityManager.find(GameSession.class, sessionId);
        TypedQuery<ua.kpi.cardgame.entities.jpa.UserGameSession> query = entityManager.createNamedQuery(
                "UserGameSession.findAllBySession", ua.kpi.cardgame.entities.jpa.UserGameSession.class
        ).setParameter("session", gameSession);
        List<ua.kpi.cardgame.entities.jpa.UserGameSession> results = query.getResultList();

        return results.stream().map((userGameSession) -> {
            return new ua.kpi.cardgame.entities.UserGameSession(
                userGameSession.getGameSession().getSessionId(), userGameSession.getUser().getUserId(),
                userGameSession.getUserChoice() == null? 0 : userGameSession.getUserChoice().getCardId()
            );
        }).toList();
    }
}
