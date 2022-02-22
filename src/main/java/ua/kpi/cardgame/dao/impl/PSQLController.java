package ua.kpi.cardgame.dao.impl;

import ua.kpi.cardgame.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PSQLController {
    private Connection connection;

    public PSQLController() {
        connection = ConnectionFactory.getPostgreSQLConnection();
    }

    public PreparedStatement getPreparedStatement(String sql) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    public PreparedStatement getPreparedStatement(String sql, int arg) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql, arg);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    public void closePreparedStatement(PreparedStatement ps) {
        try {
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
