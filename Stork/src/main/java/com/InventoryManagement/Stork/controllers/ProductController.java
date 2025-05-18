package com.InventoryManagement.Stork.controllers;

import com.InventoryManagement.Stork.models.Product;
import com.InventoryManagement.Stork.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    private boolean isEditProduct = false;
    private Product editProduct;
    private List<Product> products;

    @GetMapping("/products")
    public String listProducts(Model model) {
        if (products == null) {
            products = ProductService.getAllProducts();
        }
        model.addAttribute("products", products);
        model.addAttribute("isEditProduct", isEditProduct);
        model.addAttribute("editProduct", editProduct);
        return "products";
    }

    @PostMapping("/products/addProduct")
    public String addProduct(@RequestParam(required = false) String productName, @RequestParam(required = false) String idInput, @RequestParam(required = false) Double priceInput, @RequestParam(required = false) String unitInput, @RequestParam(required = false) String supplierInput) {
        if(productName == null || idInput == null || priceInput == null || unitInput == null || supplierInput == null) {
            return "redirect:/products";
        }
        Product product = new Product(productName, idInput, priceInput, unitInput, supplierInput);
        ProductService.addProduct(product);
        products = null;
        return "redirect:/products";
    }

    @PostMapping("/products/search")
    public String searchProducts (@RequestParam(required = false) String idInput) {
        if(idInput.isEmpty()) {
            products = ProductService.getAllProducts();
            return "redirect:/products";
        }
        products.clear();
        if (productService.findProductById(idInput) != null){
            Product product = productService.findProductById(idInput);
            products.add(product);
        }
        return "redirect:/products";
    }

    @PostMapping("/products/editOrDeleteProduct")
    public String editOrDeleteProduct(@RequestParam String action, @RequestParam String idInput) {
        if(action.equals("delete")) {
            Product product  = ProductService.findProductById(idInput);
            ProductService.deleteProduct(product);
        } else {
            isEditProduct = true;
            editProduct = ProductService.findProductById(idInput);
        }
        products = ProductService.getAllProducts();
        return "redirect:/products";
    }

    @PostMapping("/products/editProduct")
    public String editProduct(@RequestParam String productName, @RequestParam String productId, @RequestParam Double productPrice, @RequestParam String productUnit, @RequestParam String productSupplier) {
        System.out.println(productName);
        if (productName == null || productId == null || productPrice == null || productUnit == null || productSupplier == null) {
            return "redirect:/products";
        }
        ProductService.updateProduct(new Product(productName, productId, productPrice, productUnit, productSupplier), editProduct.getId());
        isEditProduct = false;
        editProduct = null;
        products = null;
        return "redirect:/products";
    }
}