package com.heditra.events.ticket;

import com.heditra.events.core.DomainEvent;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TicketCreatedEvent extends DomainEvent {
    
    private Long ticketId;
    private Long userId;
    private String eventName;
    private Integer quantity;
    private BigDecimal pricePerTicket;
    private BigDecimal totalAmount;
    
    @Builder
    public TicketCreatedEvent(String eventId, String aggregateId, Integer version,
                              Long ticketId, Long userId, String eventName,
                              Integer quantity, BigDecimal pricePerTicket, BigDecimal totalAmount) {
        super(eventId, "TicketCreated", aggregateId, version);
        this.ticketId = ticketId;
        this.userId = userId;
        this.eventName = eventName;
        this.quantity = quantity;
        this.pricePerTicket = pricePerTicket;
        this.totalAmount = totalAmount;
    }
}
