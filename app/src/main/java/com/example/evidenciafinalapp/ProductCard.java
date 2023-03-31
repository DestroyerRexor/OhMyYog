package com.example.evidenciafinalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ProductCard extends ArrayAdapter<Product> {

    public ProductCard(Context context, int resource, List<Product> productList) {
        super(context, resource, productList);
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
        productImage.setImageResource(product.getURLImage());

        return convertView;
    }
}
