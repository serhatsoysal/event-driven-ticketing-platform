package com.heditra.events.sourcing;

import com.heditra.events.core.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventReplayer {
    
    private final EventStore eventStore;
    
    public void replayEvents(String aggregateId, Consumer<DomainEvent> eventConsumer) {
        List<DomainEvent> events = eventStore.getEvents(aggregateId);
        log.info("Replaying {} events for aggregate {}", events.size(), aggregateId);
        events.forEach(eventConsumer);
    }
    
    public void replayEventsByType(String eventType, Consumer<DomainEvent> eventConsumer) {
        List<DomainEvent> events = eventStore.getEventsByType(eventType);
        log.info("Replaying {} events of type {}", events.size(), eventType);
        events.forEach(eventConsumer);
    }
}
