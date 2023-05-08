package com.example.evidenciafinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    public static ArrayList<Product> productList;
    private GridView gridView;
    private static final String API_URL = "https://test-project-fire-ca86c-default-rtdb.firebaseio.com/products.json";

    private ImageView returnArrowImageView;
    private String selectedFilter = "Helados";

    private ImageView cartImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        returnArrowImageView = findViewById(R.id.returnArrowImageView);
        returnArrowImageView.setImageAlpha(0);
        cartImageView = findViewById(R.id.cartImageView);
        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductListActivity.this, CartViewActivity.class));
            }
        });

        gridView = findViewById(R.id.grid);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int columnWidth = screenWidth / 2;
        gridView.setColumnWidth(columnWidth);

        productList = new ArrayList<>();

        new FetchDataTask().execute();
        setUpOnClickListener();
        initSearchProducts();

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                switch (position) {
                    case 0: {
                        filterList("Helados");
                    } break;

                    case 1: {
                        filterList("Litros");
                    } break;

                    case 2: {
                        filterList("Drinks");
                    }break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
        ArrayList<Product> filteredProducts = new ArrayList<Product>();

        for (Product product: productList) {
            if(product.getCategory().contains(selectedFilter)) {
                filteredProducts.add(product);
            }
        }
        ProductCard card = new ProductCard(getApplicationContext(), 0, filteredProducts);
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
            }
        });
    }

    private void initSearchProducts() {
        SearchView searchView = (SearchView) findViewById(R.id.listSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Product> filteredProducts = new ArrayList<Product>();

                for (Product product: productList) {
                    if(product.getProductName().toLowerCase().contains(s.toLowerCase())) {
                        filteredProducts.add(product);
                    }
                }

                ProductCard card = new ProductCard(
                    getApplicationContext(), 0, filteredProducts
                );

                gridView.setAdapter(card);

                return false;
            }
        });
    }

    private void filterList(String status) {
        selectedFilter = status;

        ArrayList<Product> filteredProducts = new ArrayList<Product>();

        for (Product product: productList) {
            if(product.getCategory().contains(status)) {
                filteredProducts.add(product);
            }
        }

        ProductCard card = new ProductCard(
                getApplicationContext(), 0, filteredProducts
        );

        gridView.setAdapter(card);
    }
}