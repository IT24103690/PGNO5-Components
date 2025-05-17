package com.ordermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("order", new Order());
        model.addAttribute("editMode", false);
        return "orders";
    }

    @PostMapping("/add")
    public String addOrder(@ModelAttribute Order order) {
        orderService.createOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        model.addAttribute("order", order);
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("editMode", true);
        return "orders";
    }

    @PostMapping("/edit")
    public String updateOrder(@ModelAttribute Order order) {
        orderService.updateOrder(order.getId(), order);
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }

    @GetMapping("/fulfill/{id}")
    public String fulfillOrder(@PathVariable Long id) {
        orderService.fulfillOrder(id);
        return "redirect:/orders";
    }
}