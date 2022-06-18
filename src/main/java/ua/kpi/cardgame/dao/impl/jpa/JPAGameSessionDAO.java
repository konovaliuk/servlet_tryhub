package ua.kpi.cardgame.dao.impl.jpa;

import ua.kpi.cardgame.dao.interfaces.GameSessionDAO;
import ua.kpi.cardgame.entities.GameSession;
import ua.kpi.cardgame.entities.jpa.Card;
import ua.kpi.cardgame.entities.jpa.Event;
import ua.kpi.cardgame.entities.jpa.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class JPAGameSessionDAO implements GameSessionDAO {
    private final EntityManager entityManager;

    public JPAGameSessionDAO(EntityManager entityManager) {
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
    public GameSession createSession(int stage, int leader_id, int condition_id, int event_id) throws SQLException {
        User user = entityManager.find(User.class, leader_id);
        Card condition = entityManager.find(Card.class, condition_id);
        Event event = entityManager.find(Event.class, event_id);

        ua.kpi.cardgame.entities.jpa.GameSession gameSession = new ua.kpi.cardgame.entities.jpa.GameSession();
        gameSession.setStage(stage);
        gameSession.setLeader(user);
        gameSession.setCondition(condition);
        gameSession.setEvent(event);

        startTransaction();
        entityManager.persist(gameSession);
        commitTransaction();

        System.out.println(gameSession);

        return new GameSession(gameSession.getSessionId(), stage, leader_id, condition_id, gameSession.getEventStartTime(), event_id);
    }

    @Override
    public GameSession getSessionById(int id) throws SQLException {
        ua.kpi.cardgame.entities.jpa.GameSession gameSession = entityManager.find(
                ua.kpi.cardgame.entities.jpa.GameSession.class, id
        );

        if (gameSession != null) {
            return new GameSession(
                gameSession.getSessionId(), gameSession.getStage(), gameSession.getLeader().getUserId(),
                gameSession.getCondition().getCardId(), gameSession.getEventStartTime(), gameSession.getEvent().getEventId()
            );
        }

        return null;
    }

    @Override
    public void deleteSessionById(int id) throws SQLException {
        ua.kpi.cardgame.entities.jpa.GameSession gameSession = entityManager.find(
                ua.kpi.cardgame.entities.jpa.GameSession.class, id
        );

        startTransaction();
        entityManager.remove(gameSession);
        commitTransaction();
    }

    @Override
    public int updateStage(GameSession session, int stage) throws SQLException {
        ua.kpi.cardgame.entities.jpa.GameSession gameSession = entityManager.find(
            ua.kpi.cardgame.entities.jpa.GameSession.class, session.getSessionId()
        );

        gameSession.setStage(stage);
        startTransaction();
        entityManager.merge(gameSession);
        commitTransaction();

        session.setStage(stage);

        return stage;
    }

    @Override
    public int updateLeader(GameSession session, int leaderId) throws SQLException {
        ua.kpi.cardgame.entities.jpa.GameSession gameSession = entityManager.find(
            ua.kpi.cardgame.entities.jpa.GameSession.class, session.getSessionId()
        );
        User user = entityManager.find(User.class, leaderId);

        gameSession.setLeader(user);
        startTransaction();
        entityManager.merge(gameSession);
        commitTransaction();

        session.setLeaderId(leaderId);

        return leaderId;
    }

    @Override
    public int updateCondition(GameSession session, int conditionId) throws SQLException {
        ua.kpi.cardgame.entities.jpa.GameSession gameSession = entityManager.find(
                ua.kpi.cardgame.entities.jpa.GameSession.class, session.getSessionId()
        );
        Card condition = entityManager.find(Card.class, conditionId);

        gameSession.setCondition(condition);
        startTransaction();
        entityManager.merge(gameSession);
        commitTransaction();

        session.setConditionId(conditionId);

        return conditionId;
    }

    @Override
    public int updateEvent(GameSession session, int eventId) throws SQLException {
        ua.kpi.cardgame.entities.jpa.GameSession gameSession = entityManager.find(
                ua.kpi.cardgame.entities.jpa.GameSession.class, session.getSessionId()
        );
        Event event = entityManager.find(Event.class, eventId);

        gameSession.setEvent(event);
        gameSession.setEventStartTime(new Timestamp(new Date().getTime()));
        startTransaction();
        entityManager.merge(gameSession);
        commitTransaction();

        session.setEventId(eventId);
        session.setEventStartTime(new Timestamp(new Date().getTime()));

        return eventId;
    }

    @Override
    public List<GameSession> getAllSessions() throws SQLException {
        TypedQuery<ua.kpi.cardgame.entities.jpa.GameSession> query = entityManager.createNamedQuery(
                "GameSession.findAll", ua.kpi.cardgame.entities.jpa.GameSession.class
        );
        List<ua.kpi.cardgame.entities.jpa.GameSession> results = query.getResultList();
        return results.stream().map((gameSession) -> {
            return new GameSession(
                gameSession.getSessionId(), gameSession.getStage(), gameSession.getLeader().getUserId(),
                gameSession.getCondition().getCardId(), gameSession.getEventStartTime(), gameSession.getEvent().getEventId()
            );
        }).toList();
    }
}
