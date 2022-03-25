package com.example.eparking.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Customer {
    private int id;
    private String fullname;
    private String identityCard;
    private String telephone;
    private String address;
    private String username;
    private String password;

    public Customer() {
    }

    public Customer(int id, String fullname, String identityCard, String telephone, String address, String username, String password) {
        this.id = id;
        this.fullname = fullname;
        this.identityCard = identityCard;
        this.telephone = telephone;
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public Customer(String fullname, String identityCard, String telephone, String address, String username, String password) {
        this.fullname = fullname;
        this.identityCard = identityCard;
        this.telephone = telephone;
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
