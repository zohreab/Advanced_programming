package app.model.Cards;

import app.model.CardTypes.CardType;
import app.model.CardTypes.MonsterType;

public class Monster extends Card {
    int level;
    String MonsterType;
    String attribute;
    String cardType;
    int ATK;
    int DEF;

    public Monster(String s, int parseInt, String s1, String s2, String s3, int parseInt1, int parseInt2, String description, int parseInt3) {
        super(CardType.MONSTER, s, description, parseInt3);
        this.level = parseInt;
        this.attribute = s1;
        this.MonsterType = s2;
        cardType = s3;
        ATK = parseInt1;
        DEF = parseInt2;
    }//Name,Level,Attribute, Monster Type , Card Type ,Atk,Def,Description,Price

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getMonsterType() {
        return MonsterType;
    }

    public void setMonsterType(String monsterType) {
        MonsterType = monsterType;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getATK() {
        return ATK;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public int getDEF() {
        return DEF;
    }

    public void setDEF(int DEF) {
        this.DEF = DEF;
    }

    @Override
    public String toString() {
        return   "name: "+ name + "\n" +
                 "Level: "+ level + "\n"+
                 "Type: "+ MonsterType + "\n"+
                 "ATK: "+ ATK + "\n"+
                 "DEF: "+ DEF + "\n"+
                 "Description: " + description + "\n";
    }
}
