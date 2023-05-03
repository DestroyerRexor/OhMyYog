package com.example.evidenciafinalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.bumptech.glide.Glide;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private final List<CartViewActivity.CartItem> cartItems;
    private static OnDeleteItemListener onDeleteItemListener = null;
    private Context context;


    public interface OnDeleteItemListener {
        void onDeleteItem(int position);
    }

    private String clearURLImageAPI(String url) {
        String cleanURL = "";
        return  cleanURL = url.replace("\\/", "/");
    }

    public CartAdapter(List<CartViewActivity.CartItem> cartItems, OnDeleteItemListener onDeleteItemListener) {
        this.cartItems = cartItems;
        this.onDeleteItemListener = onDeleteItemListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView itemImageView;
        final TextView itemNameTextView;
        final TextView itemPriceTextView;
        final TextView itemQuantityTextView;
        final Button deleteButton;

        ViewHolder(View view) {
            super(view);
            itemImageView = view.findViewById(R.id.item_image_imageView);
            itemNameTextView = view.findViewById(R.id.item_name_textview);
            itemPriceTextView = view.findViewById(R.id.item_price_textview);
            itemQuantityTextView = view.findViewById(R.id.item_quantity_textview);
            deleteButton = view.findViewById(R.id.deleteSelfButton);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDeleteItemListener.onDeleteItem(getAdapterPosition());
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartViewActivity.CartItem cartItem = cartItems.get(position);

        Glide.with(context).load(clearURLImageAPI(cartItem.getImage())).override(320, 320).into(holder.itemImageView);
        holder.itemNameTextView.setText(cartItem.getName());
        holder.itemPriceTextView.setText("$" + cartItem.getPrice());
        holder.itemQuantityTextView.setText(String.valueOf(cartItem.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}

