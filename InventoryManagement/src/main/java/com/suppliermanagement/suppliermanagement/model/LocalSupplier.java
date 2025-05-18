package com.example.project.model;

import java.util.List;

public class LocalSupplier extends Supplier {
    private String region;

    public LocalSupplier(String name, String contact, List<String> products, String region) {
        super(name, contact, products);
        this.region = region;
    }

    public String getRegion() { return region; }

    @Override
    public String getDisplayDetails() {
        return "Local Supplier: " + getName() + ", Region: " + region;
    }
}
