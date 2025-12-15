package com.heditra.events.ticket;

import com.heditra.events.core.DomainEvent;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TicketConfirmedEvent extends DomainEvent {
    
    private Long ticketId;
    private Long userId;
    private String eventName;
    
    @Builder
    public TicketConfirmedEvent(String eventId, String aggregateId, Integer version,
                                Long ticketId, Long userId, String eventName) {
        super(eventId, "TicketConfirmed", aggregateId, version);
        this.ticketId = ticketId;
        this.userId = userId;
        this.eventName = eventName;
    }
}
