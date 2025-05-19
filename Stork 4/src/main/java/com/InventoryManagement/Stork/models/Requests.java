package com.InventoryManagement.Stork.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Requests {
    private int requestId;
    private Product product;
    private int amountFilled;
    private int fullAmountRequested;
    private boolean isComplete;
    private int amountToBeFilled;
    public boolean isVisibleToSupplier;
    private int FirstRequestId;

    public Requests(int requestId, Product product,  int amountFilled, int fullAmountRequested, boolean isVisibleToSupplier, int firstRequestId) {
        this.requestId = requestId;
        this.amountFilled = amountFilled;
        this.fullAmountRequested = fullAmountRequested;
        this.product = product;
        this.isVisibleToSupplier = isVisibleToSupplier;
        this.FirstRequestId = firstRequestId;
        isComplete = (fullAmountRequested == amountFilled);
        amountToBeFilled = fullAmountRequested - amountFilled;
    }

    public Requests(int requestId, Product product,  int amount, int firstRequestId) {
        this.requestId = requestId;
        this.product = product;
        this.amountFilled = amount;
        this.fullAmountRequested = amount;
        this.isComplete = true;
        this.isVisibleToSupplier = true;
        this.FirstRequestId = firstRequestId;
    }

    public Requests(int requestId, Product product,  int fullAmountRequested) {
        this.requestId = requestId;
        this.product = product;
        this.fullAmountRequested = fullAmountRequested;
        this.isVisibleToSupplier = true;
        isComplete = (fullAmountRequested == amountFilled);
        amountToBeFilled = fullAmountRequested - amountFilled;
    }

    public void completeRequest(){
        this.amountFilled = this.fullAmountRequested;
        this.isComplete = true;
    }

    public void printDetails(){
        System.out.println(
                "Request Details:\n" +
                        "Request ID: " + requestId + "\n" +
                        "Product: " + (product != null ? product.toString() : "null") + "\n" +
                        "Amount Filled: " + amountFilled + "\n" +
                        "Full Amount Requested: " + fullAmountRequested + "\n" +
                        "Is Complete: " + isComplete + "\n" +
                        "Amount To Be Filled: " + amountToBeFilled + "\n" +
                        "Visible to Supplier: " + isVisibleToSupplier + "\n" +
                        "First Request ID: " + getFirstRequestId()
        );
    }
}
