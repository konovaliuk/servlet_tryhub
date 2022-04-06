package ua.kpi.cardgame.dao.interfaces;

import ua.kpi.cardgame.entities.Event;

import java.sql.SQLException;

public interface EventDAO {
    Event getEventById(int eventId) throws SQLException;
}
