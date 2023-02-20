package app.model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username;
    private String password;
    private String nickname;
    private int score = 0;
    private int balance;
    private HashMap<String, Integer> cards = new HashMap<>();
    private String activeDeck;
    private HashMap<String, Deck> decks = new HashMap<>();
    private Deck notInADeck = new Deck("notInADeck", Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);


    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public void increaseBalance(int balance) {
        this.balance += balance;
    }

    public void decreaseScore(int score) {
        this.score -= score;
    }

    public String toString() {
        return "username: " + username + "\nnickname: " + nickname + "\nscore: " + score;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void decreaseBalance(int price) {
        this.balance -= price;
    }

    public void addCard(String cardName) throws IllegalActionException {
        if (cards == null) {
            cards = new HashMap<>();
        }
        if (cards.containsKey(cardName)) {
            int number = cards.get(cardName);
            number++;
            cards.put(cardName, number);
        } else {
            cards.put(cardName, 1);
        }
        notInADeck.addCard(cardName, false);
    }

    public void createDeck(String name) throws IllegalActionException {
        if (decks.containsKey(name))
            throw new IllegalActionException("deck with name " + name + " already exists");
        decks.put(name, new Deck(name, 60, 15, 3));
    }

    public void deleteDeck(String deckName) throws IllegalActionException {
        if (decks.containsKey(deckName)) {
            ArrayList<String> cards = decks.get(deckName).getAllCards();
            decks.remove(deckName);
            for (String card : cards) {
                notInADeck.addCard(card, false);
            }
            if (deckName.equals(activeDeck)) {
                activeDeck = null;
            }
        } else {
            throw new IllegalActionException("deck with name " + deckName + " does not exist");
        }
    }

    public void setActiveDeck(String deckName) throws IllegalActionException {
        if (decks.containsKey(deckName)) {
            activeDeck = deckName;
        } else {
            throw new IllegalActionException("deck with name " + deckName + " does not exist");
        }
    }

    public Deck getActiveDeck() {
        if (activeDeck == null) {
            return null;
        }
        return decks.get(activeDeck);
    }

    public void addCardToDeck(String cardName, String deckName, boolean side) throws IllegalActionException {
        if (notInADeck == null)
            notInADeck = new Deck("notInADeck", Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        if (notInADeck.mainCardsContainsCard(cardName) > 0) {
            if (decks.containsKey(deckName)) {
                Deck deck = decks.get(deckName);
                deck.addCard(cardName, side);
                notInADeck.removeCardFromDeck(cardName, false);
            } else {
                throw new IllegalActionException("deck with name " + deckName + " does not exist");
            }
        } else {
            throw new IllegalActionException("card with name " + cardName + " does not exist");
        }
    }

    public void removeCardFromDeck(String cardName, String deckName, boolean side) throws IllegalActionException {
        if (decks.containsKey(deckName)) {
            Deck deck = decks.get(deckName);
            deck.removeCardFromDeck(cardName, side);
            notInADeck.addCard(cardName, false);
        } else {
            throw new IllegalActionException("deck with name " + deckName + " does not exist");
        }
    }

    public ArrayList<Deck> getAllDecks() {
        return new ArrayList<>(decks.values());
    }

    public void migrate() {
        if (cards == null) {
            cards = new HashMap<>();
        }
        if (decks == null) {
            decks = new HashMap<>();
        }
        if (notInADeck == null)
            notInADeck = new Deck("notInADeck", Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public HashMap<String, Integer> getCards() {
        return cards;
    }
}
