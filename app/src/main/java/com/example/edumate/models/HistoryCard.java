package com.example.edumate.models;

public class HistoryCard {
    private String title;
    private String description;

    public HistoryCard(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
