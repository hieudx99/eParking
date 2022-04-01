package com.example.eparking.model;

import java.io.Serializable;

public class Bill implements Serializable {
    private int id;
    private String createDate;
    private String startTime;
    private String endTime;
    private double total;
    private String paymentStatus;
    private User user;
    private Car car;
    private ParkingSlot parkingSlot;
    private PaymentMethod paymentMethod;

    public Bill(int id, String createDate, String startTime, String endTime, double total, String paymentStatus,
                User user, Car car, ParkingSlot parkingSlot, PaymentMethod paymentMethod) {
        this.id = id;
        this.createDate = createDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.total = total;
        this.paymentStatus = paymentStatus;
        this.user = user;
        this.car = car;
        this.parkingSlot = parkingSlot;
        this.paymentMethod = paymentMethod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public void setParkingSlot(ParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
