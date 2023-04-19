package com.example.evidenciafinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ResumeDeliveryActivity extends AppCompatActivity {

    Button keepShopping;
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
    }
}