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

    private static final String COLUMN_SESSION = "session_id";
    private static final String COLUMN_USER = "user_id";
    private static final String COLUMN_CARD = "card_id";

    private static final String SELECT = "SELECT * FROM cardGame.user_cards WHERE session_id = ? and user_id = ?";
    private static final String INSERT = "INSERT INTO cardGame.user_cards (session_id, user_id, card_id)" +
                                         "VALUES (?, ?, ?)";
    private static final String DELETE = "DELETE FROM cardGame.user_cards WHERE session_id = ? and user_id = ?";

    public PSQLUserCardsDAO() {
        controller = PSQLController.getInstance();
    }

    @Override
    public boolean addUserCards(List<UserCard> userCards) throws SQLException {
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
    public boolean addUserCard(UserCard userCard) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(INSERT);

        ps.setInt(1, userCard.getSessionId());
        ps.setInt(2, userCard.getUserId());
        ps.setInt(3, userCard.getCardId());

        int rowsAffected = ps.executeUpdate();
        ps.close();

        if (rowsAffected == 1) {
            return true;
        }

        return false;
    }

    @Override
    public List<UserCard> getUserCards(int sessionId, int userId) throws SQLException {
        List<UserCard> userCards = new ArrayList<>();

        PreparedStatement ps = controller.getPreparedStatement(SELECT);
        ps.setInt(1, sessionId);
        ps.setInt(2, userId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            userCards.add(new UserCard(sessionId, userId, rs.getInt(COLUMN_CARD)));
        }

        rs.close();
        ps.close();

        return userCards;
    }

    @Override
    public void deleteAllUserCards(int sessionId, int userId) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(DELETE);
        ps.setInt(1, sessionId);
        ps.setInt(2, userId);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void deleteUserCard(UserCard userCard) throws SQLException {
        PreparedStatement ps = controller.getPreparedStatement(DELETE + " and card_id = ?");
        ps.setInt(1, userCard.getSessionId());
        ps.setInt(2, userCard.getUserId());
        ps.setInt(3, userCard.getCardId());
        ps.executeUpdate();
        ps.close();
    }
}
