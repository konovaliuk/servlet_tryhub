package ua.kpi.cardgame.dao.impl.jpa;

import ua.kpi.cardgame.dao.interfaces.UserSearchGameDAO;
import ua.kpi.cardgame.entities.UserSearchGame;
import ua.kpi.cardgame.entities.jpa.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class JPAUserSearchGameDAO implements UserSearchGameDAO {
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
    public void startUserSearchGame(int userId) throws SQLException {
        User user = entityManager.find(User.class, userId);
        ua.kpi.cardgame.entities.jpa.UserSearchGame userSearchGame = new ua.kpi.cardgame.entities.jpa.UserSearchGame(
            user, new Timestamp(new Date().getTime())
        );
        entityManager.getTransaction().begin();
        entityManager.persist(userSearchGame);
        commitTransaction();
    }

    @Override
    public void stopUserSearchGame(int userId) throws SQLException {
        ua.kpi.cardgame.entities.jpa.UserSearchGame userSearchGame = entityManager.find(ua.kpi.cardgame.entities.jpa.UserSearchGame.class, userId);
        entityManager.getTransaction().begin();
        entityManager.remove(userSearchGame);
        commitTransaction();
    }

    @Override
    public List<UserSearchGame> getAllUserSearchGame() throws SQLException {
        TypedQuery<ua.kpi.cardgame.entities.jpa.UserSearchGame> query = entityManager.createNamedQuery(
        "UserOnlineStatus.findAll", ua.kpi.cardgame.entities.jpa.UserSearchGame.class
        );
        List<ua.kpi.cardgame.entities.jpa.UserSearchGame> results = query.getResultList();
        return results.stream().map((userSearchGame) -> {
            return new UserSearchGame(userSearchGame.getUser().getUserId(), userSearchGame.getStartTime());
        }).toList();
    }
}
