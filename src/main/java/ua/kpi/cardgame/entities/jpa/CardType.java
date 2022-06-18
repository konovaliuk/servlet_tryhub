package ua.kpi.cardgame.entities.jpa;

public enum CardType {
    IMAGE ("IMAGE"), TEXT ("TEXT");

    private final String type;

    CardType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}