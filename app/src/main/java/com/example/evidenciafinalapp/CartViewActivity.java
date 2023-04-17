package com.example.evidenciafinalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CartViewActivity extends AppCompatActivity {

    private int itemNameX = 1;

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private int total = 0;
    private List<CartItem> cartItems = ShoppingCartSingleton.getInstance().getArray();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);

        final TextView priceView = (TextView) findViewById(R.id.totalPrice);

        for (CartItem item : cartItems) {
            total += (item.quantity * item.price);
        }

        priceView.setText("$" + String.valueOf(total));

        cartRecyclerView = findViewById(R.id.cart_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cartRecyclerView.setLayoutManager(linearLayoutManager);

        cartAdapter = new CartAdapter(cartItems, position -> deleteItem(position));
        cartRecyclerView.setAdapter(cartAdapter);

        // TODO: Add items to cartItems list and update cartAdapter
    }

    private void deleteItem(int position) {
        cartItems.remove(position);
        cartAdapter.notifyItemRemoved(position);
    }

    public static void getSelectedProduct(Product selectProduct) {
        System.out.println("Nombre: " + selectProduct.getProductName() + " Precio: $" + selectProduct.getPrice() + " Cantidad:" + ProductViewActivity.getQuantity());
    }

    public static class CartItem {
        private String name;
        private double price;
        private int quantity;

        public CartItem(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
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
    }
}