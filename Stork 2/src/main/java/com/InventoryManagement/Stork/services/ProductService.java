package com.InventoryManagement.Stork.services;

import com.InventoryManagement.Stork.models.Product;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private static final String FILE_PATH = "data/products.txt";

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    products.add(new Product(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3], parts[4]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static void addProduct(Product product) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(product.getId() + "," + product.getName() + "," + product.getPrice());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Product getProductById(String id){
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }
}
