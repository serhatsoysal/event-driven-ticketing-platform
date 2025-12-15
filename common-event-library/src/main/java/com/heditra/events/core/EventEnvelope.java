package com.heditra.events.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventEnvelope<T extends DomainEvent> {
    
    private EventMetadata metadata;
    private T payload;
}
