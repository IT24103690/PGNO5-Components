package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.model.Requests;
import com.inventory.inventorymanagement.util.requestFileHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    public static List<Requests> getRequests() {
        return requestFileHandler.getAllRequests();
    }

    public static List<Requests> searchBoxRequests(@RequestParam(required = false) Integer idInput, @RequestParam List<Requests> requests) {
        return getAllRequests(idInput, requests);
    }

    public static List<Requests> getPendingRequests( List<Requests> requests) {
        List<Requests> pendingRequests = new ArrayList<>();
        for (Requests request : requests) {
            if(!request.isComplete()){
                pendingRequests.add(request);
            }
        }
        return pendingRequests;
    }

    public static Requests getRequestById(int id) {
        return requestFileHandler.getRequestById(id);
    }

    public static List<Requests> getAllRequests(int id, List<Requests> requests) {
        List<Requests> newRequests = new ArrayList<>();
        for (Requests request : requests) {
            if (request.getFirstRequestId() == id) {
                newRequests.add(request);
            }
        }
        return newRequests;
    }

    public static void deleteRequestById(int id) {
        requestFileHandler.deleteRequestById(id);
    }

    public static void updateRequest(Requests requestIn, int quantity) {
        List<Requests> requests = getRequests();;
        for (Requests request : requests) {
            if (request.getRequestId() == requestIn.getRequestId()) {
                if (request.getAmountToBeFilled() > quantity) {
                    request.setFullAmountRequested(request.getFullAmountRequested() + quantity - request.getAmountToBeFilled());
                    requestFileHandler.writeRequests(requests);
                }
                return;
            }
        }
    }

    public static void addRequest(Requests request) {
        int tempId = 1;
        while(getRequestById(tempId) != null){
            tempId++;
        }
        System.out.println(tempId);
        request.setRequestId(tempId);
        if(request.getFirstRequestId() == 0){
            request.setFirstRequestId(tempId);
        }
        requestFileHandler.addRequest(request);
    }

    public static void replaceRequest(Requests request) {
        requestFileHandler.addRequest(request);
    }

    public static List<Requests> filterRequestsBySupplier(String supplier) {
        List<Requests> requests = getRequests();
        List<Requests> filteredRequests = new ArrayList<>();
        for (Requests request : requests) {
            if(request.getProduct().getSupplier().trim().equals(supplier) && request.isVisibleToSupplier()){
                filteredRequests.add(request);
            }
        }
        return filteredRequests;
    }

    public static void hideToSupplier(int idOfCard) {
        List<Requests> allRequests = getRequests();
        for (Requests request : allRequests) {
            if(request.getRequestId() == idOfCard){
                request.setVisibleToSupplier(false);
                break;
            }
        }
        requestFileHandler.writeRequests(allRequests);
    }

    public static void fillRequests(Requests fillRequest, int amount) {
        List<Requests> requests = getRequests();
        for (Requests request : requests) {
            if(request.getRequestId() == fillRequest.getRequestId()){
                if(request.getAmountToBeFilled() > amount){
                    Requests tempRequest = new Requests(0, fillRequest.getProduct(), amount, fillRequest.getRequestId());
                    RequestService.deleteRequestById(request.getRequestId());
                    request.setAmountFilled(request.getAmountFilled() + amount);
                    RequestService.addRequest(request);
                    RequestService.addRequest(tempRequest);
                } else if(request.getAmountToBeFilled() == amount){
                    Requests tempRequest = new Requests(fillRequest.getRequestId(), fillRequest.getProduct(), fillRequest.getFullAmountRequested(), fillRequest.getRequestId());
                    tempRequest.completeRequest();
                    deleteRequestsByMainId(fillRequest.getRequestId());
                    RequestService.replaceRequest(tempRequest);
                } else {
                    return;
                }
                break;
            }
        }
    }

    public static void deleteRequestsByMainId(int idOfCard) {
        List<Requests> requests = getRequests();
        for (Requests request : requests) {
            if(request.getFirstRequestId() == idOfCard){
                requestFileHandler.deleteRequestById(request.getRequestId());
            }
        }
    }
}

