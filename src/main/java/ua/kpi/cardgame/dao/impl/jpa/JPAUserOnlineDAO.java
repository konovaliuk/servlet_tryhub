package ua.kpi.cardgame.dao.impl.jpa;

import ua.kpi.cardgame.dao.interfaces.IUserOnlineDAO;
import ua.kpi.cardgame.entities.UserOnlineStatus;
import ua.kpi.cardgame.entities.jpa.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class JPAUserOnlineDAO implements IUserOnlineDAO {
    EntityManager entityManager = Persistence.createEntityManagerFactory("jpa").createEntityManager();

    @Override
    public void rollbackTransaction() {
        entityManager.getTransaction().rollback();
    }

    @Override
    public void commitTransaction() throws SQLException {
        entityManager.getTransaction().commit();
    }

    @Override
    public void setUserOnline(int userId) throws SQLException {
        setUserOffline(userId);
        User user = entityManager.find(User.class, userId);
        ua.kpi.cardgame.entities.jpa.UserOnlineStatus userOnlineStatus = new ua.kpi.cardgame.entities.jpa.UserOnlineStatus(
            user, new Timestamp(new Date().getTime())
        );
        entityManager.getTransaction().begin();
        entityManager.persist(userOnlineStatus);
        commitTransaction();
    }

    @Override
    public void setUserOffline(int userId) throws SQLException {
        TypedQuery<ua.kpi.cardgame.entities.jpa.UserOnlineStatus> query = entityManager.createNamedQuery(
            "UserOnlineStatus.findById", ua.kpi.cardgame.entities.jpa.UserOnlineStatus.class
        ).setParameter("userId", userId);
        List<ua.kpi.cardgame.entities.jpa.UserOnlineStatus> results = query.getResultList();

        if (results.size() == 1) {
            entityManager.getTransaction().begin();
            entityManager.remove(results.get(0));
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
