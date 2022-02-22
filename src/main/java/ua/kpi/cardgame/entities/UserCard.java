package ua.kpi.cardgame.entities;

import java.util.Objects;

public class UserCard {
    private int sessionId;
    private int userId;
    private int cardId;

    public UserCard(int sessionId, int userId, int cardId) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.cardId = cardId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCard userCard = (UserCard) o;
        return sessionId == userCard.sessionId && userId == userCard.userId && cardId == userCard.cardId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, userId, cardId);
    }

    @Override
    public String toString() {
        return "UserCards{" +
                "sessionId=" + sessionId +
                ", userId=" + userId +
                ", cardId=" + cardId +
                '}';
    }
}
