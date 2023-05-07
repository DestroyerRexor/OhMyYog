package com.example.evidenciafinalapp;

import java.util.Calendar;

public class DeliveryInformation {

    private String name;
    private String phone;
    private String email;
    private String deliveryDate;
    private int deliveryHour;
    private int deliveryMinute;
    private String deliveryBranch;

    public DeliveryInformation(String name, String phone, String email, String deliveryDate,
                               int deliveryHour, int deliveryMinute, String deliveryBranch) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.deliveryDate = deliveryDate;
        this.deliveryHour = deliveryHour;
        this.deliveryMinute = deliveryMinute;
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

    public int getDeliveryHour() {
        return deliveryHour;
    }

    public void setDeliveryHour(int deliveryHour) {
        this.deliveryHour = deliveryHour;
    }

    public int getDeliveryMinute() {
        return deliveryMinute;
    }

    public void setDeliveryMinute(int deliveryMinute) {
        this.deliveryMinute = deliveryMinute;
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
                ", deliveryMinute=" + deliveryMinute +
                ", deliveryBranch='" + deliveryBranch + '\'' +
                '}';
    }
}
