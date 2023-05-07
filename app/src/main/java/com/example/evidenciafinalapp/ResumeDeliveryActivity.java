package com.example.evidenciafinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ResumeDeliveryActivity extends AppCompatActivity {

    Button keepShopping;
    private ImageView cartImageView;
    private ImageView returnArrowImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_delivery);

        keepShopping = findViewById(R.id.keepShopping);

        keepShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shoppingList = new Intent(getApplicationContext(), ProductListActivity.class);
                startActivity(shoppingList);
            }
        });

        returnArrowImageView = findViewById(R.id.returnArrowImageView);
        returnArrowImageView.setImageAlpha(0);
        cartImageView = findViewById(R.id.cartImageView);
        cartImageView.setImageAlpha(0);
    }
}