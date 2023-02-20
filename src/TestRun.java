import app.model.Cards.Monster;
import app.model.Cards.Spell;
import app.model.Cards.Trap;
import app.model.User;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class TestRun {

    private static final String pathToMonsters = "resources/cards/Monster.csv";
    private static final String pathToUsers = "resources/users/";
    private static final String pathToSpellTrap = "resources/cards/SpellTrap.csv";

    public static void main(String[] args) {

        HashMap<String, Spell> spells = new HashMap<>();
        HashMap<String, Trap> traps = new HashMap<>();
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(pathToSpellTrap)));
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                if (record[1].equals("Trap"))
                    traps.put(record[0], new Trap(record[0], record[2], record[3], record[4], Integer.parseInt(record[5])));
                else
                    spells.put(record[0], new Spell(record[0], record[2], record[3], record[4], Integer.parseInt(record[5])));
            }//Name,Type ,Icon (Property),Description,Status,Price

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        for (String  trapName : traps.keySet()
        ) {
            Trap trap = traps.get( trapName);
            Gson gson = new Gson();
            String trapJson = gson.toJson(trap);
            try {
                FileWriter myWriter = new FileWriter("resources/cards/traps/" +  trapName + ".json");
                myWriter.write(trapJson);
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (String  spellName : spells.keySet()
        ) {
            Spell spell = spells.get( spellName);
            Gson gson = new Gson();
            String spellJson = gson.toJson(spell);
            try {
                FileWriter myWriter = new FileWriter("resources/cards/spells/" +  spellName + ".json");
                myWriter.write(spellJson);
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
