package com.heditra.events.core;

import java.util.concurrent.CompletableFuture;

public interface EventPublisher {
    
    <T extends DomainEvent> CompletableFuture<Void> publish(T event);
    
    <T extends DomainEvent> CompletableFuture<Void> publish(String topic, T event);
}
