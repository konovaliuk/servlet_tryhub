package ua.kpi.cardgame.dao.impl;

import ua.kpi.cardgame.dao.interfaces.ICardDAO;
import ua.kpi.cardgame.entities.Card;
import ua.kpi.cardgame.entities.CardType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PSQLCardDAO implements ICardDAO {
    private final PSQLController controller;

    private static final String COLUMN_ID = "card_id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_RESOURCE = "resource";

    private static final String SELECT = "SELECT * FROM cardGame.cards";
    private static final String INSERT = "INSERT INTO cardGame.cards (type, resource) VALUES (CAST(? AS card_type), ?)";
    private static final String UPDATE = "UPDATE cardGame.cards SET resource = ? WHERE card_id = ?";
    private static final String DELETE = "DELETE FROM cardGame.cards WHERE card_id = ?";

    public PSQLCardDAO() {
        controller = PSQLController.getInstance();
    }

    @Override
    public Card getCardById(int id) throws SQLException {
        Card card = null;

        PreparedStatement ps = controller.getPreparedStatement(SELECT + " WHERE card_id = ?");
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            card = new Card(
                rs.getInt(COLUMN_ID), CardType.valueOf(rs.getString(COLUMN_TYPE).toUpperCase()),
                rs.getString(COLUMN_RESOURCE)
            );
        }

        rs.close();
        ps.close();

        return card;
    }

    @Override
    public Card createCard(CardType type, String resource) throws SQLException {
        Card card = null;

        PreparedStatement ps = controller.getPreparedStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, type.toString());
        ps.setString(2, resource);

        if (ps.executeUpdate() == 1) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                card = new Card(rs.getInt(COLUMN_ID), type, resource);
            }
            rs.close();
        }

        ps.close();

        return card;
    }

    @Override
    public void deleteCardById(int id) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(DELETE);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public boolean updateCardResource(Card card, String resource) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(UPDATE);
        ps.setString(1, resource);
        ps.setInt(2, card.getCardId());
        int affectedRows = ps.executeUpdate();
        ps.close();

        if (affectedRows == 1) {
            card.setResource(resource);
            return true;
        }

        return false;
    }

    @Override
    public List<Card> getAllCards() throws SQLException {
        List<Card> cards = new ArrayList<>();
        PreparedStatement ps = controller.getPreparedStatement(SELECT);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            cards.add(new Card(
                rs.getInt(COLUMN_ID), CardType.valueOf(rs.getString(COLUMN_TYPE).toUpperCase()),
                rs.getString(COLUMN_TYPE)
            ));
        }

        rs.close();
        ps.close();

        return cards;
    }

    @Override
    public List<Card> getAllCardsByType(CardType type) throws SQLException {
        List<Card> cards = new ArrayList<>();

        PreparedStatement ps = controller.getPreparedStatement(SELECT + " WHERE type = CAST(? AS card_type)");
        ps.setString(1, type.toString());

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            cards.add(new Card(
                rs.getInt(COLUMN_ID), CardType.valueOf(rs.getString(COLUMN_TYPE).toUpperCase()),
                rs.getString(COLUMN_RESOURCE)
            ));
        }

        rs.close();
        ps.close();

        return cards;
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