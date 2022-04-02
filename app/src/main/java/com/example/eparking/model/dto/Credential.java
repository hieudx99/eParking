package com.example.eparking.model.dto;

import java.io.Serializable;

public class Credential implements Serializable {
    private String username;
    private String password;

    public Credential() {
    }

    public Credential(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
