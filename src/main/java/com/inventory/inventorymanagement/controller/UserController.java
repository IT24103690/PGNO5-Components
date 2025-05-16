package com.inventory.inventorymanagement.controller;

import com.inventory.inventorymanagement.model.User;
import com.inventory.inventorymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "user-registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        try {
            userService.createUser(user);
        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/users/register?error=true";
        }
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping
    public String showUserList(Model model) {
        try {
            // Fetch users only once to avoid redundant calls
            var users = userService.getAllUsers();
            model.addAttribute("users", users);
            model.addAttribute("selectedUser", new User());
            System.out.println("User list loaded with " + users.size() + " users");
            return "user-list";
        } catch (Exception e) {
            System.err.println("Error in showUserList: " + e.getMessage());
            e.printStackTrace(); // Print full stack trace for debugging
            model.addAttribute("error", "Failed to load users: " + e.getMessage());
            return "error"; // Redirect to error.html
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User getUserById(@PathVariable String id) {
        try {
            return userService.getUserById(id);
        } catch (Exception e) {
            System.err.println("Error fetching user: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/users?error=true";
        }
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
        }
    }
}