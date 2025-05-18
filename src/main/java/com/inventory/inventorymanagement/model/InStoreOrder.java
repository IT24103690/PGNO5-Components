package com.inventory.inventorymanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class InStoreOrder extends Order {
    @JsonProperty("storeLocation")
    private String storeLocation;

    public InStoreOrder() {
        setOrderType("inStore");
    }

    public InStoreOrder(String id, String userId, String customerName, String product, int quantity, double totalAmount, Date orderDate, String status, String storeLocation) {
        super(id, userId, customerName, product, quantity, "inStore", totalAmount, orderDate, status);
        this.storeLocation = storeLocation;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }
}