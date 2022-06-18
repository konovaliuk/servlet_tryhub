package ua.kpi.cardgame.entities.jpa;

import java.io.Serializable;
import java.util.Objects;

public class UserCardKey implements Serializable {
    private GameSession gameSession;
    private User user;
    private Card card;

    public UserCardKey() {
    }

    public UserCardKey(GameSession gameSession, User user, ua.kpi.cardgame.entities.jpa.Card card) {
        this.gameSession = gameSession;
        this.user = user;
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCardKey that = (UserCardKey) o;
        return Objects.equals(gameSession, that.gameSession) && Objects.equals(user, that.user) && Objects.equals(card, that.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameSession, user, card);
    }
}
