package com.heditra.paymentservice.exception;

public class PaymentNotFoundException extends BusinessException {
    
    public PaymentNotFoundException(String message) {
        super(message, "PAYMENT_NOT_FOUND");
    }
    
    public PaymentNotFoundException(Long paymentId) {
        super("Payment not found with ID: " + paymentId, "PAYMENT_NOT_FOUND");
    }
}
