package com.inventory.inventorymanagement.model;

public class supplierUser extends User {
    private String companyName;

    // Default constructor
    public supplierUser() {
        super();
        setRole("supplier");
    }

    // Constructor with fields
    public supplierUser(String id, String name, String password, String companyName) {
        super(id, name, "supplier", password);
        this.companyName = companyName;
    }

    // Getter and Setter for companyName
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}