package com.heditra.saga.model;

public enum SagaStatus {
    STARTED,
    IN_PROGRESS,
    COMPLETED,
    COMPENSATING,
    COMPENSATED,
    FAILED
}
