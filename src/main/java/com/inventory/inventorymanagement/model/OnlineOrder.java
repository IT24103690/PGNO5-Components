package com.inventory.inventorymanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class OnlineOrder extends Order {
    @JsonProperty("shippingAddress")
    private String shippingAddress;

    public OnlineOrder() {
        setOrderType("online");
    }

    public OnlineOrder(String id, String userId, String customerName, String product, int quantity, double totalAmount, Date orderDate, String status, String shippingAddress) {
        super(id, userId, customerName, product, quantity, "online", totalAmount, orderDate, status);
        this.shippingAddress = shippingAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}