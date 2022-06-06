package ua.kpi.cardgame.dao.interfaces;

import java.sql.SQLException;

public interface DAO {
    public void rollbackTransaction();
    public void commitTransaction() throws SQLException;
}
