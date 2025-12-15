package com.heditra.paymentservice.exception;

public class PaymentProcessingException extends TechnicalException {
    
    public PaymentProcessingException(String message) {
        super(message, "PAYMENT_PROCESSING_ERROR");
    }
    
    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
