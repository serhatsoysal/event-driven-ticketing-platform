package com.heditra.events.inventory;

import com.heditra.events.core.DomainEvent;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InventoryReservedEvent extends DomainEvent {
    
    private String eventName;
    private Integer quantity;
    private Long ticketId;
    private Long reservationId;
    
    @Builder
    public InventoryReservedEvent(String eventId, String aggregateId, Integer version,
                                  String eventName, Integer quantity, Long ticketId, Long reservationId) {
        super(eventId, "InventoryReserved", aggregateId, version);
        this.eventName = eventName;
        this.quantity = quantity;
        this.ticketId = ticketId;
        this.reservationId = reservationId;
    }
}
