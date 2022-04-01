package com.example.eparking.model;

import java.io.Serializable;

public class Car implements Serializable {
    private int id;
    private String name;
    private String color;
    private String brand;
    private String licensePlate;
    private int seatNumber;

    public Car() {
    }

    public Car(int id, String name, String color, String brand, String licensePlate, int seatNumber) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.brand = brand;
        this.licensePlate = licensePlate;
        this.seatNumber = seatNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
}
