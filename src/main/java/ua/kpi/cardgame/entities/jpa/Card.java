package ua.kpi.cardgame.entities.jpa;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQueries({
    @NamedQuery(name = "Card.findAll", query = "SELECT c FROM Card c"),
    @NamedQuery(name = "Card.findAllByType", query = "SELECT c FROM Card c WHERE c.type = :type")
})
public class Card {
    @Id
    @GeneratedValue
    private int cardId;
    @Enumerated(EnumType.STRING)
    private CardType type;
    private String resource;

    public Card() {
    }

    public Card(int cardId, CardType type, String resource) {
        this.cardId = cardId;
        this.type = type;
        this.resource = resource;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card Card = (Card) o;
        return cardId == Card.cardId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId);
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", type=" + type +
                ", resource='" + resource + '\'' +
                '}';
    }

    public String toJSON() {
        return "{" +
                "\"card_id\": " + cardId +
                ", \"type\": \"" + type +
                "\", \"resource\": \"" + resource +
                "\"}";
    }
}
