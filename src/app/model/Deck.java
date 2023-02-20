package app.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private String name;
    private HashMap<String, Integer> mainCards = new HashMap<>();
    private HashMap<String, Integer> sideCards = new HashMap<>();
    private int mainCapacity;
    private int sideCapacity;
    private int cardCountLimit;
    private int mainCount;
    private int sideCount;

    public Deck(String name, int mainCapacity, int sideCapacity, int cardCountLimit) {
        this.name = name;
        this.mainCapacity = mainCapacity;
        this.sideCapacity = sideCapacity;
        this.cardCountLimit = cardCountLimit;
    }

    public void addCard(String name, boolean side) throws IllegalActionException {
        HashMap<String, Integer> deck = mainCards;

        if (side) {

            deck = sideCards;
            if (sideCount >= sideCapacity)
                throw new IllegalActionException("side deck is full");
        } else {
            if (mainCount >= mainCapacity)
                throw new IllegalActionException("main deck is full");
        }
        int count = 0;
        if (mainCards.containsKey(name)) {
            count += mainCards.get(name);
        }
        if (sideCards.containsKey(name)) {
            count += sideCards.get(name);
        }
        if (count >= cardCountLimit)
            throw new IllegalActionException("There are already three cards with name " + name + " in deck " + this.name);
        if (deck.containsKey(name)) {
            int number = deck.get(name);
            number++;
            deck.put(name, number);
        } else {
            deck.put(name, 1);
        }
        if (side)
            sideCount++;
        else
            mainCount++;
    }

    public ArrayList<String> getAllCards() {
        ArrayList<String> cards = new ArrayList<>();
        for (String cardName : mainCards.keySet()) {
            for (int i = 0; i < mainCards.get(cardName); i++) {
                cards.add(cardName);
            }
        }
        for (String cardName : sideCards.keySet()) {
            for (int i = 0; i < mainCards.get(cardName); i++) {
                cards.add(cardName);
            }
        }
        return cards;
    }
  public void removeCardFromDeck(String cardName, boolean side) throws IllegalActionException {
        if (side){
            if (sideCards.containsKey(cardName)){
                int number = sideCards.get(cardName);
                number--;
                if (number<=0){
                    sideCards.remove(cardName);
                }
                else {
                    sideCards.put(cardName, number);
                }
            }
            else {
                throw new IllegalActionException("card with name " +cardName+ " does not exist in side deck");
            }
        } else {
            if (mainCards.containsKey(cardName)){
                int number = mainCards.get(cardName);
                number--;
                if (number<=0){
                    mainCards.remove(cardName);
                }
                else {
                    mainCards.put(cardName, number);
                }
            }
            else {
                throw new IllegalActionException("card with name " +cardName+ " does not exist in main deck");
            }

        }
  }
    public int mainCardsContainsCard(String cardName) {
        if (!mainCards.containsKey(cardName))
            return 0;
        return mainCards.get(cardName);
    }

    public String getName() {
        return name;
    }

    public int getMainCount() {
        return mainCount;
    }

    public int getSideCount() {
        return sideCount;
    }
    public boolean isDeckValid(){
        if (getMainCount()<40){
            return false;
        }
        else {
            return true;
        }
    }


    public HashMap<String, Integer> getMainCards() {
        return mainCards;
    }

    public HashMap<String, Integer> getSideCards() {
        return sideCards;
    }

    public void migrate (){
        mainCards = new HashMap<>();
        sideCards = new HashMap<>();
    }
}
