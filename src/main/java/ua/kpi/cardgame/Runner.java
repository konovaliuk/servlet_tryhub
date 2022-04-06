package ua.kpi.cardgame;

import ua.kpi.cardgame.dao.impl.PSQLController;
import ua.kpi.cardgame.dao.impl.PSQLUserDAO;
import ua.kpi.cardgame.dao.impl.PSQLUserOnlineDAO;
import ua.kpi.cardgame.entities.User;

import java.sql.SQLException;
import java.sql.SQLOutput;

public class Runner {
    public static void main(String[] args) throws SQLException {
        PSQLUserDAO psqlUserDAO = new PSQLUserDAO();
        PSQLUserOnlineDAO psqlUserOnlineDAO = new PSQLUserOnlineDAO();

        System.out.println(psqlUserDAO.getAllUsers());
        System.out.println("==================================");

        User newUser = psqlUserDAO.createUser("new_login", "new_password");
        psqlUserOnlineDAO.setUserOnline(newUser.getUserId());
        PSQLController.getInstance().rollbackTransaction();
        System.out.println(psqlUserDAO.getAllUsers());
        System.out.println(psqlUserOnlineDAO.getAllOnlineUsers());
        System.out.println("==================================");

        psqlUserDAO.updateUserRate(newUser, 15);
        System.out.println(psqlUserDAO.getAllUsers());
        System.out.println("==================================");

        psqlUserOnlineDAO.setUserOffline(newUser.getUserId());
        psqlUserDAO.deleteUserById(newUser.getUserId());
        System.out.println(psqlUserDAO.getAllUsers());
        System.out.println("==================================");
        PSQLController.getInstance().commitTransaction();
    }
}
