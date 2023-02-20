package app;

import app.model.Battle.Battle;
import app.model.Battle.BattleCard;
import app.model.Battle.Phases;
import app.model.Battle.SelectType;
import app.model.CardTypes.CardType;
import app.model.Cards.Card;
import app.model.Deck;
import app.model.IllegalActionException;
import app.model.User;
import app.view.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Controller {
    MenuHandler handler = new LoginMenuHandler();

    public void run() {
        DataCenter.getInstance().loadData();
        while (true) {
            try {
                if (!handler.handle(this))
                    break;

            } catch (IllegalActionException e) {
                System.out.println(e.getMessage());
            }
        }
        DataCenter.getInstance().storeData();
    }


    public void createUser(String username, String password, String nickname) throws IllegalActionException {
        if (DataCenter.getInstance().getUser(username) == null) {
            if (DataCenter.getInstance().nicknameExisted(nickname)) {
                throw new IllegalActionException("user with nickname " + nickname + " already exists");
            }
            User user = new User(username, password, nickname);
            DataCenter.getInstance().addUser(user);
            System.out.println("user created successfully!");
        } else {
            throw new IllegalActionException("user with username " + username + " already exists");
        }
    }

    public void login(String username, String password) throws IllegalActionException {
        User user = DataCenter.getInstance().getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            DataCenter.getInstance().setCurrentUser(user);
            handler = new MainMenuHandler();
            System.out.println("user logged in successfully!");

        } else {
            throw new IllegalActionException("Username and password didn't match!");
        }

    }

    public void logout() {
        DataCenter.getInstance().setCurrentUser(null);
        handler = new LoginMenuHandler();
        System.out.println("user logged out successfully!");
    }

    public void enterMenu(String group) {
        if (group.equals("Deck")) {
            handler = new DeckMenuHandler();
        } else if (group.equals("Profile")) {
            handler = new ProfileMenuHandler();
        } else if (group.equals("Scoreboard")) {
            handler = new ScoreboardMenuHandler();
        } else if (group.equals("Shop")) {
            handler = new ShopMenuHandler();
        }
    }

    public void exitMain() {
        logout();
    }

    public void changeNickName(String nickname) throws IllegalActionException {
        if (DataCenter.getInstance().nicknameExisted(nickname)) {
            throw new IllegalActionException("user with nickname " + nickname + " already exists");
        } else {
            User user = DataCenter.getInstance().getCurrentUser();
            user.setNickname(nickname);
            System.out.println("nickname changed successfully!");
        }
    }

    public void changePassword(String cp, String password) throws IllegalActionException {
        User user = DataCenter.getInstance().getCurrentUser();
        if (user.getPassword().equals(cp)) {
            if (user.getPassword().equals(password)) {
                throw new IllegalActionException("please enter a new password");
            }
            user.setPassword(password);
            System.out.println("password changed successfully");
        } else {
            throw new IllegalActionException("current password is invalid");
        }
    }

    public void showScoreboard() {

        ArrayList<User> users = DataCenter.getInstance().getUsers();
        users.sort((o1, o2) -> {
                    if (o1.getScore() == o2.getScore())
                        return 0;
                    return Integer.compare(o2.getScore(), o1.getScore());
                }
        );
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ". " + users.get(i).getUsername() + " : " + users.get(i).getScore());
        }
    }


    public void showShop() {
        HashMap<String, Card> cards = DataCenter.getInstance().getCards();

        for (Card card : cards.values()) {
            System.out.println(card.getName() + " : " + card.getDescription() + "\n");
        }

    }


    public void buyCard(String group) throws IllegalActionException {
        HashMap<String, Card> cards = DataCenter.getInstance().getCards();

        if (cards.containsKey(group)) {
            Card card = cards.get(group);
            if (card.getPrice() <= DataCenter.getInstance().getCurrentUser().getBalance()) {
                DataCenter.getInstance().getCurrentUser().decreaseBalance(card.getPrice());
                DataCenter.getInstance().getCurrentUser().addCard(group);
            } else {
                throw new IllegalActionException("not enough money");
            }
        } else {
            throw new IllegalActionException("there is no card with this name");
        }


    }

    public void cardShow(String group) throws IllegalActionException {
        HashMap<String, Card> cards = DataCenter.getInstance().getCards();
        if (cards.containsKey(group)) {
            Card card = cards.get(group);
            System.out.println(card.toString());
        } else {
            throw new IllegalActionException("card with this name does not exist");
        }
    }

    public void activeEffect() {
    }

    public void attack(String group) {
    }

    public void back() {
        handler = new DuelMenuHandler();
    }

    public void deselect() throws IllegalActionException {
        DataCenter.getInstance().getCurrentBattle().deselect();
        System.out.println("card deselected");
    }

    public void directAttack() {
    }

    public void oppField() throws IllegalActionException {
        int turn = DataCenter.getInstance().getCurrentBattle().getTurn();
        BattleCard fieldZone = DataCenter.getInstance().getCurrentBattle().getBattleField().getFieldZone((turn + 1) % 2);
        if (fieldZone == null) {
            throw new IllegalActionException("no card found in the given position");
        }
        DataCenter.getInstance().getCurrentBattle().select(0, SelectType.FIELD_OPPONENT);
        System.out.println("card selected");
    }

    public void flip() {
    }

    public void spellOpp(int group) throws IllegalActionException {
        if (group > 5 || group < 1) {
            throw new IllegalActionException("invalid selection");
        }
        int selectedAddress;
        switch (group) {
            case 1:
                selectedAddress = 2;
                break;
            case 2:
                selectedAddress = 3;
                break;
            case 3:
                selectedAddress = 1;
                break;
            case 4:
                selectedAddress = 4;
                break;
            default:
                selectedAddress = 0;
        }
        int turn = DataCenter.getInstance().getCurrentBattle().getTurn();
        BattleCard[] spellZone = DataCenter.getInstance().getCurrentBattle().getBattleField().getSpellZone((turn + 1) % 2);
        if (spellZone[selectedAddress] == null) {
            throw new IllegalActionException("no card found in the given position");
        }
        DataCenter.getInstance().getCurrentBattle().select(selectedAddress, SelectType.SPELL_OPPONENT);
        System.out.println("card selected");
    }

    public void oppMonster(int group) throws IllegalActionException {
        if (group > 5 || group < 1) {
            throw new IllegalActionException("invalid selection");
        }
        int selectedAddress;
        switch (group) {
            case 1:
                selectedAddress = 2;
                break;
            case 2:
                selectedAddress = 3;
                break;
            case 3:
                selectedAddress = 1;
                break;
            case 4:
                selectedAddress = 4;
                break;
            default:
                selectedAddress = 0;
        }
        int turn = DataCenter.getInstance().getCurrentBattle().getTurn();
        BattleCard[] monsterZone = DataCenter.getInstance().getCurrentBattle().getBattleField().getMonsterZone((turn + 1) % 2);
        if (monsterZone[selectedAddress] == null) {
            throw new IllegalActionException("no card found in the given position");
        }
        DataCenter.getInstance().getCurrentBattle().select(selectedAddress, SelectType.MONSTER_OPPONENT);
        System.out.println("card selected");
    }

    public void selectField() throws IllegalActionException {
        int turn = DataCenter.getInstance().getCurrentBattle().getTurn();
        BattleCard fieldZone = DataCenter.getInstance().getCurrentBattle().getBattleField().getFieldZone(turn % 2);
        if (fieldZone == null) {
            throw new IllegalActionException("no card found in the given position");
        }
        DataCenter.getInstance().getCurrentBattle().select(0, SelectType.SELECT_FIELLD);
        System.out.println("card selected");
    }

    public void selectHand(int group) throws IllegalActionException {
        group--;
        if (group > 5 || group < 0) {
            throw new IllegalActionException("invalid selection");
        }
        ArrayList<BattleCard> battleCards = DataCenter.getInstance().getCurrentBattle().getCurrentPlayer().getHandCards();
        if (battleCards.size() <= group) {
            throw new IllegalActionException("no card found in the given position");
        }
        DataCenter.getInstance().getCurrentBattle().select(group, SelectType.SELECT_HAND);
        System.out.println("card selected");
    }

    public void selectSpell(int group) throws IllegalActionException {
        if (group > 5 || group < 1) {
            throw new IllegalActionException("invalid selection");
        }
        int selectedAddress;
        switch (group) {
            case 1:
                selectedAddress = 2;
                break;
            case 2:
                selectedAddress = 3;
                break;
            case 3:
                selectedAddress = 1;
                break;
            case 4:
                selectedAddress = 4;
                break;
            default:
                selectedAddress = 0;
        }
        int turn = DataCenter.getInstance().getCurrentBattle().getTurn();
        BattleCard[] spellZone = DataCenter.getInstance().getCurrentBattle().getBattleField().getSpellZone(turn % 2);
        if (spellZone[selectedAddress] == null) {
            throw new IllegalActionException("no card found in the given position");
        }
        DataCenter.getInstance().getCurrentBattle().select(selectedAddress, SelectType.SELECT_SPELL);
        System.out.println("card selected");
    }

    public void selectMonster(int group) throws IllegalActionException {
        if (group > 5 || group < 1) {
            throw new IllegalActionException("invalid selection");
        }
        int selectedAddress;
        switch (group) {
            case 1:
                selectedAddress = 2;
                break;
            case 2:
                selectedAddress = 3;
                break;
            case 3:
                selectedAddress = 1;
                break;
            case 4:
                selectedAddress = 4;
                break;
            default:
                selectedAddress = 0;
        }
        int turn = DataCenter.getInstance().getCurrentBattle().getTurn();
        BattleCard[] monsterZone = DataCenter.getInstance().getCurrentBattle().getBattleField().getMonsterZone(turn % 2);
        if (monsterZone[selectedAddress] == null) {
            throw new IllegalActionException("no card found in the given position");
        }
        DataCenter.getInstance().getCurrentBattle().select(selectedAddress, SelectType.SELECT_MONSTER);
        System.out.println("card selected");
    }


    public void setPosition(String group) throws IllegalActionException {
        DataCenter.getInstance().getCurrentBattle().changeMonsterState(group);
        System.out.println("monster card position changed successfully");
    }

    public void showSelected() throws IllegalActionException {
        BattleCard battleCard = DataCenter.getInstance().getCurrentBattle().getSelected();
        Card card = battleCard.getCard();
        System.out.println(card.toString());

    }

    public void showGraveyard() throws IllegalActionException {
        handler = new GraveYardMenuHandler();
        int turn = DataCenter.getInstance().getCurrentBattle().getTurn();
        ArrayList<BattleCard> graveYard = DataCenter.getInstance().getCurrentBattle().getBattleField().getGraveYard(turn % 2);
        if (graveYard.size() == 0) {
            throw new IllegalActionException("graveyard empty");
        } else {
            int i = 1;
            for (BattleCard battleCard : graveYard) {
                Card card = battleCard.getCard();
                System.out.println(i + ". " + card.getName() + " : " + card.getDescription() + "\n");
                i++;
            }
        }
    }

    public void opponentGraveYard() throws IllegalActionException {
        handler = new GraveYardMenuHandler();
        int turn = DataCenter.getInstance().getCurrentBattle().getTurn();
        ArrayList<BattleCard> graveYard = DataCenter.getInstance().getCurrentBattle().getBattleField().getGraveYard((turn + 1) % 2);
        if (graveYard.size() == 0) {
            throw new IllegalActionException("graveyard empty");
        } else {
            int i = 1;
            for (BattleCard battleCard : graveYard
            ) {
                Card card = battleCard.getCard();
                System.out.println(i + ". " + card.getName() + " : " + card.getDescription() + "\n");
                i++;
            }
        }
    }


    public void summon() throws IllegalActionException {
        DataCenter.getInstance().getCurrentBattle().summon();
        System.out.println("summoned successfully");
    }

    public void surrender() {
        DataCenter.getInstance().getCurrentBattle().surrender();
        DataCenter.getInstance().setCurrentBattle(null);
        handler = new MainMenuHandler();
    }

    public void winByCheat(int i) {
        DataCenter.getInstance().getCurrentBattle().winGame(i);
        DataCenter.getInstance().setCurrentBattle(null);
        handler = new MainMenuHandler();
    }

    public void createDeck(String group) throws IllegalActionException {
        DataCenter.getInstance().getCurrentUser().createDeck(group);
        System.out.println("deck created successfully!");
    }

    public void deleteDeck(String group) throws IllegalActionException {
        DataCenter.getInstance().getCurrentUser().deleteDeck(group);
        System.out.println("deck deleted successfully!");
    }

    public void setActive(String group) throws IllegalActionException {
        DataCenter.getInstance().getCurrentUser().setActiveDeck(group);
        System.out.println("deck activated successfully");
    }

    public void addCard(String cardName, String deckName, boolean side) throws IllegalActionException {
        DataCenter.getInstance().getCurrentUser().addCardToDeck(cardName, deckName, side);
        System.out.println("card added to deck successfully");
    }

    public void removeCard(String cardName, String deckName, boolean side) throws IllegalActionException {
        DataCenter.getInstance().getCurrentUser().removeCardFromDeck(cardName, deckName, side);
        System.out.println("card removed form deck successfully");
    }

    public void showAll() {
        Deck activeDeck = DataCenter.getInstance().getCurrentUser().getActiveDeck();
        System.out.println("Decks:\nActive deck:");
        ArrayList<Deck> otherDecks = DataCenter.getInstance().getCurrentUser().getAllDecks();
        if (activeDeck != null) {
            System.out.println(activeDeck.getName() + ": " + activeDeck.getMainCount() + ", " + activeDeck.getSideCount() + ", " + (activeDeck.isDeckValid() ? "valid" : "invalid"));
            otherDecks.remove(activeDeck);
        }
        System.out.println("Other decks:");
        for (Deck deck : otherDecks
        ) {
            System.out.println(deck.getName() + ": " + deck.getMainCount() + ", " + deck.getSideCount() + ", " + (deck.isDeckValid() ? "valid" : "invalid"));
        }


    }

    public void showDeck(String deckName, boolean side) {
        User u = DataCenter.getInstance().getCurrentUser();
        for (Deck deck : u.getAllDecks()) {
            if (deck.getName().equals(deckName)) {
                HashMap<String, Integer> cards;
                if (side) {
                    cards = deck.getMainCards();
                } else {
                    cards = deck.getSideCards();
                }
                ArrayList<Card> monsters = new ArrayList<>();
                ArrayList<Card> spstrps = new ArrayList<>();
                for (String cardName : cards.keySet()) {
                    Card card = DataCenter.getInstance().getCard(cardName);
                    if (card.getType() == CardType.MONSTER) {
                        monsters.add(card);
                    } else {
                        spstrps.add(card);
                    }
                }
                System.out.println("Deck: " + deckName);
                System.out.println(side ? "Side" : "Main" + "deck:");
                System.out.println("Monsters:");
                for (Card c : monsters) {
                    System.out.println(c.getName()+" : "+c.getDescription());
                }
                System.out.println("Spell and Traps:");
                for (Card c : spstrps) {
                    System.out.println(c.getName()+" : "+c.getDescription());
                }
                return;
            }
        }

    }


    public void exit() {
        handler = new MainMenuHandler();
    }


    public User getCurrentUser() {
        return DataCenter.getInstance().getCurrentUser();
    }


    public void newDuel(String username, int round) throws IllegalActionException {
        DataCenter d = DataCenter.getInstance();
        User currentUser = d.getCurrentUser();
        if (d.getUser(username) == null) {
            throw new IllegalActionException("there is no player with this username");
        }
        User secondPlayer = d.getUser(username);
        if (round != 3 && round != 1) {
            throw new IllegalActionException("number of rounds is not supported");
        }
        if (currentUser.getActiveDeck() == null || !currentUser.getActiveDeck().isDeckValid()) {
            throw new IllegalActionException(getCurrentUser().getUsername() + "’s deck is invalid");
        }
        if (secondPlayer.getActiveDeck() == null || !secondPlayer.getActiveDeck().isDeckValid()) {
            throw new IllegalActionException(username + "’s deck is invalid");
        }
        d.setCurrentBattle(new Battle(currentUser, secondPlayer, round));
        DuelMenuHandler h = new DuelMenuHandler();
        h.printBattle(d.getCurrentBattle());
        handler = h;
    }

    public void export(String cardName) throws IllegalActionException {
        DataCenter.getInstance().export(cardName);
        System.out.println("card exported successfully");
    }

    public void importCard(String cardName) throws IllegalActionException {
        DataCenter.getInstance().importCard(cardName);
        System.out.println("card imported successfully");
    }

    public void showCardDeckMenu() {
        User user = DataCenter.getInstance().getCurrentUser();
        HashMap<String, Integer> cards = user.getCards();
        ArrayList<String> cardNames = new ArrayList<>(cards.keySet());
        Collections.sort(cardNames);
        for (String cardName : cardNames) {
            Card card = DataCenter.getInstance().getCard(cardName);
            System.out.println(cardName + ":" + card.getDescription());
        }
    }

    public Phases nextPhase() {
        return DataCenter.getInstance().getCurrentBattle().nextPhase();
    }


    public void setMonster() throws IllegalActionException {
        DataCenter.getInstance().getCurrentBattle().setMonster();
        System.out.println("set successfully");
    }

    public void setSpell() throws IllegalActionException {
        DataCenter.getInstance().getCurrentBattle().setSpell();
        System.out.println("set successfully");
    }

    public void setTrap() throws IllegalActionException {
        DataCenter.getInstance().getCurrentBattle().setTrap();
        System.out.println("set successfully");
    }

    public void increaseLP(int parseInt) {
        DataCenter.getInstance().getCurrentBattle().getCurrentPlayer().increaseLP(parseInt);
    }

    public void increaseMoney(int parseInt) {
        DataCenter.getInstance().getCurrentBattle().getCurrentPlayer().getUser().increaseBalance(parseInt);
    }

    public void killSelected() {
        DataCenter.getInstance().getCurrentBattle().killSelected();
    }
}
