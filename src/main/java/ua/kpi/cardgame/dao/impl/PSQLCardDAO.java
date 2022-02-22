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
    private static final String SELECT = "SELECT * FROM cardGame.cards";
    private static final String INSERT = "INSERT INTO cardGame.cards (type, resource) VALUES (CAST(? AS card_type), ?)";
    private static final String UPDATE = "UPDATE cardGame.cards SET resource = ? WHERE card_id = ?";
    private static final String DELETE = "DELETE FROM cardGame.cards WHERE card_id = ?";

    public PSQLCardDAO() {
        controller = new PSQLController();
    }

    @Override
    public Card getCardById(int id) {
        Card card = null;
        PreparedStatement ps = controller.getPreparedStatement(SELECT + " WHERE card_id = ?");
        ResultSet cards = null;

        try {
            ps.setInt(1, id);
            cards = ps.executeQuery();

            if (cards.next()) {
                card = new Card(
                        cards.getInt(1), CardType.valueOf(cards.getString(2).toUpperCase()),
                        cards.getString(3)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            controller.closeResultSet(cards);
            controller.closePreparedStatement(ps);
        }

        return card;
    }

    @Override
    public Card createCard(CardType type, String resource) {
        Card card = null;
        PreparedStatement ps = controller.getPreparedStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = null;

        try {
            ps.setString(1, type.toString());
            ps.setString(2, resource);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 1) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    card = new Card(rs.getInt(1), type, resource);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            controller.closeResultSet(rs);
            controller.closePreparedStatement(ps);
        }

        return card;
    }

    @Override
    public void deleteCardById(int id) {
        PreparedStatement ps = controller.getPreparedStatement(DELETE);

        try {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            controller.closePreparedStatement(ps);
        }
    }

    @Override
    public boolean updateCardResource(Card card, String resource) {
        PreparedStatement ps = controller.getPreparedStatement(UPDATE);

        try {
            ps.setString(1, resource);
            ps.setInt(2, card.getCardId());

            if (ps.executeUpdate() == 1) {
                card.setResource(resource);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            controller.closePreparedStatement(ps);
        }

        return false;
    }

    @Override
    public List<Card> getAllCards() {
        List<Card> cards = new ArrayList<>();
        PreparedStatement ps = controller.getPreparedStatement(SELECT);
        ResultSet rs = null;

        try {
            rs = ps.executeQuery();

            while (rs.next()) {
                cards.add(new Card(
                        rs.getInt(1), CardType.valueOf(rs.getString(2).toUpperCase()),
                        rs.getString(3)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            controller.closeResultSet(rs);
            controller.closePreparedStatement(ps);
        }

        return cards;
    }

    @Override
    public List<Card> getAllCardsByType(CardType type) {
        List<Card> cards = new ArrayList<>();
        PreparedStatement ps = controller.getPreparedStatement(SELECT + " WHERE type = CAST(? AS card_type)");
        ResultSet rs = null;

        try {
            ps.setString(1, type.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                cards.add(new Card(
                        rs.getInt(1), CardType.valueOf(rs.getString(2).toUpperCase()),
                        rs.getString(3)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            controller.closeResultSet(rs);
            controller.closePreparedStatement(ps);
        }

        return cards;
    }
}
