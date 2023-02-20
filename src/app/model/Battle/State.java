package app.model.Battle;

public enum State {
    EMPTY("E"),
    DEFENSIVE_OCCUPIED("DO"),
    DEFENSIVE_HIDDEN("DH"),
    OFFENSIVE_OCCUPIED("OO"),
    OCCUPIED("O"),
    HIDDEN("H");

    String s;

    State(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }
}
