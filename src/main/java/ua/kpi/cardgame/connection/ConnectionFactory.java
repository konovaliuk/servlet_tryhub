package ua.kpi.cardgame.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getPostgreSQLConnection() {
        Connection connection = null;

        try {
            connection = PostgreSQLConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
