package com.heditra.events.inventory;

import com.heditra.events.core.DomainEvent;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InventoryReleasedEvent extends DomainEvent {
    
    private String eventName;
    private Integer quantity;
    private Long ticketId;
    private Long reservationId;
    private String releaseReason;
    
    @Builder
    public InventoryReleasedEvent(String eventId, String aggregateId, Integer version,
                                  String eventName, Integer quantity, Long ticketId,
                                  Long reservationId, String releaseReason) {
        super(eventId, "InventoryReleased", aggregateId, version);
        this.eventName = eventName;
        this.quantity = quantity;
        this.ticketId = ticketId;
        this.reservationId = reservationId;
        this.releaseReason = releaseReason;
    }
}
