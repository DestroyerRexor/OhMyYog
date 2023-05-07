package com.example.evidenciafinalapp;

import java.util.ArrayList;

public class ShoppingCartSingleton {
    private static ShoppingCartSingleton mInstance = null;

    private ArrayList<CartViewActivity.CartItem> mArray;

    private ShoppingCartSingleton(){
        mArray = new ArrayList<CartViewActivity.CartItem>();
    }

    public static ShoppingCartSingleton getInstance(){
        if(mInstance == null) {
            mInstance = new ShoppingCartSingleton();
        }
        return mInstance;
    }

    public ArrayList<CartViewActivity.CartItem> getArray() {
        return this.mArray;
    }

    public void setArray(ArrayList<CartViewActivity.CartItem> value){
        mArray = value;
    }

    public void addValue(CartViewActivity.CartItem value){
        mArray.add(value);
    }

    public void setEmptyCart() {
        mArray.clear();
    }
}
