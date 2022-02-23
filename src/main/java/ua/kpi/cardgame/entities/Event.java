package ua.kpi.cardgame.entities;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Objects;

enum EventName {
    DELAY, CHOOSING, VOTING, AUTO_VOTING
}

public class Event {
    private int eventId;
    private EventName eventName;
    private Duration eventDuration;

    public Event() { }

    public Event(int eventId, EventName eventName, Duration eventDuration) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDuration = eventDuration;
    }

    public int getEventId() { return eventId; }

    public void setEventId(int eventId) { this.eventId = eventId; }

    public EventName getEventName() { return eventName; }

    public void setEventName(EventName eventName) { this.eventName = eventName; }

    public Duration getEventDuration() { return eventDuration; }

    public void setEventDuration(Duration eventDuration) { this.eventDuration = eventDuration; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventId == event.eventId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName=" + eventName +
                ", eventDuration=" + eventDuration +
                '}';
    }
}
