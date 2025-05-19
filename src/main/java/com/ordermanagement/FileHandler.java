package com.ordermanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FileHandler {
    private static final String FILE_PATH = "orders.json";
    private File file;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileHandler() throws IOException {
        file = new File(FILE_PATH);
        System.out.println("File path for orders.json: " + file.getAbsolutePath());
        if (!file.exists()) {
            System.out.println("Creating new orders.json at: " + file.getAbsolutePath());
            file.createNewFile();
            List<Order> initialOrders = new ArrayList<>();
            initialOrders.add(new Order(1L, "chanula", "Laptop", false));
            writeOrders(initialOrders);
        }
    }

    private File getFile() {
        return file;
    }

    public List<Order> readOrders() {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFile()))) {
            if (getFile().length() > 0) { // Only parse if file is not empty
                orders = objectMapper.readValue(reader, objectMapper.getTypeFactory()
                        .constructCollectionType(List.class, Order.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read orders.json", e);
        }
        return orders;
    }

    public void writeOrders(List<Order> orders) {
        System.out.println("Writing to orders.json at: " + getFile().getAbsolutePath());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(), false))) {
            objectMapper.writeValue(writer, orders);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to write to orders.json", e);
        }
    }

    public void addOrder(Order order) {
        List<Order> orders = readOrders();
        orders.add(order);
        writeOrders(orders);
    }


    public Optional<Order> getOrder(Long id) {
        return readOrders().stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }

    public void updateOrder(Order updatedOrder) {
        List<Order> orders = readOrders();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equals(updatedOrder.getId())) {
                orders.set(i, updatedOrder);
                break;
            }
        }
        writeOrders(orders);
    }

    public void deleteOrder(Long id) {
        List<Order> orders = readOrders();
        orders.removeIf(o -> o.getId().equals(id));
        writeOrders(orders);
    }
}