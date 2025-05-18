package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private static final String JSON_FILE_PATH = "data/product.json";

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(JSON_FILE_PATH);
            products = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static Product findProductById(String id) {
        return getAllProducts().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(
                        null
                );
    }

    public static void addProduct(Product product) {
        List<Product> products = getAllProducts();
        products.add(product);
        saveProducts(products);
    }

    public static void deleteProduct(Product product) {
        List<Product> products = getAllProducts();
        products.removeIf(p -> p.getId().equals(product.getId())); //Changed this cuz it wouldn't equal when you have two different product ykwim
        saveProducts(products);
    }

    public static void updateProduct(Product product, String ID) {
        List<Product> products = getAllProducts();
        products.removeIf(p -> p.getId().equals(ID)); // Remove old entry
        products.add(product); // Add updated product
        saveProducts(products);
    }

    private static void saveProducts(List<Product> products) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(JSON_FILE_PATH), products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
