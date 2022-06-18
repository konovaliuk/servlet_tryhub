package ua.kpi.cardgame.dao.impl.jpa;

import ua.kpi.cardgame.dao.interfaces.IUserDAO;
import ua.kpi.cardgame.entities.User;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.List;

public class JPAUserDAO implements IUserDAO {
    private final EntityManager entityManager;

    public JPAUserDAO(EntityManager entityManager) {
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
    public User getUserById(int id) throws SQLException {
        ua.kpi.cardgame.entities.jpa.User user = entityManager.find(ua.kpi.cardgame.entities.jpa.User.class, id);

        if (user != null) {
            return new User(user.getUserId(), user.getLogin(), user.getPassword(), user.getRate());
        }

        return null;
    }

    @Override
    public User getUserByLogin(String login) throws SQLException {
        TypedQuery<ua.kpi.cardgame.entities.jpa.User> query = entityManager.createNamedQuery(
        "User.findByLogin", ua.kpi.cardgame.entities.jpa.User.class
        );
        List<ua.kpi.cardgame.entities.jpa.User> results = query.setParameter("login", login).getResultList();

        if (results.size() == 1) {
            ua.kpi.cardgame.entities.jpa.User user = results.get(0);
            return new User(user.getUserId(), user.getLogin(), user.getPassword(), user.getRate());
        }

        return null;
    }

    @Override
    public User createUser(String login, String password) throws SQLException {
        ua.kpi.cardgame.entities.jpa.User user = new ua.kpi.cardgame.entities.jpa.User();
        user.setLogin(login);
        user.setPassword(password);
        user.setRate(0);
        try {
            startTransaction();
            entityManager.persist(user);
            commitTransaction();
            return new User(user.getUserId(), user.getLogin(), user.getPassword(), user.getRate());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteUserById(int id) throws SQLException {
        ua.kpi.cardgame.entities.jpa.User entity = entityManager.find(ua.kpi.cardgame.entities.jpa.User.class, id);
        try {
            startTransaction();
            entityManager.remove(entity);
            commitTransaction();
        } catch (Exception e) {}
    }

    @Override
    public boolean updateUserRate(User user, int rate) throws SQLException {
        ua.kpi.cardgame.entities.jpa.User entity = entityManager.find(ua.kpi.cardgame.entities.jpa.User.class, user.getUserId());
        entity.setRate(rate);
        try {
            startTransaction();
            entityManager.merge(entity);
            commitTransaction();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateUserPassword(User user, String password) throws SQLException {
        ua.kpi.cardgame.entities.jpa.User entity = entityManager.find(ua.kpi.cardgame.entities.jpa.User.class, user.getUserId());
        entity.setPassword(password);
        try {
            startTransaction();
            entityManager.merge(entity);
            commitTransaction();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        TypedQuery<ua.kpi.cardgame.entities.jpa.User> query = entityManager.createNamedQuery(
            "User.findAll", ua.kpi.cardgame.entities.jpa.User.class
        );
        List<ua.kpi.cardgame.entities.jpa.User> results = query.getResultList();
        return results.stream().map((user) -> {
            return new User(user.getUserId(), user.getLogin(), user.getPassword(), user.getRate());
        }).toList();
    }

    @Override
    public List<User> getUsersWithRateBetween(int from, int to) throws SQLException {
        TypedQuery<ua.kpi.cardgame.entities.jpa.User> query = entityManager.createNamedQuery(
            "User.findWithRateBetween", ua.kpi.cardgame.entities.jpa.User.class
        ).setParameter("from", from).setParameter("to", to);
        List<ua.kpi.cardgame.entities.jpa.User> results = query.getResultList();
        return results.stream().map((user) -> {
            return new User(user.getUserId(), user.getLogin(), user.getPassword(), user.getRate());
        }).toList();
    }
}
