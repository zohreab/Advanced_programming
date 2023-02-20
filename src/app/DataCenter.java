package app;

import app.model.Battle.Battle;
import app.model.CardTypes.CardType;
import app.model.Cards.Card;
import app.model.Cards.Monster;
import app.model.Cards.Spell;
import app.model.Cards.Trap;
import app.model.IllegalActionException;
import app.model.User;
import app.utils.DatabaseManager;

import java.util.ArrayList;
import java.util.HashMap;

public class DataCenter {
    private static DataCenter dataCenter;
    //تو این بخش همه دیتاهایی که مورد نیاز هست رو (هش‌مپ ها و اری‌لیست ها) اینجا اضافه میشه
    private HashMap<String, User> users;
    private HashMap<String, Monster> monsters;
    private HashMap<String, Spell> spells;
    private HashMap<String, Trap> traps;
    private HashMap<String, Card> cards;
    private User currentUser;
    private Battle currentBattle;

    private DataCenter() {
        users = new HashMap<>();
    }

    public static DataCenter getInstance() {
        if (dataCenter == null) {
            dataCenter = new DataCenter();
        }
        return dataCenter;
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }


    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    public HashMap<String, Monster> getMonsters() {
        return monsters;
    }

    public HashMap<String, Spell> getSpells() {
        return spells;
    }

    public HashMap<String, Trap> getTraps() {
        return traps;
    }

    public HashMap<String, Card> getCards() {
        return cards;
    }

    public void loadData() {
        monsters = DatabaseManager.loadMonsters();
        traps = DatabaseManager.loadTraps();
        spells = DatabaseManager.loadSpells();
        users = DatabaseManager.loadUsers();
        cards = new HashMap<>();
        for (Trap trap : traps.values()) {
            cards.put(trap.getName(), trap);
        }
        for (Monster monster : monsters.values()) {
            cards.put(monster.getName(), monster);
        }
        for (Spell spell : spells.values()) {
            cards.put(spell.getName(), spell);
        }
    }

    public void storeData() {
        DatabaseManager.storeUsers(users);
    }

    public boolean nicknameExisted(String nickname) {
        for (User u : users.values()) {
            if (u.getNickname().equals(nickname))
                return true;
        }
        return false;
    }

    public Battle getCurrentBattle() {
        return currentBattle;
    }

    public void setCurrentBattle(Battle currentBattle) {
        this.currentBattle = currentBattle;
    }

    public Card getCard(String cardName) {
        return cards.get(cardName);
    }

    public void export(String cardName) throws IllegalActionException {
        if (!cards.containsKey(cardName)) {
            throw new IllegalActionException("there is no card with this name");
        }
        DatabaseManager.exportCard(cards.get(cardName));
    }

    public void importCard(String cardName) throws IllegalActionException {
        if (cards.containsKey(cardName)) {
            throw new IllegalActionException("there is a card with this name");
        }
        Card c = DatabaseManager.importCard(cardName);
        cards.put(c.getName(), c);
        if (c.getType() == CardType.TRAP)
            traps.put(c.getName(), (Trap) c);
        if (c.getType() == CardType.MONSTER) {
            assert c instanceof Monster;
            monsters.put(c.getName(), (Monster) c);
        }
        if (c.getType() == CardType.SPELL) {
            assert c instanceof Spell;
            spells.put(c.getName(), (Spell) c);
        }
    }
}
