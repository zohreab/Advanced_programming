package app.model.CardTypes;

public enum SpellType {
    NORMAL("normal");

    private String title;

    private SpellType(String title) {
        this.title = title;
    }

    public String getName() {
        return title;
    }
}
