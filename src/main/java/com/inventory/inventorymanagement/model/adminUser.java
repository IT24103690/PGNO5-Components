package com.inventory.inventorymanagement.model;

public class adminUser extends User {
    private String adminLevel;

    // Default constructor
    public adminUser() {
        super();
        setRole("admin");
    }

    // Constructor with fields
    public adminUser(String id, String name, String password, String adminLevel) {
        super(id, name, "admin", password);
        this.adminLevel = adminLevel;
    }

    // Getter and Setter for adminLevel
    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }
}