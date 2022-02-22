package ua.kpi.cardgame.connection;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgreSQLConnector {
    private static BasicDataSource dataSource;

    private PostgreSQLConnector() {};

    private static void initDataSource() {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("password");
        dataSource.setMaxTotal(5);
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        if (dataSource == null) {
            initDataSource();
        }

        connection = dataSource.getConnection();

        return connection;
    }
}