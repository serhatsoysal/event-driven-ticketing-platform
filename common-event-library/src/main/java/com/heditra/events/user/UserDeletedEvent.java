package com.heditra.events.user;

import com.heditra.events.core.DomainEvent;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDeletedEvent extends DomainEvent {
    
    private Long userId;
    
    @Builder
    public UserDeletedEvent(String eventId, String aggregateId, Integer version, Long userId) {
        super(eventId, "UserDeleted", aggregateId, version);
        this.userId = userId;
    }
}
