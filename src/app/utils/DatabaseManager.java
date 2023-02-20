package app.utils;

import app.model.Cards.Card;
import app.model.Cards.Monster;
import app.model.Cards.Spell;
import app.model.Cards.Trap;
import app.model.User;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class DatabaseManager {
    private static final String pathToMonsters = "resources/cards/Monsters/";
    private static final String pathToUsers = "resources/users/";
    private static final String pathToTraps = "resources/cards/Traps/";
    private static final String pathToSpells = "resources/cards/Spells/";

    public static HashMap<String, User> loadUsers() {
        HashMap<String, User> users = new HashMap<>();
        File file = new File(pathToUsers);
        try {
            String[] files = file.list();
            if (files == null)
                return null;
            for (String path : files) {
                String userJson = readFileAsString(pathToUsers + path);
                User user = new Gson().fromJson(userJson, User.class);
                users.put(user.getUsername(), user);
            }
        } catch (NullPointerException | IOException e) {
            System.out.println(e);
        }
        return users;
    }

    private static String readFileAsString(String fileName) throws IOException {
        String data;
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public static HashMap<String, Monster> loadMonsters() {
        HashMap<String, Monster> monsters = new HashMap<>();
        File file = new File(pathToMonsters);
        try {
            String[] files = file.list();
            if (files == null)
                return null;
            for (String path : files) {
                String monsterJson = readFileAsString(pathToMonsters + path);
                Monster monster = new Gson().fromJson(monsterJson, Monster.class);
                monsters.put(monster.getName(), monster);
            }
        } catch (NullPointerException | IOException e) {
            System.out.println(e);
        }
        return monsters;
    }

    public static HashMap<String, Trap> loadTraps() { //Name,Type ,Icon (Property),Description,Status,Price
        HashMap<String, Trap> traps = new HashMap<>();
        File file = new File(pathToTraps);
        try {
            String[] files = file.list();
            if (files == null)
                return null;
            for (String path : files) {
                String trapJson = readFileAsString(pathToTraps + path);
                Trap trap = new Gson().fromJson(trapJson, Trap.class);
                traps.put(trap.getName(), trap);
            }
        } catch (NullPointerException | IOException e) {
            System.out.println(e);
        }
        return traps;

    }

    public static HashMap<String, Spell> loadSpells() { //Name,Type ,Icon (Property),Description,Status,Price
        HashMap<String, Spell> spells = new HashMap<>();
        File file = new File(pathToSpells);
        try {
            String[] files = file.list();
            if (files == null)
                return null;
            for (String path : files) {
                String spellJson = readFileAsString(pathToSpells + path);
                Spell spell = new Gson().fromJson(spellJson, Spell.class);
                spells.put(spell.getName(), spell);
            }
        } catch (NullPointerException | IOException e) {
            System.out.println(e);
        }
        return spells;

    }


    public static void storeUsers(HashMap<String, User> users) {
        for (String username : users.keySet()
        ) {
            User user = users.get(username);
            Gson gson = new Gson();
            String userJson = gson.toJson(user);
            Path path = Paths.get(pathToUsers + username + ".json");
            try {
                Files.write(path, userJson.getBytes());
            } catch (IOException ignored) {
            }
        }

    }

    public static void storeMonsters(HashMap<String, Monster> monsters) {
        for (String monsterName : monsters.keySet()
        ) {
            Monster monster = monsters.get(monsterName);
            Gson gson = new Gson();
            String monsterJson = gson.toJson(monster);
            Path path = Paths.get(pathToMonsters + monsterName + ".json");
            try {
                Files.write(path, monsterJson.getBytes());
            } catch (IOException ignored) {
            }
        }

    }

    public static void storeTraps(HashMap<String, Trap> traps) {
        for (String trapName : traps.keySet()
        ) {
            Trap trap = traps.get(trapName);
            Gson gson = new Gson();
            String trapJson = gson.toJson(trap);
            Path path = Paths.get(pathToTraps + trapName + ".json");
            try {
                Files.write(path, trapJson.getBytes());
            } catch (IOException ignored) {
            }
        }

    }

    public static void storeSpells(HashMap<String, Spell> spells) {
        for (String spellName : spells.keySet()
        ) {
            Spell spell = spells.get(spellName);
            Gson gson = new Gson();
            String spellJson = gson.toJson(spell);
            Path path = Paths.get(pathToSpells + spellName + ".json");
            try {
                Files.write(path, spellJson.getBytes());
            } catch (IOException ignored) {
            }
        }

    }

    public static Card importCard(String cardName) {
        String cardJson = null;
        try {
            cardJson = readFileAsString("resources/import/" + cardName+".json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Card card = new Gson().fromJson(cardJson, Card.class);

        switch (card.getType()){
            case MONSTER:
                card = new Gson().fromJson(cardJson, Monster.class);
                break;
            case SPELL:
                card = new Gson().fromJson(cardJson, Spell.class);
                break;
            case TRAP:
                card = new Gson().fromJson(cardJson, Trap.class);
                break;
        }

        return card;
    }

    public static void exportCard(Card card) {
        Gson gson = new Gson();
        String cardJson = gson.toJson(card);
        Path path = Paths.get("resources/export/" + card.getName() + ".json");
        try {
            Files.write(path, cardJson.getBytes());
        } catch (IOException ignored) {
        }
    }


}
