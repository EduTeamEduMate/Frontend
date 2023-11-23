package com.example.edumate.models;

public class HistoryCard {
    private String name;
    private int id;

    public HistoryCard(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

}
