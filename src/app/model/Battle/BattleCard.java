package app.model.Battle;

import app.model.Cards.Card;

import java.util.Random;

public class BattleCard {
    private static final Random rand = new Random();
    private final String id;
    private State state;
    private final Card card;
    private boolean hasStateChanged;

    public BattleCard(String username, Card card) {
        this.id = username + "-" + card.getName() + "-" + rand.nextInt(10000000);
        this.card = card;
    }

    public String getId() {
        return id;
    }

    public Card getCard() {
        return card;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void changeState(BattleCard battleCard){
        if (battleCard.getState()==State.DEFENSIVE_OCCUPIED){
            setState(State.OFFENSIVE_OCCUPIED);
        }
        if (battleCard.getState() == State.OFFENSIVE_OCCUPIED){
            setState(State.DEFENSIVE_OCCUPIED);
        }
    }

    public boolean isHasStateChanged() {
        return hasStateChanged;
    }

    public void setHasStateChanged(boolean hasStateChanged) {
        this.hasStateChanged = hasStateChanged;
    }
}
