package app.model.Cards;

import app.model.CardTypes.CardType;

public class Card {
    CardType type;
    String name;
    String description;
    int price;

    public Card(CardType type, String name, String description, int price) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
