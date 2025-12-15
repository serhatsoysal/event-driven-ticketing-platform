package com.heditra.events.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heditra.events.core.DomainEvent;
import com.heditra.events.core.EventHandler;
import com.heditra.events.core.EventProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class KafkaEventListener {
    
    private final Map<Class<? extends DomainEvent>, EventHandler<? extends DomainEvent>> handlers = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;
    
    public KafkaEventListener(List<EventHandler<? extends DomainEvent>> handlersList, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        handlersList.forEach(handler -> handlers.put(handler.getEventType(), handler));
        log.info("Registered {} event handlers", handlers.size());
    }
    
    public <T extends DomainEvent> void registerHandler(EventHandler<T> handler) {
        handlers.put(handler.getEventType(), handler);
        log.info("Registered handler for event type: {}", handler.getEventType().getSimpleName());
    }
    
    @SuppressWarnings("unchecked")
    protected <T extends DomainEvent> void processEvent(T event) {
        log.debug("Processing event: {}", event.getEventType());
        
        EventHandler<T> handler = (EventHandler<T>) handlers.get(event.getClass());
        
        if (handler != null) {
            try {
                handler.handle(event);
                log.debug("Event processed successfully: {}", event.getEventType());
            } catch (Exception e) {
                log.error("Error processing event: {}", event.getEventType(), e);
                throw new EventProcessingException("Failed to process event: " + event.getEventType(), e);
            }
        } else {
            log.warn("No handler found for event type: {}", event.getClass().getSimpleName());
        }
    }
    
    protected Optional<EventHandler<? extends DomainEvent>> findHandler(Class<? extends DomainEvent> eventType) {
        return Optional.ofNullable(handlers.get(eventType));
    }
}
