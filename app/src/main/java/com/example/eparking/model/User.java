package com.example.eparking.model;


import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private int id;
    private String fullname;
    private String identityCard;
    private String telephone;
    private String address;
    private String username;
    private String password;
    private Role role;
    private List<Car> listCar;

    public User() {
    }

    public User(int id, String username, String password, String fullname, String identityCard, String telephone, String address, Role role, List<Car> listCar) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.identityCard = identityCard;
        this.telephone = telephone;
        this.address = address;
        this.role = role;
        this.listCar = listCar;
    }

    public User(String username, String password, String fullname, String identityCard, String telephone, String address) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.identityCard = identityCard;
        this.telephone = telephone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Car> getListCar() {
        return listCar;
    }

    public void setListCar(List<Car> listCar) {
        this.listCar = listCar;
    }
}
