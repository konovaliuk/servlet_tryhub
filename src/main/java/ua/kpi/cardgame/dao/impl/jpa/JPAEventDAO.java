package ua.kpi.cardgame.dao.impl.jpa;

import ua.kpi.cardgame.dao.interfaces.EventDAO;
import ua.kpi.cardgame.entities.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.SQLException;

public class JPAEventDAO implements EventDAO {
    private final EntityManager entityManager;

    public JPAEventDAO(EntityManager entityManager) {
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
    public Event getEventById(int eventId) throws SQLException {
        ua.kpi.cardgame.entities.jpa.Event event = entityManager.find(ua.kpi.cardgame.entities.jpa.Event.class, eventId);

        if (event != null) {
            return new Event(event.getEventId(), event.getEventName().toString(), event.getDuration().toSeconds());
        }

        return null;
    }
}
