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
public class PaymentCompletedEvent extends DomainEvent {
    
    private Long paymentId;
    private Long ticketId;
    private Long userId;
    private BigDecimal amount;
    private String transactionId;
    
    @Builder
    public PaymentCompletedEvent(String eventId, String aggregateId, Integer version,
                                 Long paymentId, Long ticketId, Long userId,
                                 BigDecimal amount, String transactionId) {
        super(eventId, "PaymentCompleted", aggregateId, version);
        this.paymentId = paymentId;
        this.ticketId = ticketId;
        this.userId = userId;
        this.amount = amount;
        this.transactionId = transactionId;
    }
}
