package com.heditra.events.ticket;

import com.heditra.events.core.DomainEvent;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TicketCancelledEvent extends DomainEvent {
    
    private Long ticketId;
    private Long userId;
    private String eventName;
    private Integer quantity;
    private String cancellationReason;
    
    @Builder
    public TicketCancelledEvent(String eventId, String aggregateId, Integer version,
                                Long ticketId, Long userId, String eventName,
                                Integer quantity, String cancellationReason) {
        super(eventId, "TicketCancelled", aggregateId, version);
        this.ticketId = ticketId;
        this.userId = userId;
        this.eventName = eventName;
        this.quantity = quantity;
        this.cancellationReason = cancellationReason;
    }
}
