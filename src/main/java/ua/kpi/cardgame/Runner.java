package ua.kpi.cardgame;

import ua.kpi.cardgame.dao.impl.PSQLEventDAO;
import ua.kpi.cardgame.dao.interfaces.EventDAO;

import java.sql.SQLException;
import java.sql.SQLOutput;

public class Runner {
    public static void main(String[] args) throws SQLException {
        EventDAO eventDAO = new PSQLEventDAO();

        System.out.println(eventDAO.getEventById(2));
    }
}
