package ua.kpi.cardgame.entities;

import java.util.Objects;

public class UserGameSession {
    private int sessionId;
    private int userId;
    private int userChoice;

    public UserGameSession() { }

    public UserGameSession(int sessionId, int userId, int userChoice) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.userChoice = userChoice;
    }

    public int getSessionId() { return sessionId; }

    public void setSessionId(int sessionId) { this.sessionId = sessionId; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public int getUserChoice() { return userChoice; }

    public void setUserChoice(int userChoice) { this.userChoice = userChoice; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGameSession that = (UserGameSession) o;
        return sessionId == that.sessionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }

    @Override
    public String toString() {
        return "UserGameSession{" +
                "sessionId=" + sessionId +
                ", userId=" + userId +
                ", userChoise=" + userChoice +
                '}';
    }
}
