package com.example.project.controller;

import com.example.project.model.*;
import com.example.project.service.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {
    private final SupplierService service = new SupplierService();

    @GetMapping
    public String list(Model model) {
        model.addAttribute("suppliers", service.getAll());
        return "supplier/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("supplier", new LocalSupplier("", "", Arrays.asList(), ""));
        return "supplier/create";
    }

    @PostMapping("/create")
    public String create(@RequestParam String name,
                         @RequestParam String contact,
                         @RequestParam String products,
                         @RequestParam String type,
                         @RequestParam String regionOrCountry) {
        Supplier supplier;
        if ("local".equals(type)) {
            supplier = new LocalSupplier(name, contact, Arrays.asList(products.split(",")), regionOrCountry);
        } else {
            supplier = new InternationalSupplier(name, contact, Arrays.asList(products.split(",")), regionOrCountry);
        }
        service.addSupplier(supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String name) {
        service.deleteSupplier(name);
        return "redirect:/suppliers";
    }
}
