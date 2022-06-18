package ua.kpi.cardgame.dao.impl.jpa;

import ua.kpi.cardgame.dao.interfaces.IUserOnlineDAO;
import ua.kpi.cardgame.entities.UserOnlineStatus;
import ua.kpi.cardgame.entities.jpa.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class JPAUserOnlineDAO implements IUserOnlineDAO {
    private final EntityManager entityManager;

    public JPAUserOnlineDAO(EntityManager entityManager) {
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
    public void setUserOnline(int userId) throws SQLException {
        setUserOffline(userId);
        User user = entityManager.find(User.class, userId);
        ua.kpi.cardgame.entities.jpa.UserOnlineStatus userOnlineStatus = new ua.kpi.cardgame.entities.jpa.UserOnlineStatus(
            user, new Timestamp(new Date().getTime())
        );
        try {
            startTransaction();
            entityManager.persist(userOnlineStatus);
            commitTransaction();
        } catch (Exception e) {
        }
    }

    @Override
    public void setUserOffline(int userId) throws SQLException {
        ua.kpi.cardgame.entities.jpa.UserOnlineStatus userOnlineStatus = entityManager.find(
            ua.kpi.cardgame.entities.jpa.UserOnlineStatus.class, userId
        );

        if (userOnlineStatus != null) {
            startTransaction();
            entityManager.remove(userOnlineStatus);
            commitTransaction();
        }
    }

    @Override
    public List<UserOnlineStatus> getAllOnlineUsers() throws SQLException {
        TypedQuery<ua.kpi.cardgame.entities.jpa.UserOnlineStatus> query = entityManager.createNamedQuery(
            "UserOnlineStatus.findAll", ua.kpi.cardgame.entities.jpa.UserOnlineStatus.class
        );
        List<ua.kpi.cardgame.entities.jpa.UserOnlineStatus> results = query.getResultList();
        return results.stream().map((userOnlineStatus) -> {
            return new UserOnlineStatus(userOnlineStatus.getUser().getUserId(), userOnlineStatus.getTimestamp());
        }).toList();
    }
}
