package app.model.Battle;

import java.util.ArrayList;

public class BattleField {
    private ArrayList<BattleCard> graveYard0 = new ArrayList<>();
    private ArrayList<BattleCard> graveYard1 = new ArrayList<>();
    private BattleCard fieldZone0;
    private BattleCard fieldZone1;
    private BattleCard[] monsterCards0 = new BattleCard[5];
    private BattleCard[] monsterCards1 = new BattleCard[5];
    private BattleCard[] spellTrapCards0 = new BattleCard[5];
    private BattleCard[] spellTrapCards1 = new BattleCard[5];


    public BattleCard[] getMonsterZone(int i) {
        if (i == 0)
            return monsterCards0;
        return monsterCards1;
    }

    public BattleCard[] getSpellZone(int i) {
        if (i == 0)
            return spellTrapCards0;
        return spellTrapCards1;
    }

    public BattleCard getFieldZone(int i) {
      if (i == 0)
        return fieldZone0;
      return fieldZone1;
    }

    public ArrayList<BattleCard> getGraveYard(int i) {
        if (i == 0)
            return graveYard0;
        return graveYard1;
    }

    public void addToGraveYard(int i,BattleCard battleCard) {
        if (i == 0)
            graveYard0.add(battleCard);
        else
            graveYard1.add(battleCard);
    }
}
