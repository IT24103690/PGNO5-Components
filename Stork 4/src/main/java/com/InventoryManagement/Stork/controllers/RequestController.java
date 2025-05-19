package com.InventoryManagement.Stork.controllers;

import com.InventoryManagement.Stork.models.Product;
import com.InventoryManagement.Stork.services.ProductService;
import com.InventoryManagement.Stork.models.Requests;
import com.InventoryManagement.Stork.repos.requestFileHandler;
import com.InventoryManagement.Stork.services.RequestService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Getter
@Setter
@Controller
public class RequestController {
    private List<Requests> requests;
    private List<Requests> filteredRequests = null;
    private boolean isPending = false;
    private boolean isEditCard = false;
    private Requests EditCardRequest;
    private List<Product> products;
    private Product productSelected = new Product(" ", null, 0, " ", " ");
    private requestFileHandler requestFileHandler = new requestFileHandler();
    private String supplier = "Apple";
    private Requests fillRequest = null;

    @GetMapping("/adminRequests")
    public String adminRequests(Model model) {
        if(requests == null) {
            requests = RequestService.getRequests();
        }
        products = ProductService.getAllProducts();
        for (Requests request : requests) {
            if (request.getProduct() == null) {
                System.out.println("Product is null for request: " + request.getRequestId());
            }
        }
        model.addAttribute("requests", requests);
        model.addAttribute("isPending", isPending);
        model.addAttribute("isEditCard", isEditCard);
        model.addAttribute("editCardRequest", EditCardRequest);
        model.addAttribute("products", products);
        model.addAttribute("productSelected", productSelected);
        return "adminRequests";
    }

    @GetMapping("/supplierRequests")
    public String supplierRequests(Model model) {
        if (filteredRequests == null) {
            filteredRequests = RequestService.filterRequestsBySupplier(supplier);
        }
        if (products == null) {
            products = ProductService.getAllProducts();
        }
        model.addAttribute("requests", filteredRequests);
        model.addAttribute("products", products);
        model.addAttribute("isPending", isPending);
        model.addAttribute("fillRequest", fillRequest);
        return "supplierRequests";
    }

    @PostMapping("/adminRequests/SearchBox")
    public String adminRequestsSearchBox(@RequestParam String action, @RequestParam(required = false) Integer idInput) {
        isEditCard = false;
        requests = RequestService.getRequests();
        if(action.equals("pending")) {
            isPending = !isPending;
            if(isPending) {

                requests = RequestService.getPendingRequests(requests);
            }
        } else if (action.equals("search") && idInput != null){
            isPending = false;
            requests = RequestService.searchBoxRequests(idInput, requests);
        }
        return "redirect:/adminRequests";
    }

    @PostMapping("/supplierRequests/SearchBox")
    public String supplierRequestsSearchBox(@RequestParam String action, @RequestParam(required = false) Integer idInput) {
        filteredRequests = RequestService.filterRequestsBySupplier(supplier);
        if(action.equals("pending")) {
            isPending = !isPending;
            if(isPending) {
                filteredRequests = RequestService.getPendingRequests(filteredRequests);
            } else {
                filteredRequests = RequestService.filterRequestsBySupplier(supplier);
            }
        } else if (action.equals("search") && idInput != null){
            isPending = false;
            filteredRequests = RequestService.searchBoxRequests(idInput, filteredRequests);
        }
        return "redirect:/supplierRequests";
    }

    @PostMapping("/adminRequests/editOrDelete")
    public String adminRequestsEditOrDelete(@RequestParam String action, @RequestParam Integer idOfCard) {
        if(action.equals("delete")) {
            RequestService.deleteRequestsByMainId(idOfCard);
            requests = RequestService.getRequests();
        } else {
            isEditCard = true;
            isPending = false;
            EditCardRequest = RequestService.getRequestById(idOfCard);
        }
        return ("redirect:/adminRequests");
    }

    @PostMapping("/supplierRequests/editOrDelete")
    public String supplierRequestsEditOrDelete(@RequestParam(required = false) Integer idOfCard, @RequestParam String action) {
        if(action.equals("delete")) {
            RequestService.hideToSupplier(idOfCard);
            filteredRequests = null;
        } else {
            fillRequest = RequestService.getRequestById(idOfCard);
        }
        return ("redirect:/supplierRequests");
    }

    @PostMapping("/adminRequests/editCardSave")
    public String adminRequestsEditCardSave(@RequestParam(required = false) Integer productAmount) {
        if(productAmount == null) {
            isEditCard = false;
            return ("redirect:/adminRequests");
        }
        RequestService.updateRequest(EditCardRequest,  productAmount);
        isEditCard = false;
        requests = RequestService.getRequests();
        filteredRequests = null;
        return ("redirect:/adminRequests");
    }

    @PostMapping("/supplierRequests/editCardSave")
    public String supplierRequestsEditCardSave(@RequestParam(required = false) Integer productAmount) {
        if(productAmount == null) {
            return ("redirect:/supplierRequests");
        } else {
            RequestService.fillRequests(fillRequest, productAmount);
            requests = null;
            fillRequest = null;
            filteredRequests = null;
            return ("redirect:/supplierRequests");
        }
    }

    @PostMapping("/adminRequests/registerBox")
    public String adminRequestsRegisterBox(@RequestParam(required = false) String productId, @RequestParam(required = false) Integer amount, @RequestParam(required = false) String action) {
        if(ProductService.findProductById(productId) != (productSelected)) {
            productSelected = ProductService.findProductById(productId);
            requests = RequestService.getRequests();
        }
        if (action != null && action.equals("manual")) {
            if(amount == null) {
                productSelected.setName(null);
                productSelected.setSupplier(" ");
                productSelected.setUnit(" ");
                return ("redirect:/adminRequests");
            }
            Requests requestsNew = new Requests(1, productSelected, amount);
            RequestService.addRequest(requestsNew);
            productSelected.setName(null);
            productSelected.setSupplier(" ");
            productSelected.setUnit(" ");
            requests = RequestService.getRequests();
        }
        return ("redirect:/adminRequests");
    }
}
