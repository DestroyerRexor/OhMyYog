package com.example.evidenciafinalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProductCard extends ArrayAdapter<Product> {

    public ProductCard(Context context, int resource, List<Product> productList) {
        super(context, resource, productList);
    }

    public static String clearURLImageAPI(String url) {
        String cleanURL = "";
        return  cleanURL = url.replace("\\/", "/");
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_card, parent, false);
        }

        TextView productName = (TextView) convertView.findViewById(R.id.productName);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        ImageView productImage = (ImageView) convertView.findViewById(R.id.productImage);

        productName.setText(product.getProductName());
        price.setText("$ " + product.getPrice());
        Glide.with(getContext()).load(clearURLImageAPI(product.getURLImage())).override(320, 320).into(productImage);

        return convertView;
    }
}
