package com.inventory.inventorymanagement.controller;

import com.inventory.inventorymanagement.model.User;
import com.inventory.inventorymanagement.model.adminUser;
import com.inventory.inventorymanagement.model.staffUser;
import com.inventory.inventorymanagement.model.supplierUser;
import com.inventory.inventorymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public String registerUser(@ModelAttribute User user, Model model, @RequestParam(value = "adminLevel", required = false) String adminLevel,
                               @RequestParam(value = "companyName", required = false) String companyName,
                               @RequestParam(value = "department", required = false) String department) {
        try {
            if (user.getId() == null || user.getId().trim().isEmpty()) {
                user.setId(UUID.randomUUID().toString());
            }
            if (user.getName() == null || user.getName().trim().isEmpty()) {
                model.addAttribute("error", "Name is required");
                return "user-registration";
            }
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                model.addAttribute("error", "Password is required");
                return "user-registration";
            }
            if (user.getRole() == null || user.getRole().trim().isEmpty()) {
                model.addAttribute("error", "Role is required");
                return "user-registration";
            }
            User newUser;
            switch (user.getRole().toLowerCase()) {
                case "admin":
                    newUser = new adminUser(user.getId(), user.getName(), user.getPassword(), adminLevel != null ? adminLevel : "standard");
                    break;
                case "supplier":
                    newUser = new supplierUser(user.getId(), user.getName(), user.getPassword(), companyName != null ? companyName : "Unknown");
                    break;
                case "staff":
                    newUser = new staffUser(user.getId(), user.getName(), user.getPassword(), department != null ? department : "Unknown");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid role: " + user.getRole());
            }
            userService.createUser(newUser);
            System.out.println("Registered user: " + newUser);
        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Failed to register user: " + e.getMessage());
            return "user-registration";
        }
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model, @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
        return "login";
    }

    @GetMapping
    public String showUserList(Model model) {
        try {
            var users = userService.getAllUsers();
            model.addAttribute("users", users);
            model.addAttribute("selectedUser", new User());
            System.out.println("User list loaded with " + users.size() + " users: " + users);
            return "user-list";
        } catch (Exception e) {
            System.err.println("Error in showUserList: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Failed to load users: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(Authentication authentication) {
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(authority -> authority.getAuthority().replace("ROLE_", "").toLowerCase())
                .orElse("staff");
        switch (role) {
            case "admin":
                return "user/admin-dashboard";
            case "staff":
                return "user/staff-dashboard";
            case "supplier":
                return "user/supplier-dashboard";
            default:
                return "redirect:/users/login";
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
    public String updateUser(@ModelAttribute User user, @RequestParam(value = "adminLevel", required = false) String adminLevel,
                             @RequestParam(value = "companyName", required = false) String companyName,
                             @RequestParam(value = "department", required = false) String department) {
        try {
            User updatedUser = new User(user.getId(), user.getName(), user.getRole(), user.getPassword());
            switch (user.getRole().toLowerCase()) {
                case "admin":
                    updatedUser = new adminUser(user.getId(), user.getName(), user.getPassword(), adminLevel != null ? adminLevel : "standard");
                    break;
                case "supplier":
                    updatedUser = new supplierUser(user.getId(), user.getName(), user.getPassword(), companyName != null ? companyName : "Unknown");
                    break;
                case "staff":
                    updatedUser = new staffUser(user.getId(), user.getName(), user.getPassword(), department != null ? department : "Unknown");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid role: " + user.getRole());
            }
            userService.updateUser(updatedUser);
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