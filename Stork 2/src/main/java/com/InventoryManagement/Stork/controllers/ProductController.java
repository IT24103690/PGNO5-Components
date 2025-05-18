package com.InventoryManagement.Stork.controllers;

import com.InventoryManagement.Stork.models.Product;
import com.InventoryManagement.Stork.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @PostMapping("/addProduct")
    public String addProduct(Product product) {
        productService.addProduct(product);
        return "redirect:/products";
    }
}