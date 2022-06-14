package ua.kpi.cardgame;

import ua.kpi.cardgame.dao.impl.jpa.JPAUserDAO;
import ua.kpi.cardgame.dao.interfaces.IUserDAO;

import java.sql.SQLException;

public class RunnerJPA {
    public static void main(String[] args) throws SQLException {
        IUserDAO userDAO = new JPAUserDAO();

        userDAO.createUser("test", "test");
        System.out.println(userDAO.getUsersWithRateBetween(2, 4));
        userDAO.deleteUserById(15);
    }
}
