package ua.kpi.cardgame.entities;

import java.sql.Timestamp;
import java.util.Objects;

public class UserSearchGame {
    private int userId;
    private Timestamp startTime;

    public UserSearchGame() { }

    public UserSearchGame(int userId, Timestamp startTime) {
        this.userId = userId;
        this.startTime = startTime;
    }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public Timestamp getStartTime() { return startTime; }

    public void setStartTime(Timestamp startTime) { this.startTime = startTime; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSearchGame that = (UserSearchGame) o;
        return userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "UserSearchGame{" +
                "userId=" + userId +
                ", startTime=" + startTime +
                '}';
    }
}
