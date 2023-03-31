package com.example.evidenciafinalapp;

public class Product {
    private String id;
    private String category;
    private String description;
    private String price;
    private String productName;
    private int URLImage;

    public Product(String id, String category, String description, String price, String productName, int URLImage) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.price = price;
        this.productName = productName;
        this.URLImage = URLImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String  price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getURLImage() {
        return URLImage;
    }

    public void setURLImage(int URLImage) {
        this.URLImage = URLImage;
    }
}
