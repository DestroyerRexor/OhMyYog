package com.example.evidenciafinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductViewActivity extends AppCompatActivity {

    String[] item = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;
    ImageView returnArrowImageView;
    ImageView cartImageView;
    Button  addToCartButton;
    Product selectProduct;

    private static String quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        returnArrowImageView = findViewById(R.id.returnArrowImageView);
        cartImageView = findViewById(R.id.cartImageView);

        cartImageView.setImageAlpha(0);

        returnArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addToCartButton = findViewById(R.id.addToCartButton);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        adapterItem = new ArrayAdapter<String>(this, R.layout.list_item, item);

        autoCompleteTextView.setAdapter(adapterItem);
        autoCompleteTextView.setText(item[0].toString(), false);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });

        getSelectedProduct();
        setValues();

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showCart = new Intent(getApplicationContext(), CartViewActivity.class);
                showCart.putExtra("id", selectProduct.getId());
                quantity = String.valueOf(autoCompleteTextView.getText());
                CartViewActivity.getSelectedProduct(selectProduct);

                List<CartViewActivity.CartItem> cartItems = ShoppingCartSingleton.getInstance().getArray();


                CartViewActivity.CartItem existingItem = null;

                for (CartViewActivity.CartItem item : cartItems) {
                    if (item.getName().equals(selectProduct.getProductName())) {
                        existingItem = item;
                        break;
                    }
                }

                if (existingItem != null) {
                    existingItem.setQuantity(existingItem.getQuantity() + Integer.parseInt(quantity));
                } else {
                    CartViewActivity.CartItem newItem = new CartViewActivity.CartItem(selectProduct.getProductName(), selectProduct.getPrice(), Integer.parseInt(quantity), selectProduct.getURLImage());
                    ShoppingCartSingleton.getInstance().addValue(newItem);
                }

                System.out.println(ShoppingCartSingleton.getInstance().getArray());

                startActivity(showCart);
            }
        });
    }

    private void getSelectedProduct() {
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        selectProduct = ProductListActivity.productList.get(Integer.parseInt(parsedStringID));
    }

    private void setValues() {
        ImageView productImageView = findViewById(R.id.productImageView);
        TextView productNameTextView = findViewById(R.id.productNameTextView);
        TextView productPriceTextView = findViewById(R.id.productPriceTextView);
        TextView productDescriptionSubtitleTextView = findViewById(R.id.productDescriptionSubtitleTextView);

        Glide.with(getApplicationContext()).load(ProductCard.clearURLImageAPI(selectProduct.getURLImage())).override(320, 320).into(productImageView);
        productNameTextView.setText(selectProduct.getProductName());
        productPriceTextView.setText("$" + selectProduct.getPrice());
        productDescriptionSubtitleTextView.setText(selectProduct.getDescription());
    }

    public static String getQuantity(){
        return quantity;
    }
}