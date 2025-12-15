package com.heditra.events.core;

public interface EventHandler<T extends DomainEvent> {
    
    void handle(T event);
    
    Class<T> getEventType();
}
