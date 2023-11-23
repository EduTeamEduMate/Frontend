package com.example.edumate.models;

public class TokenResponse {
    private String access_token;
    private String token_type;

    private UserDataFromLogin user;
    public TokenResponse(String access_token, String token_type) {
        this.access_token = access_token;
        this.token_type = token_type;
    }

    public UserDataFromLogin getUser() {
        return user;
    }

    public void setUser(UserDataFromLogin user) {
        this.user = user;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}
