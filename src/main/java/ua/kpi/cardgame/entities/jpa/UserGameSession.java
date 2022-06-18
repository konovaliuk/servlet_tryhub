package ua.kpi.cardgame.entities.jpa;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(UserGameSessionKey.class)
@NamedQueries({
    @NamedQuery(name = "UserGameSession.findAllBySession", query = "SELECT s FROM UserGameSession s WHERE s.gameSession = :session"),
    @NamedQuery(name = "UserGameSession.findByUserAndSession", query = "SELECT s FROM UserGameSession s WHERE s.gameSession = :session AND s.user = :user"),
})
public class UserGameSession {
    @Id
    @ManyToOne
    private GameSession gameSession;
    @Id
    @OneToOne
    private User user;
    @ManyToOne
    private Card userChoice;

    public UserGameSession() { }

    public UserGameSession(GameSession gameSession, User user, Card userChoice) {
        this.gameSession = gameSession;
        this.user = user;
        this.userChoice = userChoice;
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

    public Card getUserChoice() {
        return userChoice;
    }

    public void setUserChoice(Card userChoice) {
        this.userChoice = userChoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGameSession that = (UserGameSession) o;
        return Objects.equals(gameSession, that.gameSession) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameSession, user);
    }

    @Override
    public String toString() {
        return "UserGameSession{" +
                "sessionId=" + gameSession.getSessionId() +
                ", userId=" + user.getUserId() +
                ", userChoice=" + (userChoice == null? "0" : String.valueOf(userChoice.getCardId())) +
                '}';
    }
}
