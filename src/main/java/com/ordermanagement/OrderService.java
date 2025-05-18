package com.ordermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService
{
    @Autowired
    private FileHandler fileHandler;

    public Order createOrder(Order order) {
        List<Order> orders = fileHandler.readOrders();
        Long newId = orders.stream().mapToLong(Order::getId).max().orElse(0L) + 1;
        order.setId(newId);
        fileHandler.addOrder(order);
        return order;
    }

    public List<Order> getAllOrders() {
        return fileHandler.readOrders();
    }

    public Optional<Order> getOrderById(Long id) {
        return fileHandler.getOrder(id);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        updatedOrder.setId(id);
        fileHandler.updateOrder(updatedOrder);
        return updatedOrder;
    }

    public void deleteOrder(Long id) {
        fileHandler.deleteOrder(id);
    }

    public Order fulfillOrder(Long id) {
        return fileHandler.getOrder(id)
                .map(order -> {
                    order.setFulfilled(true);
                    fileHandler.updateOrder(order);
                    return order;
                })
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}