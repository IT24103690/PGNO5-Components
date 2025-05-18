package com.ordermanagement;

import lombok.Data;

@Data
public class Order {
    private Long id;
    private String customer;
    private String product;

    private boolean fulfilled;

    public Order() {
        this.fulfilled = false; // Default value
    }

    public Order(Long id, String customer, String product, boolean fulfilled) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.fulfilled = fulfilled;
    }
}