package com.example.eparking.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private int id;
    private String fullname;
    private String identityCard;
    private String telephone;
    private String address;
    private String username;
    private String password;


    public Customer(String fullname, String identityCard, String telephone, String address, String username, String password) {
        this.fullname = fullname;
        this.identityCard = identityCard;
        this.telephone = telephone;
        this.address = address;
        this.username = username;
        this.password = password;
    }

}
