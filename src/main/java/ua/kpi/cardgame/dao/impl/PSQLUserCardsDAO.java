package ua.kpi.cardgame.dao.impl;

import ua.kpi.cardgame.dao.interfaces.IUserCardsDAO;
import ua.kpi.cardgame.entities.UserCard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PSQLUserCardsDAO implements IUserCardsDAO {
    private final PSQLController controller;
    private static final String SELECT = "SELECT * FROM cardGame.user_cards WHERE session_id = ? and user_id = ?";
    private static final String INSERT = "INSERT INTO cardGame.user_cards (session_id, user_id, card_id)" +
                                         "VALUES (?, ?, ?)";
    private static final String DELETE = "DELETE FROM cardGame.user_cards WHERE session_id = ? and user_id = ?";

    public PSQLUserCardsDAO() {
        controller = new PSQLController();
    }


    @Override
    public boolean addUserCards(List<UserCard> userCards) {
        controller.startTransaction();

        for (UserCard userCard : userCards) {
            if (!addUserCard(userCard)) {
                controller.rollbackTransaction();
                return false;
            }
        }

        controller.commitTransaction();
        return true;
    }

    @Override
    public boolean addUserCard(UserCard userCard) {
        PreparedStatement ps = controller.getPreparedStatement(INSERT);

        try {
            ps.setInt(1, userCard.getSessionId());
            ps.setInt(2, userCard.getUserId());
            ps.setInt(3, userCard.getCardId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 1) {
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
    public List<UserCard> getUserCards(int sessionId, int userId) {
        PreparedStatement ps = controller.getPreparedStatement(SELECT);
        ResultSet rs = null;
        List<UserCard> userCards = new ArrayList<>();

        try {
            ps.setInt(1, sessionId);
            ps.setInt(2, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                userCards.add(new UserCard(sessionId, userId, rs.getInt(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            controller.closeResultSet(rs);
            controller.closePreparedStatement(ps);
        }

        return userCards;
    }

    @Override
    public void deleteAllUserCards(int sessionId, int userId) {
        PreparedStatement ps = controller.getPreparedStatement(DELETE);

        try {
            ps.setInt(1, sessionId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUserCard(UserCard userCard) {
        PreparedStatement ps = controller.getPreparedStatement(DELETE + " and card_id = ?");

        try {
            ps.setInt(1, userCard.getSessionId());
            ps.setInt(2, userCard.getUserId());
            ps.setInt(3, userCard.getCardId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
