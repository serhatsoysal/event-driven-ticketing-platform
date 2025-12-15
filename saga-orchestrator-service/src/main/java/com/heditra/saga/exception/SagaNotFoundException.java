package com.heditra.saga.exception;

public class SagaNotFoundException extends BusinessException {
    
    public SagaNotFoundException(String message) {
        super(message, "SAGA_NOT_FOUND");
    }
    
    public SagaNotFoundException(Long sagaId) {
        super("Saga not found with ID: " + sagaId, "SAGA_NOT_FOUND");
    }
}
