package ua.kpi.cardgame.entities.jpa;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Users")
@NamedQueries({
    @NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login = :login"),
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findWithRateBetween", query = "SELECT u FROM User u WHERE u.rate >= :from AND u.rate <= :to")
})

public class User {
    @Id
    @GeneratedValue
    private int userId;
    @Column(unique = true)
    private String login;
    private String password;
    private int rate;

    public User() {
    }

    public User(int userId, String login, String password, int rate) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.rate = rate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
