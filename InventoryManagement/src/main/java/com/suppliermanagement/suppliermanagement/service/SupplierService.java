package com.example.project.service;

import com.example.project.model.Supplier;
import com.example.project.utils.SupplierFileHandler;
import java.util.*;
import java.util.stream.Collectors;

public class SupplierService {
    private List<Supplier> suppliers = SupplierFileHandler.loadSuppliers();

    public List<Supplier> getAll() {
        return suppliers;
    }

    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
        SupplierFileHandler.saveSuppliers(suppliers);
    }

    public Supplier findByName(String name) {
        return suppliers.stream().filter(s -> s.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void updateSupplier(Supplier updatedSupplier) {
        suppliers = suppliers.stream()
                .map(s -> s.getName().equalsIgnoreCase(updatedSupplier.getName()) ? updatedSupplier : s)
                .collect(Collectors.toList());
        SupplierFileHandler.saveSuppliers(suppliers);
    }

    public void deleteSupplier(String name) {
        suppliers.removeIf(s -> s.getName().equalsIgnoreCase(name));
        SupplierFileHandler.saveSuppliers(suppliers);
    }
}
