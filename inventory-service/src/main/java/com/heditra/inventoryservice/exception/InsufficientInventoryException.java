package com.heditra.inventoryservice.exception;

public class InsufficientInventoryException extends BusinessException {
    
    public InsufficientInventoryException(String message) {
        super(message, "INSUFFICIENT_INVENTORY");
    }
}
