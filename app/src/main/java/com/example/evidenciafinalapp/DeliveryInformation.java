package com.example.evidenciafinalapp;

import java.util.Calendar;

public class DeliveryInformation {

    private String name;
    private String phone;
    private String email;
    private String deliveryDate;
    private String deliveryHour;
    private String deliveryBranch;

    public DeliveryInformation(String name, String phone, String email, String deliveryDate,
                               String deliveryHour, String deliveryBranch) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.deliveryDate = deliveryDate;
        this.deliveryHour = deliveryHour;
        this.deliveryBranch = deliveryBranch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryHour() {
        return deliveryHour;
    }

    public void setDeliveryHour(String deliveryHour) {
        this.deliveryHour = deliveryHour;
    }
    public String getDeliveryBranch() {
        return deliveryBranch;
    }

    public void setDeliveryBranch(String deliveryBranch) {
        this.deliveryBranch = deliveryBranch;
    }

    @Override
    public String toString() {
        return "------------------------------------------------------------DeliveryInformation{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", deliveryHour=" + deliveryHour +
                ", deliveryBranch='" + deliveryBranch + '\'' +
                '}';
    }
}
