package com.heditra.events.sourcing;

import com.heditra.events.core.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventStore implements EventStore {
    
    private static final String EVENT_STORE_TOPIC = "event-store";
    
    private final KafkaTemplate<String, DomainEvent> kafkaTemplate;
    
    // In-memory cache for demonstration - in production use a proper event store database
    private final Map<String, List<DomainEvent>> eventCache = new ConcurrentHashMap<>();
    
    @Override
    public void save(DomainEvent event) {
        log.debug("Saving event to event store: {}", event.getEventType());
        
        kafkaTemplate.send(EVENT_STORE_TOPIC, event.getAggregateId(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        cacheEvent(event);
                        log.debug("Event saved to event store: {}", event.getEventType());
                    } else {
                        log.error("Failed to save event to event store: {}", event.getEventType(), ex);
                    }
                });
    }
    
    @Override
    public void saveAll(List<DomainEvent> events) {
        events.forEach(this::save);
    }
    
    @Override
    public List<DomainEvent> getEvents(String aggregateId) {
        return eventCache.getOrDefault(aggregateId, new ArrayList<>());
    }
    
    @Override
    public List<DomainEvent> getEvents(String aggregateId, Integer fromVersion) {
        return getEvents(aggregateId).stream()
                .filter(event -> event.getVersion() >= fromVersion)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<DomainEvent> getEventsByType(String eventType) {
        return eventCache.values().stream()
                .flatMap(List::stream)
                .filter(event -> event.getEventType().equals(eventType))
                .collect(Collectors.toList());
    }
    
    private void cacheEvent(DomainEvent event) {
        eventCache.computeIfAbsent(event.getAggregateId(), k -> new ArrayList<>()).add(event);
    }
}
