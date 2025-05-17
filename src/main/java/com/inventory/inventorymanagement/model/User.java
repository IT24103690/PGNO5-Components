package com.inventory.inventorymanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "role"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = adminUser.class, name = "admin"),
        @JsonSubTypes.Type(value = staffUser.class, name = "staff"),
        @JsonSubTypes.Type(value = supplierUser.class, name = "supplier")
})
public class User {
    private String id;
    private String name;
    private String role;
    private String password;
    @JsonProperty("adminLevel")
    private String adminLevel;
    @JsonProperty("companyName")
    private String companyName;
    @JsonProperty("department")
    private String department;

    // Default constructor for JSON deserialization
    public User() {}

    // Constructor with basic fields
    public User(String id, String name, String role, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "User{id='" + id + "', name='" + name + "', role='" + role + "'}";
    }
}