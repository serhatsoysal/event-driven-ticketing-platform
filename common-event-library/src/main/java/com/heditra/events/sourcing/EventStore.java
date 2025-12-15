package com.heditra.events.sourcing;

import com.heditra.events.core.DomainEvent;

import java.util.List;

public interface EventStore {
    
    void save(DomainEvent event);
    
    void saveAll(List<DomainEvent> events);
    
    List<DomainEvent> getEvents(String aggregateId);
    
    List<DomainEvent> getEvents(String aggregateId, Integer fromVersion);
    
    List<DomainEvent> getEventsByType(String eventType);
}
