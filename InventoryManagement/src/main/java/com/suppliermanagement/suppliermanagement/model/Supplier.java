package com.example.project.model;

import java.util.List;

public abstract class Supplier {
    private String name;
    private String contact;
    private List<String> products;

    public Supplier(String name, String contact, List<String> products) {
        this.name = name;
        this.contact = contact;
        this.products = products;
    }

    public String getName() { return name; }
    public String getContact() { return contact; }
    public List<String> getProducts() { return products; }

    public void setName(String name) { this.name = name; }
    public void setContact(String contact) { this.contact = contact; }
    public void setProducts(List<String> products) { this.products = products; }

    public abstract String getDisplayDetails();
}
