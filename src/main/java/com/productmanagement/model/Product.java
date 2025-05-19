package com.productmanagement.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private String id;
    private String name;
    private double price;
    private String unit;
    private String supplier;

    public Product() {}

    public Product(String name, String id, double price, String unit , String supplier) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.supplier = supplier;
    }
}