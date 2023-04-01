package com.example.evidenciafinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.util.DisplayMetrics;
import android.widget.GridView;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    public static ArrayList<Product> productList = new ArrayList<Product>();
    private GridView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        GridView gridView = findViewById(R.id.grid);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int columnWidth = screenWidth / 2;
        gridView.setColumnWidth(columnWidth);

        setupData();
        setUpList();
        setUpOnClickListener();
    }


    private void setupData() {
        Product p1 = new Product("01", "Helados", "2 Sabores, Taro & Natural Stevia integrados", "159", "Taro & Natural Stevia", R.drawable.icecream);
        Product p2 = new Product("02", "Helados", "Helado KETO & Vegano sabor Vinilla, cero Azúcar (Endulzado 100% con Monk Fruit) Vegana (Base leche de Almendra)", "159", "Vainilla KETO", R.drawable.icecream);
        Product p3 = new Product("03", "Helados", "Helado KETO & Vegano sabor Cacao Endulzado 100% con Monk Fruit (cero azúcar) Presentación de 300 gramos. *Contiene Leche de Almendra ", "159", "Cacao KETO", R.drawable.icecream);
        Product p4 = new Product("04", "Helados", "Una deliciosa mezcla de sabores dulces y crujientes, perfecta para cualquier ocasión.", "129", "Galleta Lotus", R.drawable.icecream);

        productList.add(p1);
        productList.add(p2);
        productList.add(p3);
        productList.add(p4);
    }

    private void setUpList() {
        listView = (GridView) findViewById(R.id.grid);
        ProductCard card = new ProductCard(getApplicationContext(), 0, productList);
        listView.setAdapter(card);
    }

    private  void setUpOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Product selectShape = (Product) (listView.getItemAtPosition(position));
                Intent showDetail = new Intent(getApplicationContext(), ProductViewActivity.class);
                showDetail.putExtra("id",selectShape.getId());
                startActivity(showDetail);
            }
        });
    }
}