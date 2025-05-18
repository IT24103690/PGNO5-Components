
package com.inventory.inventorymanagement.controller;

import com.inventory.inventorymanagement.model.User;

public class UserUpdateRequest {
    private User user;
    private String originalId;

    // Getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }
}
