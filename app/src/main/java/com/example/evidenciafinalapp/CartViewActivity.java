package com.example.evidenciafinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CartViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);
    }

    public static void getSelectedProduct(Product selectProduct) {
        System.out.println("Nombre: " + selectProduct.getProductName() + " Precio: $" + selectProduct.getPrice() + " Cantidad:" + ProductViewActivity.getQuantity());
    }
}