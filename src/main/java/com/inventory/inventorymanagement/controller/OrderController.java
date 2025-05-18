package com.inventory.inventorymanagement.controller;

import com.inventory.inventorymanagement.model.Order;
import com.inventory.inventorymanagement.model.OnlineOrder;
import com.inventory.inventorymanagement.model.InStoreOrder;
import com.inventory.inventorymanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/dashboard")
    public String showOrderDashboard(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "order/order-dashboard";
    }

    @GetMapping("/create")
    public String showOrderCreateForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("userId", ""); // Placeholder; replace with authenticated user ID if needed
        return "order/order-create";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order, @RequestParam(value = "shippingAddress", required = false) String shippingAddress, @RequestParam(value = "storeLocation", required = false) String storeLocation, Model model) {
        try {
            order.setId(java.util.UUID.randomUUID().toString());
            order.setUserId(""); // Placeholder; replace with authenticated user ID if needed
            order.setOrderDate(new java.util.Date());
            order.setStatus("Pending"); // Default status

            // Instantiate the correct subclass based on orderType
            Order finalOrder;
            if ("online".equals(order.getOrderType())) {
                OnlineOrder onlineOrder = new OnlineOrder();
                onlineOrder.setShippingAddress(shippingAddress != null ? shippingAddress : "");
                finalOrder = onlineOrder;
            } else if ("inStore".equals(order.getOrderType())) {
                InStoreOrder inStoreOrder = new InStoreOrder();
                inStoreOrder.setStoreLocation(storeLocation != null ? storeLocation : "");
                finalOrder = inStoreOrder;
            } else {
                finalOrder = new Order(); // Fallback
            }

            // Copy common properties
            finalOrder.setCustomerName(order.getCustomerName());
            finalOrder.setProduct(order.getProduct());
            finalOrder.setQuantity(order.getQuantity());
            finalOrder.setTotalAmount(order.getTotalAmount());
            finalOrder.setOrderType(order.getOrderType());
            finalOrder.setOrderDate(order.getOrderDate());
            finalOrder.setStatus(order.getStatus());

            orderService.createOrder(finalOrder);
            model.addAttribute("successMessage", "Order created successfully!");
            model.addAttribute("orders", orderService.getAllOrders());
            return "redirect:/order/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create order: " + e.getMessage());
            return "order/order-create";
        }
    }

    @GetMapping("/history")
    public String showOrderHistory(Model model) {
        model.addAttribute("completedOrders", orderService.getCompletedOrders());
        return "order/order-history";
    }
}