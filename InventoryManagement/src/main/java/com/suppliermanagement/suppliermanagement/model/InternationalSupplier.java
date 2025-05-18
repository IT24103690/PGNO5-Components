package com.example.project.model;

import java.util.List;

public class InternationalSupplier extends Supplier {
    private String country;

    public InternationalSupplier(String name, String contact, List<String> products, String country) {
        super(name, contact, products);
        this.country = country;
    }

    public String getCountry() { return country; }

    @Override
    public String getDisplayDetails() {
        return "International Supplier: " + getName() + ", Country: " + country;
    }
}


