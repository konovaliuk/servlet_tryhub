package ua.kpi.cardgame.entities.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@NamedQueries({
    @NamedQuery(name = "UserSearchGame.findAll", query = "SELECT u FROM UserSearchGame u"),
})
public class UserSearchGame implements Serializable {
    @Id
    @OneToOne
    private User user;
    private Timestamp startTime;

    public UserSearchGame() { }

    public UserSearchGame(User user, Timestamp startTime) {
        this.user = user;
        this.startTime = startTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSearchGame that = (UserSearchGame) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "UserSearchGame{" +
                "userId=" + user.getUserId() +
                ", startTime=" + startTime +
                '}';
    }
}
