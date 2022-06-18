package ua.kpi.cardgame;

import ua.kpi.cardgame.dao.DAOFactory;
import ua.kpi.cardgame.dao.interfaces.GameSessionDAO;
import ua.kpi.cardgame.dao.interfaces.IUserDAO;
import ua.kpi.cardgame.dao.interfaces.UserGameSessionDAO;
import ua.kpi.cardgame.entities.GameSession;
import ua.kpi.cardgame.entities.UserGameSession;

import java.sql.SQLException;

public class RunnerJPA {
    public static void main(String[] args) throws SQLException {
        IUserDAO userDAO = DAOFactory.getUserDAO();
        GameSessionDAO gameSessionDAO = DAOFactory.getGameSessionDAO();
        UserGameSessionDAO userGameSessionDAO = DAOFactory.getUserGameSessionDAO();

        GameSession session = gameSessionDAO.createSession(1, 24, 1, 1);
        userGameSessionDAO.setUserGameSession(24, session.getSessionId());
        userGameSessionDAO.commitTransaction();

        userGameSessionDAO.updateUserSessionChoice(new UserGameSession(session.getSessionId(), 24, 0), 10);
        userGameSessionDAO.commitTransaction();
        System.out.println(userGameSessionDAO.getUsersBySessionId(session.getSessionId()));

        userGameSessionDAO.deleteAllBySessionId(session.getSessionId());
        gameSessionDAO.deleteSessionById(session.getSessionId());
        gameSessionDAO.commitTransaction();
    }
}
