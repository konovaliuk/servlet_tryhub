package ua.kpi.cardgame.dao.impl;

import ua.kpi.cardgame.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PSQLController {
    private static PSQLController instance;
    private Connection connection;

    private PSQLController() {}

    public static PSQLController getInstance() {
        if (instance == null) {
            instance = new PSQLController();
        }

        return instance;
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        startTransaction();
        return connection.prepareStatement(sql);
    }

    public PreparedStatement getPreparedStatement(String sql, int arg) throws SQLException {
        startTransaction();
        return connection.prepareStatement(sql, arg);
    }

    public void startTransaction() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = ConnectionFactory.getPostgreSQLConnection();
        }
        connection.setAutoCommit(false);
    }

    public void rollbackTransaction() throws SQLException {
        connection.rollback();
        connection.close();
    }

    public void commitTransaction() throws SQLException {
        connection.commit();
        connection.close();
    }
}
