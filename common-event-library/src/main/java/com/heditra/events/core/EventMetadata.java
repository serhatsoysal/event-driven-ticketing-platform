package com.heditra.events.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventMetadata {
    
    private String eventId;
    private String eventType;
    private String source;
    private String correlationId;
    private String causationId;
    private Long timestamp;
}
