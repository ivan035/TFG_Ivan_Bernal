package com.example.aprendejugando.memory;

public class Card {
    //This class will create a Card object wich has the ID of the card and his value

    Integer id;
    Integer value;

    public Card(Integer id, Integer value) {
        this.id = id;
        this.value = value;
    }
    public Card(Integer id) {
        this.id = id;
        this.value = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
