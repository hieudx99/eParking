package com.example.eparking.model.dto;

import java.io.Serializable;

public class UserCredential implements Serializable {
    private String username;
    private String password;

    public UserCredential() {
    }

    public UserCredential(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
