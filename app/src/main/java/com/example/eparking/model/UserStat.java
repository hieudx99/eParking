package com.example.eparking.model;

import java.io.Serializable;
import java.util.List;

public class UserStat extends User implements Serializable {
    private int rentTimes;
    private double rentTotal;

    public UserStat(int rentTimes, double rentTotal) {
        this.rentTimes = rentTimes;
        this.rentTotal = rentTotal;
    }

    public UserStat(int id, String username, String password, String fullname, String identityCard, String email, String telephone, String address, Role role, List<Car> listCar, int rentTimes, double rentTotal) {
        super(id, username, password, fullname, identityCard, email, telephone, address, role, listCar);
        this.rentTimes = rentTimes;
        this.rentTotal = rentTotal;
    }

    public UserStat(String username, String password, String fullname, String identityCard, String email, String telephone, String address, int rentTimes, double rentTotal) {
        super(username, password, fullname, identityCard, email, telephone, address);
        this.rentTimes = rentTimes;
        this.rentTotal = rentTotal;
    }

    public int getRentTimes() {
        return rentTimes;
    }

    public void setRentTimes(int rentTimes) {
        this.rentTimes = rentTimes;
    }

    public double getRentTotal() {
        return rentTotal;
    }

    public void setRentTotal(double rentTotal) {
        this.rentTotal = rentTotal;
    }
}
