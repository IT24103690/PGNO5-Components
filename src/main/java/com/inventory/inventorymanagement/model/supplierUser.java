package com.inventory.inventorymanagement.model;

public class supplierUser extends User {
    // Default constructor
    public supplierUser() {
        setRole("supplier");
    }

    // Constructor with fields
    public supplierUser(String id, String name, String password, String companyName) {
        super(id, name, "supplier", password);
        setCompanyName(companyName);
    }

    @Override
    public String toString() {
        return "supplierUser{id='" + getId() + "', name='" + getName() + "', role='" + getRole() + "', companyName='" + getCompanyName() + "'}";
    }
}