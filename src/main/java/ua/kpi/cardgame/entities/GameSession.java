package ua.kpi.cardgame.entities;

import java.sql.Timestamp;
import java.util.Objects;

public class GameSession {
    private int sessionId;
    private int stage;
    private int leaderId;
    private int conditionId;
    private Timestamp eventStartTime;
    private int eventId;

    public GameSession() { }

    public GameSession(int sessionId, int stage, int leaderId, int conditionId, Timestamp eventStartTime, int eventId) {
        this.sessionId = sessionId;
        this.stage = stage;
        this.leaderId = leaderId;
        this.conditionId = conditionId;
        this.eventStartTime = eventStartTime;
        this.eventId = eventId;
    }

    public int getSessionId() { return sessionId; }

    public void setSessionId(int sessionId) { this.sessionId = sessionId; }

    public int getStage() { return stage; }

    public void setStage(int stage) { this.stage = stage; }

    public int getLeaderId() { return leaderId; }

    public void setLeaderId(int leaderId) { this.leaderId = leaderId; }

    public int getConditionId() { return conditionId; }

    public void setConditionId(int conditionId) { this.conditionId = conditionId; }

    public Timestamp getEventStartTime() { return eventStartTime; }

    public void setEventStartTime(Timestamp eventStartTime) { this.eventStartTime = eventStartTime; }

    public int getEventId() { return eventId; }

    public void setEventId(int eventId) { this.eventId = eventId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameSession that = (GameSession) o;
        return sessionId == that.sessionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }

    @Override
    public String toString() {
        return "GameSession{" +
                "sessionId=" + sessionId +
                ", stage=" + stage +
                ", leaderId=" + leaderId +
                ", conditionId=" + conditionId +
                ", eventStartTime=" + eventStartTime +
                ", eventId=" + eventId +
                '}';
    }

    public String toJSON() {
        return "{" +
                "\"session_id\": " + sessionId +
                ", \"stage\": " + stage +
                ", \"leader_id\": " + leaderId +
                ", \"condition_id\": " + conditionId +
                ", \"event_start_time\": \"" + eventStartTime +
                "\", \"event_id\": " + eventId +
                '}';
    }
}
