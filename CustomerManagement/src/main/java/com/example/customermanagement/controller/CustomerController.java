package com.example.customermanagement.controller;

import com.example.customermanagement.entity.Customer;
import com.example.customermanagement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    // Show Sign-up Form
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "signup";
    }

    // Handle Sign-up
    @PostMapping("/signup")
    public String signUp(@ModelAttribute Customer customer) {
        customerService.createCustomer(customer);
        return "redirect:/login";
    }

    // Show Login Form
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Handle Login
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer != null && customer.getPassword().equals(password) && customer.isActive()) {
            model.addAttribute("customer", customer);
            return "redirect SodaService:/catalog";
        }
        model.addAttribute("error", "Invalid credentials or inactive account");
        return "login";
    }

    // Show Customer Catalog
    @GetMapping("/catalog")
    public String showCatalog(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "catalog";
    }

    // Show Update Form
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        model.addAttribute("customer", customer);
        return "edit";
    }

    // Handle Update
    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute Customer customer) {
        customerService.updateCustomer(id, customer);
        return "redirect:/catalog";
    }

    // Delete Customer
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/catalog";
    }
}