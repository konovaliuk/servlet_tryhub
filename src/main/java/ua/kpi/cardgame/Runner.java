package ua.kpi.cardgame;

import ua.kpi.cardgame.connection.ConnectionFactory;
import ua.kpi.cardgame.dao.impl.PSQLUserDAO;
import ua.kpi.cardgame.entities.User;

import java.sql.Connection;
import java.sql.SQLException;

public class Runner {
    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionFactory.getPostgreSQLConnection();
        System.out.println(connection);
        connection.close();

        PSQLUserDAO psqlUserDAO = new PSQLUserDAO();
        User test = psqlUserDAO.getUserById(8);
        User obj = psqlUserDAO.createUser("misha", "password222");
        psqlUserDAO.deleteUserById(obj.getUserId());
        System.out.println(psqlUserDAO.getAllUsers());
        System.out.println(psqlUserDAO.getUsersWithRateBetween(0, 10));
        psqlUserDAO.updateUserRate(test, 15);
        System.out.println(psqlUserDAO.getUsersWithRateBetween(1, 10));
        System.out.println(psqlUserDAO.getAllUsers());
        System.out.println("ok");
    }
}
