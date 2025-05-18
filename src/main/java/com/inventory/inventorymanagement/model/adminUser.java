
package com.inventory.inventorymanagement.model;

public class adminUser extends User {
    // Default constructor
    public adminUser() {
        setRole("admin");
    }

    // Constructor with fields
    public adminUser(String id, String name, String password, String adminLevel) {
        super(id, name, "admin", password);
        setAdminLevel(adminLevel);
    }

    @Override
    public String toString() {
        return "adminUser{id='" + getId() + "', name='" + getName() + "', role='" + getRole() + "', adminLevel='" + getAdminLevel() + "'}";
    }
}
