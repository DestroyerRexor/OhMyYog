package com.example.evidenciafinalapp;

import java.util.List;

public class Order {
    private DeliveryInformation deliveryInformation;
    private List<CartViewActivity.CartItem> cartItems;

    public Order(DeliveryInformation deliveryInformation, List<CartViewActivity.CartItem> cartItems) {
        this.deliveryInformation = deliveryInformation;
        this.cartItems = cartItems;
    }

    public DeliveryInformation getDeliveryInformation() {
        return deliveryInformation;
    }

    public void setDeliveryInformation(DeliveryInformation deliveryInformation) {
        this.deliveryInformation = deliveryInformation;
    }

    public List<CartViewActivity.CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartViewActivity.CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
