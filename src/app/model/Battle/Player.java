package app.model.Battle;

import app.DataCenter;
import app.model.Cards.Card;
import app.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Player {
    private int LP;
    private User user;
    private final ArrayList<BattleCard> remainedCards;
    private final ArrayList<BattleCard> handCards = new ArrayList<>();
    private int maxLPReward;
    private int winingCount;

    public Player(int LP, User user) {
        this.LP = LP;
        this.user = user;
        this.remainedCards = new ArrayList<>();
        HashMap<String, Integer> cards = user.getActiveDeck().getMainCards();
        for (String cardName : cards.keySet()) {
            Card card = DataCenter.getInstance().getCard(cardName);
            for (int i = 0; i < cards.get(cardName); i++) {
                remainedCards.add(new BattleCard(user.getUsername(), card));
            }
        }
        Collections.shuffle(remainedCards);
        for (int i = 0; i < 4; i++) {
            draw();
        }
    }

    public ArrayList<BattleCard> getHandCards() {
        return handCards;
    }

    public ArrayList<BattleCard> getRemainedCards() {
        return remainedCards;
    }

    public int getLP() {
        return LP;
    }

    public User getUser() {
        return user;
    }

    public void decreaseLP(int LP) {
        this.LP -= LP;
    }

    public void increaseLP(int LP) {
        this.LP += LP;
    }

    public boolean draw () {
        if (remainedCards.size() > 0 && handCards.size() < 6) {
            BattleCard battleCard = remainedCards.get(0);
            remainedCards.remove(0);
            handCards.add(battleCard);
            return true;
        } else return remainedCards.size() != 0 || handCards.size() != 0;
    }

    public int getMaxLPReward() {
        return maxLPReward;
    }

    public void setMaxLPReward(int maxLPReward) {
        this.maxLPReward = maxLPReward;
    }

    public int getWiningCount() {
        return winingCount;
    }

    public void increaseWiningCount() {
        this.winingCount += 1;
    }
}
