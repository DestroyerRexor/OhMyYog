package com.example.evidenciafinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

public class ProductViewActivity extends AppCompatActivity {

    String[] item = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;
    ImageView returnArrowImageView;
    ImageView cartImageView;

    Product selectProduct;

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
                startActivity(new Intent(ProductViewActivity.this, ProductListActivity.class));
            }
        });

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

    }

    private void getSelectedProduct() {
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        selectProduct = ProductListActivity.productList.get(Integer.valueOf(parsedStringID));
    }

    private void setValues() {
        // TODO: Hacer referencia al objeto selectProduct para obtener los valores y asignarlos.

        /*
        *   TextView tv = (TextView) findViewById(R.id.shapeName);
            ImageView iv = (ImageView) findViewById(R.id.shapeImage);
            tv.setText(selectedShape.getName());
            iv.setImageResource(selectedShape.getImage());
        *
        * */
    }
}