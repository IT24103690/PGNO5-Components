package com.inventory.inventorymanagement.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "orderType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OnlineOrder.class, name = "online"),
        @JsonSubTypes.Type(value = InStoreOrder.class, name = "inStore")
})
public class Order {
    private String id;
    private String userId;
    private String customerName;
    private String product;
    private int quantity;
    private String orderType;
    private double totalAmount;
    private Date orderDate;
    private String status;

    public Order() {}

    public Order(String id, String userId, String customerName, String product, int quantity, String orderType, double totalAmount, Date orderDate, String status) {
        this.id = id;
        this.userId = userId;
        this.customerName = customerName;
        this.product = product;
        this.quantity = quantity;
        this.orderType = orderType;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getOrderType() { return orderType; }
    public void setOrderType(String orderType) { this.orderType = orderType; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}