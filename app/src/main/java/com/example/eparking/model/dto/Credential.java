package com.example.eparking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class Credential {
    private String username;
    private String password;

    public Credential() {
    }

    public Credential(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
