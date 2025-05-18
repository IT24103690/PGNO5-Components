
package com.inventory.inventorymanagement.model;

public class staffUser extends User {
    // Default constructor
    public staffUser() {
        setRole("staff");
    }

    // Constructor with fields
    public staffUser(String id, String name, String password, String department) {
        super(id, name, "staff", password);
        setDepartment(department);
    }

    @Override
    public String toString() {
        return "staffUser{id='" + getId() + "', name='" + getName() + "', role='" + getRole() + "', department='" + getDepartment() + "'}";
    }
}
