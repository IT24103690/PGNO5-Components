package com.InventoryManagement.Stork.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Requests {
    private int requestId;
    private Product product;
    @Getter
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
        if (amountFilled == fullAmountRequested){
            isComplete = true;
        }
        this.product = product;
        this.amountToBeFilled = fullAmountRequested-amountFilled;
        this.isVisibleToSupplier = isVisibleToSupplier;
        this.FirstRequestId = firstRequestId;
    }

    public Requests(int requestId, Product product,  int amount, int firstRequestId) {
        this.requestId = requestId;
        this.product = product;
        this.amountFilled = amount;
        this.fullAmountRequested = amount;
        this.isComplete = true;
        this.amountToBeFilled = 0;
        this.isVisibleToSupplier = true;
        this.FirstRequestId = firstRequestId;
    }

    public Requests(int requestId, Product product,  int fullAmountRequested) {
        this.requestId = requestId;
        this.product = product;
        this.fullAmountRequested = fullAmountRequested;
        isComplete = false;
        amountToBeFilled = fullAmountRequested;
        this.isVisibleToSupplier = true;
    }

    public void completeRequest(){
        this.amountFilled = this.fullAmountRequested;
        this.isComplete = true;
    }
}
