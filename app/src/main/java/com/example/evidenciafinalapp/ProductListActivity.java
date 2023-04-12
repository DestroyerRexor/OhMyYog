package com.example.evidenciafinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.util.DisplayMetrics;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Queue;

public class ProductListActivity extends AppCompatActivity {

    public static ArrayList<Product> productList = new ArrayList<Product>();
    private GridView listView;

    private static final String API_URL = "https://test-project-fire-ca86c-default-rtdb.firebaseio.com/products.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        GridView gridView = findViewById(R.id.grid);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int columnWidth = screenWidth / 2;
        gridView.setColumnWidth(columnWidth);

        // setupData();
        fetchData();
        setUpList();
        setUpOnClickListener();
    }


    /*
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
    */

    private void fetchData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(
            Request.Method.GET,
            API_URL,
            new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject singleObject = array.getJSONObject(i);
                            Product product = new Product(
                                    singleObject.getString("id"),
                                    singleObject.getString("category"),
                                    singleObject.getString("description"),
                                    singleObject.getString("price"),
                                    singleObject.getString("productName"),
                                    R.drawable.icecream
                            );

                            System.out.println(product.toString());
                            productList.add(product);
                        }
                    } catch (Exception e) {

                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("api", "onErrorResponse "+error.getLocalizedMessage());
                }
            }
        );
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