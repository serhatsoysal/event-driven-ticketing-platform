package com.heditra.events.payment;

import com.heditra.events.core.DomainEvent;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentFailedEvent extends DomainEvent {
    
    private Long paymentId;
    private Long ticketId;
    private Long userId;
    private BigDecimal amount;
    private String transactionId;
    private String failureReason;
    
    @Builder
    public PaymentFailedEvent(String eventId, String aggregateId, Integer version,
                              Long paymentId, Long ticketId, Long userId,
                              BigDecimal amount, String transactionId, String failureReason) {
        super(eventId, "PaymentFailed", aggregateId, version);
        this.paymentId = paymentId;
        this.ticketId = ticketId;
        this.userId = userId;
        this.amount = amount;
        this.transactionId = transactionId;
        this.failureReason = failureReason;
    }
}
