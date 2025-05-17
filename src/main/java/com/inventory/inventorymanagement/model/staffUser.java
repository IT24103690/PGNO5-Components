package com.inventory.inventorymanagement.model;

public class staffUser extends User {
    private String department;

    // Default constructor
    public staffUser() {
        super();
        setRole("staff");
    }

    // Constructor with fields
    public staffUser(String id, String name, String password, String department) {
        super(id, name, "staff", password);
        this.department = department;
    }

    // Getter and Setter for department
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}