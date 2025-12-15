package com.heditra.events.core;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public abstract class DomainEvent implements Serializable {
    
    private String eventId;
    private String eventType;
    private LocalDateTime occurredAt;
    private String aggregateId;
    private Integer version;
    
    protected DomainEvent(String eventId, String eventType, String aggregateId, Integer version) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.occurredAt = LocalDateTime.now();
        this.aggregateId = aggregateId;
        this.version = version;
    }
}
