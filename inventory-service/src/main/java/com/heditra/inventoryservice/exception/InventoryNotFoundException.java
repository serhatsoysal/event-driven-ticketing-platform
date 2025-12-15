package com.heditra.inventoryservice.exception;

public class InventoryNotFoundException extends BusinessException {
    
    public InventoryNotFoundException(String message) {
        super(message, "INVENTORY_NOT_FOUND");
    }
    
    public InventoryNotFoundException(Long inventoryId) {
        super("Inventory not found with ID: " + inventoryId, "INVENTORY_NOT_FOUND");
    }
}
