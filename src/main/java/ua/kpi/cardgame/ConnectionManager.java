package ua.kpi.cardgame;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    DataSource dataSource;

    public ConnectionManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/PostgresDB");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("Error");
        }

        return connection;
    }
}