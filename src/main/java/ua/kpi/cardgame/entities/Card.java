package ua.kpi.cardgame.entities;

import java.util.Objects;

public class Card {
    private int cardId;
    private CardType type;
    private String resource;

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
        Card card = (Card) o;
        return cardId == card.cardId;
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
}
