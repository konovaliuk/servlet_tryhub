package ua.kpi.cardgame.entities.jpa;

public enum EventName {
    DELAY ("DELAY"), CHOOSING ("CHOOSING"), VOTING ("VOTING"), AUTO_VOTING ("AUTO_VOTING");

    private final String type;

    EventName(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}