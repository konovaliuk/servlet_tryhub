package ua.kpi.cardgame.entities.jpa;

import java.io.Serializable;
import java.util.Objects;

public class UserGameSessionKey implements Serializable {
    private GameSession gameSession;
    private User user;

    public UserGameSessionKey() {
    }

    public UserGameSessionKey(GameSession gameSession, User user) {
        this.gameSession = gameSession;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGameSessionKey that = (UserGameSessionKey) o;
        return Objects.equals(gameSession, that.gameSession) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameSession, user);
    }
}
