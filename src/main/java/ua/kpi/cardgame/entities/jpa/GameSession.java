package ua.kpi.cardgame.entities.jpa;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@NamedQueries({
    @NamedQuery(name = "GameSession.findAll", query = "SELECT g FROM GameSession g"),
})
public class GameSession {
    @Id
    @GeneratedValue
    private int sessionId;
    private int stage;
    @OneToOne
    private User leader;
    @OneToOne
    private Card condition;

    @CreationTimestamp
    private Timestamp eventStartTime;
    @OneToOne
    private Event event;

    public GameSession() { }

    public GameSession(int sessionId, int stage, User leader, Card condition, Timestamp eventStartTime, Event event) {
        this.sessionId = sessionId;
        this.stage = stage;
        this.leader = leader;
        this.condition = condition;
        this.eventStartTime = eventStartTime;
        this.event = event;
    }

    public int getSessionId() { return sessionId; }

    public void setSessionId(int sessionId) { this.sessionId = sessionId; }

    public int getStage() { return stage; }

    public void setStage(int stage) { this.stage = stage; }

    public User getLeader() { return leader; }

    public void setLeader(User leader) { this.leader = leader; }

    public Card getCondition() { return condition; }

    public void setCondition(Card condition) { this.condition = condition; }

    public Timestamp getEventStartTime() { return eventStartTime; }

    public void setEventStartTime(Timestamp eventStartTime) { this.eventStartTime = eventStartTime; }

    public Event getEvent() { return event; }

    public void setEvent(Event event) { this.event = event; }

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
                ", leaderId=" + leader.getUserId() +
                ", conditionId=" + condition.getCardId() +
                ", eventStartTime=" + eventStartTime +
                ", eventId=" + event.getEventId() +
                '}';
    }

    public String toJSON() {
        return "{" +
                "\"session_id\": " + sessionId +
                ", \"stage\": " + stage +
                ", \"leader_id\": " + leader.getUserId() +
                ", \"condition_id\": " + condition.getCardId() +
                ", \"event_start_time\": \"" + eventStartTime +
                "\", \"event_id\": " + event.getEventId() +
                '}';
    }
}
