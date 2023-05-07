package com.example.evidenciafinalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartViewActivity extends AppCompatActivity {

    private int itemNameX = 1;

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private int total = 0;
    private List<CartItem> cartItems = ShoppingCartSingleton.getInstance().getArray();
    ImageView cartImageView;
    ImageView returnArrowImageView;
    Button keepShopping;
    Button finishOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);

        final TextView priceView = (TextView) findViewById(R.id.totalPrice);

        cartImageView = findViewById(R.id.cartImageView);
        returnArrowImageView = findViewById(R.id.returnArrowImageView);
        keepShopping = findViewById(R.id.keepShopping);
        finishOrder = findViewById(R.id.finishOrder);

        cartImageView.setImageAlpha(0);
        returnArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        for (CartItem item : cartItems) {
            total += (item.quantity * item.price);
        }

        priceView.setText("$" + String.valueOf(total));

        cartRecyclerView = findViewById(R.id.cart_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cartRecyclerView.setLayoutManager(linearLayoutManager);

        cartAdapter = new CartAdapter(cartItems, position -> deleteItem(position));
        cartRecyclerView.setAdapter(cartAdapter);

        keepShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shoppingList = new Intent(getApplicationContext(), ProductListActivity.class);
                startActivity(shoppingList);
            }
        });

        finishOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                DatabaseReference ordersRef = myRef.child("orders").push();

                ordersRef.setValue(cartItems);

                ShoppingCartSingleton.getInstance().setEmptyCart();

                Intent resumeDelivery = new Intent(getApplicationContext(), ResumeDeliveryActivity.class);
                startActivity(resumeDelivery);
            }
        });

        // TODO: Add items to cartItems list and update cartAdapter
    }

    private void deleteItem(int position) {
        final TextView priceView = (TextView) findViewById(R.id.totalPrice);

        cartItems.remove(position);
        cartAdapter.notifyItemRemoved(position);
        total = 0;
        for (CartItem item : cartItems) {
            total += (item.quantity * item.price);
        }

        priceView.setText("$" + String.valueOf(total));
    }

    public static void getSelectedProduct(Product selectProduct) {
        System.out.println("Nombre: " + selectProduct.getProductName() + " Precio: $" + selectProduct.getPrice() + " Cantidad:" + ProductViewActivity.getQuantity());
    }

    public static class CartItem {
        private String name;
        private double price;
        private int quantity;
        private String image;

        public CartItem(String name, double price, int quantity, String image) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        public String getImage() { return image; }
        public void setImage(String image) { this.image = image;}
    }
}