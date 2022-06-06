package ua.kpi.cardgame.dao.interfaces;

import ua.kpi.cardgame.entities.Event;

import java.sql.SQLException;

public interface EventDAO extends DAO {
    Event getEventById(int eventId) throws SQLException;
}
