package dev.farjana.e_commerceapp.models;

import com.hishd.tinycart.model.Item;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Item, Serializable {
    private String product_name, product_img, status;
    private double product_price,discount;

    private int stock,id;
    private int quantity;

    public Product(String product_name, String product_img, String status, double product_price, double discount, int stock, int id) {
        this.product_name = product_name;
        this.product_img = product_img;
        this.status = status;
        this.product_price = product_price;
        this.discount = discount;
        this.stock = stock;
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getProduct_price(){
        return product_price;
    }
    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    @Override
    public BigDecimal getItemPrice() {
        return new BigDecimal(product_price);
    }

    @Override
    public String getItemName() {
        return product_name;
    }
}
