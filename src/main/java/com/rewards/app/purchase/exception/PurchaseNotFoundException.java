package com.rewards.app.purchase.exception;

public class PurchaseNotFoundException extends RuntimeException {
    public PurchaseNotFoundException(Long id) {
        super(String.format("Purchase with id %d not found", id));
    }
}
