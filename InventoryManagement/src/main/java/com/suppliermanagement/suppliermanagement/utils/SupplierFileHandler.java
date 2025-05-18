package com.suppliermanagement.suppliermanagement.utils;

import com.example.project.model.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.Supplier;

public class SupplierFileHandler {
    private static final String FILE_PATH = "src/main/resources/suppliers.txt";

    public static List<com.example.project.model.Supplier> loadSuppliers() {
        List<com.example.project.model.Supplier> suppliers = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                String[] parts = line.split(";");
                String type = parts[0];
                String name = parts[1];
                String contact = parts[2];
                List<String> products = Arrays.asList(parts[3].split(","));

                if ("local".equalsIgnoreCase(type)) {
                } else {
                    suppliers.add(new InternationalSupplier(name, contact, products, parts[4]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    public static void saveSuppliers(List<Supplier> suppliers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Supplier supplier : suppliers) {
                StringBuilder sb = new StringBuilder();
                if (supplier instanceof LocalSupplier) {
                    sb.append("local;");
                    sb.append(supplier.getName()).append(";");
                    sb.append(supplier.getContact()).append(";");
                    sb.append(String.join(",", supplier.getProducts())).append(";");
                    sb.append(((LocalSupplier) supplier).getRegion());
                } else if (supplier instanceof InternationalSupplier) {
                    sb.append("international;");
                    sb.append(supplier.getName()).append(";");
                    sb.append(supplier.getContact()).append(";");
                    sb.append(String.join(",", supplier.getProducts())).append(";");
                    sb.append(((InternationalSupplier) supplier).getCountry());
                }
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
