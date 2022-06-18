package ua.kpi.cardgame.entities.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(UserCardKey.class)
@NamedQueries({
    @NamedQuery(name = "UserCard.findAll", query = "SELECT c FROM UserCard c WHERE c.user = :user AND c.gameSession = :session"),
})
public class UserCard implements Serializable {
    @Id
    @ManyToOne
    private GameSession gameSession;
    @Id
    @ManyToOne
    private User user;
    @Id
    @ManyToOne
    private Card card;

    public UserCard() {
    }

    public UserCard(GameSession gameSession, User user, Card cards) {
        this.gameSession = gameSession;
        this.user = user;
        this.card = cards;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ua.kpi.cardgame.entities.jpa.Card getCard() {
        return card;
    }

    public void setCard(ua.kpi.cardgame.entities.jpa.Card card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCard userCard = (UserCard) o;
        return Objects.equals(gameSession, userCard.gameSession) && Objects.equals(user, userCard.user) && Objects.equals(card, userCard.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameSession, user, card);
    }

    @Override
    public String toString() {
        return "UserCards{" +
                "sessionId=" + gameSession.getSessionId() +
                ", userId=" + user.getUserId() +
                ", cardId=" + card.getCardId() +
                '}';
    }
}
