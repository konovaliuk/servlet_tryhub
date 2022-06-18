package ua.kpi.cardgame.dao;

import ua.kpi.cardgame.dao.impl.jpa.*;
import ua.kpi.cardgame.dao.interfaces.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAOFactory {
    private static final EntityManagerFactory entityManager = Persistence.createEntityManagerFactory("jpa");

    public static EventDAO getEventDAO() {
        return new JPAEventDAO(entityManager.createEntityManager());
    }

    public static GameSessionDAO getGameSessionDAO() {
        return new JPAGameSessionDAO(entityManager.createEntityManager());
    }

    public static ICardDAO getCardDAO() {
        return new JPACardDAO(entityManager.createEntityManager());
    }

    public static IUserCardsDAO getUserCardsDAO() {
        return new JPAUserCardsDAO(entityManager.createEntityManager());
    }

    public static IUserDAO getUserDAO() {
        return new JPAUserDAO(entityManager.createEntityManager());
    }

    public static IUserOnlineDAO getUserOnlineDAO() {
        return new JPAUserOnlineDAO(entityManager.createEntityManager());
    }

    public static UserGameSessionDAO getUserGameSessionDAO() {
        return new JPAUserGameSession(entityManager.createEntityManager());
    }

    public static UserSearchGameDAO getUserSearchGameDAO() {
        return new JPAUserSearchGameDAO(entityManager.createEntityManager());
    }
}
