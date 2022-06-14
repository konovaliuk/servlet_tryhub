package ua.kpi.cardgame.dao;

import ua.kpi.cardgame.dao.impl.*;
import ua.kpi.cardgame.dao.impl.jpa.JPAUserDAO;
import ua.kpi.cardgame.dao.impl.jpa.JPAUserOnlineDAO;
import ua.kpi.cardgame.dao.impl.jpa.JPAUserSearchGameDAO;
import ua.kpi.cardgame.dao.interfaces.*;

public class DAOFactory {
    private static final EventDAO eventDAO = new PSQLEventDAO();
    private static final GameSessionDAO gameSessionDAO = new PSQLGameSessionDAO();
    private static final ICardDAO cardDAO = new PSQLCardDAO();
    private static final IUserCardsDAO userCardsDAO = new PSQLUserCardsDAO();
    private static final IUserDAO userDAO = new JPAUserDAO();
    private static final IUserOnlineDAO userOnlineDAO = new JPAUserOnlineDAO();
    private static final UserGameSessionDAO userGameSessionDAO = new PSQLUserGameSessionDAO();
    private static final UserSearchGameDAO userSearchGameDAO = new JPAUserSearchGameDAO();

    public static EventDAO getEventDAO() {
        return eventDAO;
    }

    public static GameSessionDAO getGameSessionDAO() {
        return gameSessionDAO;
    }

    public static ICardDAO getCardDAO() {
        return cardDAO;
    }

    public static IUserCardsDAO getUserCardsDAO() {
        return userCardsDAO;
    }

    public static IUserDAO getUserDAO() {
        return userDAO;
    }

    public static IUserOnlineDAO getUserOnlineDAO() {
        return userOnlineDAO;
    }

    public static UserGameSessionDAO getUserGameSessionDAO() {
        return userGameSessionDAO;
    }

    public static UserSearchGameDAO getUserSearchGameDAO() {
        return userSearchGameDAO;
    }
}
