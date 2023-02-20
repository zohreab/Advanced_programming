package app.model.CardTypes;

public enum CardType {
    MONSTER("monster"),
    SPELL("spell"),
    TRAP("trap");

    private String title;

    private CardType(String title) {
        this.title = title;
    }

    public String getName() {
        return title;
    }
}
