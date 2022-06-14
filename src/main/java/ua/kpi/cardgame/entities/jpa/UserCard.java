package ua.kpi.cardgame.entities.jpa;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
public class UserCard {
    @OneToOne
    private GameSession gameSession;
    @OneToOne
    private User user;
    @OneToOne
    private Card Card;

    public UserCard(GameSession gameSession, User user, Card cards) {
        this.gameSession = gameSession;
        this.user = user;
        this.Card = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCard userCard = (UserCard) o;
        return Objects.equals(gameSession, userCard.gameSession) && Objects.equals(user, userCard.user) && Objects.equals(Card, userCard.Card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameSession, user, Card);
    }

    @Override
    public String toString() {
        return "UserCards{" +
                "sessionId=" + gameSession.getSessionId() +
                ", userId=" + user.getUserId() +
                ", cardId=" + Card.getCardId() +
                '}';
    }
}
