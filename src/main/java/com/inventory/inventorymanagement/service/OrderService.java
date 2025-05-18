package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.model.Order;
import com.inventory.inventorymanagement.util.OrderJsonFileHandler;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderJsonFileHandler jsonFileHandler;
    private static final String ORDERS_FILE_PATH = "src/main/resources/data/orders.json";

    public OrderService() {
        this.jsonFileHandler = new OrderJsonFileHandler();
        initializeOrdersFile();
    }

    private void initializeOrdersFile() {
        try {
            Path path = Paths.get(ORDERS_FILE_PATH);
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            if (!Files.exists(path)) {
                Files.write(path, "[]".getBytes());
            }
            System.out.println("Initialized orders file at: " + path.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error initializing orders file: " + e.getMessage());
        }
    }

    public void createOrder(Order order) {
        try {
            List<Order> orders = getAllOrders();
            orders.add(order);
            jsonFileHandler.writeToJson(orders, ORDERS_FILE_PATH);
            System.out.println("Order created: " + order.getId());
        } catch (Exception e) {
            System.err.println("Error creating order: " + e.getMessage());
        }
    }

    public List<Order> getAllOrders() {
        try {
            return jsonFileHandler.readFromJson(ORDERS_FILE_PATH, Order.class);
        } catch (Exception e) {
            System.err.println("Error reading orders: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Order> getCompletedOrders() {
        try {
            return getAllOrders().stream()
                    .filter(order -> "Completed".equals(order.getStatus()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error getting completed orders: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}