package app.model.CardTypes;

public enum MonsterType {
    WARRIOR("warrior");

    private String title;

    private MonsterType(String title) {
        this.title = title;
    }

    public String getName() {
        return title;
    }
}
