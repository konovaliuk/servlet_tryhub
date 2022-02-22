package ua.kpi.cardgame.entities;

import java.sql.Timestamp;
import java.util.Objects;

public class UserOnlineStatus {
    private int userId;
    private Timestamp timestamp;

    public UserOnlineStatus(int userId, Timestamp timestamp) {
        this.userId = userId;
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOnlineStatus that = (UserOnlineStatus) o;
        return userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "UserOnline{" +
                "userId=" + userId +
                ", timestamp=" + timestamp +
                '}';
    }
}
