package ua.kpi.cardgame.dao.impl.psql;

import org.postgresql.util.PGInterval;
import ua.kpi.cardgame.dao.interfaces.EventDAO;
import ua.kpi.cardgame.entities.Event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PSQLEventDAO implements EventDAO {
    private final PSQLController controller;
    private static final String SELECT = "SELECT * FROM cardGame.events";

    public PSQLEventDAO() {
        controller = PSQLController.getInstance();
    }

    public Event getEventById(int eventId) throws SQLException {
        Event event = null;
        PreparedStatement ps = controller.getPreparedStatement(SELECT + " WHERE event_id = ?");

        ps.setInt(1, eventId);
        ResultSet events = ps.executeQuery();

        if (events.next()) {
            event = new Event(
                    events.getInt(1), events.getString(2), (PGInterval) events.getObject(3)
            );
        }

        ps.close();

        return event;
    }

    @Override
    public void rollbackTransaction() {
        controller.rollbackTransaction();
    }

    @Override
    public void commitTransaction() throws SQLException {
        controller.commitTransaction();
    }
}
