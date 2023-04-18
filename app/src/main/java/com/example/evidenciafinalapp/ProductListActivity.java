package com.example.evidenciafinalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.Manifest;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ProductListActivity extends AppCompatActivity {

    public static ArrayList<Product> productList;
    private GridView gridView;
    private static final String API_URL = "https://test-project-fire-ca86c-default-rtdb.firebaseio.com/products.json";

    private ImageView returnArrowImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        returnArrowImageView = findViewById(R.id.returnArrowImageView);
        returnArrowImageView.setImageAlpha(0);

        gridView = findViewById(R.id.grid);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int columnWidth = screenWidth / 2;
        gridView.setColumnWidth(columnWidth);

        productList = new ArrayList<>();

        new FetchDataTask().execute();
        setUpOnClickListener();
    }

    private class FetchDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            RequestQueue queue = Volley.newRequestQueue(ProductListActivity.this);
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
                                            singleObject.getInt("price"),
                                            singleObject.getString("product_name"),
                                            singleObject.getString("image")
                                    );
                                    productList.add(product);
                                }
                                setUpList();
                            } catch (Exception e) {
                                System.out.println("Ocurrio un error!!! "+e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("api", "onErrorResponse "+error.getLocalizedMessage());
                            Toast.makeText(ProductListActivity.this, "Error: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            queue.add(stringRequest);
            return null;
        }
    }

    private void setUpList() {
        gridView = (GridView) findViewById(R.id.grid);
        ProductCard card = new ProductCard(getApplicationContext(), 0, productList);
        gridView.setAdapter(card);
    }

    private  void setUpOnClickListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Product selectedProduct = (Product) (gridView.getItemAtPosition(position));
                Intent showDetail = new Intent(getApplicationContext(), ProductViewActivity.class);
                showDetail.putExtra("id",selectedProduct.getId());
                startActivity(showDetail);
                System.out.println(selectedProduct.getId());
            }
        });
    }
}