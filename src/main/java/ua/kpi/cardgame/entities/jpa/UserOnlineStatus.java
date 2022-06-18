package ua.kpi.cardgame.entities.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@NamedQueries({
    @NamedQuery(name = "UserOnlineStatus.findAll", query = "SELECT u FROM UserOnlineStatus u")
})
public class UserOnlineStatus implements Serializable {
    @Id
    @OneToOne
    private User user;
    private Timestamp timestamp;

    public UserOnlineStatus() {
    }

    public UserOnlineStatus(User user, Timestamp timestamp) {
        this.user = user;
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "UserOnline{" +
                "userId=" + user.getUserId() +
                ", timestamp=" + timestamp +
                '}';
    }
}
