package ua.kpi.cardgame.entities.jpa;

import org.postgresql.util.PGInterval;

import javax.persistence.*;
import java.time.Duration;
import java.util.Objects;

@Entity
public class Event {
    @Id
    @GeneratedValue
    private int eventId;
    @Enumerated(EnumType.STRING)
    private EventName eventName;
    private Duration duration;

    public Event() { }

    public Event(int eventId, String eventName, long duration) {
        this.eventId = eventId;
        this.eventName = EventName.valueOf(eventName);
        this.duration =  Duration.ofSeconds(duration);
    }

    public Event(int eventId, String eventName, PGInterval duration) {
        this.eventId = eventId;
        this.eventName = EventName.valueOf(eventName);
        this.duration =  Duration.ofSeconds((long) (duration.getMinutes() * 60L + duration.getSeconds()));
    }

    public int getEventId() { return eventId; }

    public void setEventId(int eventId) { this.eventId = eventId; }

    public EventName getEventName() { return eventName; }

    public void setEventName(EventName eventName) { this.eventName = eventName; }

    public Duration getDuration() { return duration; }

    public void setDuration(long duration) { this.duration = Duration.ofSeconds(duration); }

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
                ", eventDuration=" + duration +
                '}';
    }
}
