package com.example.edumate.models;

public class SUserData {

    private static SUserData instance;
    private String username;
    private String email;

    private int id;


    private SUserData() {}

    public static synchronized SUserData getInstance() {
        if (instance == null) {
            instance = new SUserData();
        }
        return instance;
    }

    public void setUser(String username, String email, int id) {
        this.username = username;
        this.email = email;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }
}

