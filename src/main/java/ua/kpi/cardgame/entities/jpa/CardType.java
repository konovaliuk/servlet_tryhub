package ua.kpi.cardgame.entities.jpa;

public enum CardType {
    IMAGE ("image"), TEXT ("text");

    private final String type;

    CardType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}