package app.model.Cards;

import app.model.CardTypes.CardType;

public class Trap extends Card {
    String icon;
    String status;
    public Trap(String name,String icon ,String description,String status ,int price) {
        super(CardType.TRAP, name, description, price);
        this.status = status;
        this.icon = icon;
    }//Name,Type ,Icon (Property),Description,Status,Price

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String toString() {

            return   "Name: "+ name + "\n" +
                      type + "\n"+
                     "Type:"+ icon + "\n"+
                     "Description: " + description + "\n";
        }
    }

