package com.heditra.saga.compensation;

import com.heditra.events.core.EventPublisher;
import com.heditra.saga.model.SagaInstance;
import com.heditra.saga.model.SagaStep;
import com.heditra.saga.model.StepStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompensationHandler {
    
    private final EventPublisher eventPublisher;
    
    public void compensate(SagaInstance saga) {
        log.info("Starting compensation for saga: {}", saga.getSagaId());
        
        saga.getSteps().stream()
                .filter(step -> step.getStatus() == StepStatus.COMPLETED)
                .sorted(Comparator.comparing(SagaStep::getExecutedAt).reversed())
                .forEach(this::executeCompensation);
        
        log.info("Compensation completed for saga: {}", saga.getSagaId());
    }
    
    private void executeCompensation(SagaStep step) {
        log.info("Compensating step: {} for saga: {}", step.getStepName(), step.getSaga().getSagaId());
        
        try {
            switch (step.getStepName()) {
                case "inventory-reservation":
                    compensateInventoryReservation(step);
                    break;
                case "payment-initiation":
                    compensatePaymentInitiation(step);
                    break;
                case "notification-sending":
                    // No compensation needed for notifications
                    log.debug("No compensation needed for notification step");
                    break;
                default:
                    log.warn("Unknown step name for compensation: {}", step.getStepName());
            }
            
            step.setStatus(StepStatus.COMPENSATED);
            step.setCompensatedAt(LocalDateTime.now());
            log.info("Step compensated: {}", step.getStepName());
            
        } catch (Exception e) {
            log.error("Failed to compensate step: {}", step.getStepName(), e);
            step.setErrorMessage("Compensation failed: " + e.getMessage());
        }
    }
    
    private void compensateInventoryReservation(SagaStep step) {
        log.info("Releasing inventory reservation for ticket: {}", step.getSaga().getTicketId());
        // Publish event to release inventory
        // In real implementation, you would publish an InventoryReleaseCommand
    }
    
    private void compensatePaymentInitiation(SagaStep step) {
        log.info("Cancelling payment for ticket: {}", step.getSaga().getTicketId());
        // Publish event to cancel payment
        // In real implementation, you would publish a PaymentCancelCommand
    }
}
