package com.heditra.events.sourcing;

import com.heditra.events.core.DomainEvent;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public abstract class AggregateRoot {
    
    protected String aggregateId;
    protected Integer version;
    protected final List<DomainEvent> uncommittedEvents = new ArrayList<>();
    
    protected void applyEvent(DomainEvent event) {
        handleEvent(event);
        uncommittedEvents.add(event);
        version++;
    }
    
    protected abstract void handleEvent(DomainEvent event);
    
    public List<DomainEvent> getUncommittedEvents() {
        return Collections.unmodifiableList(uncommittedEvents);
    }
    
    public void clearUncommittedEvents() {
        uncommittedEvents.clear();
    }
    
    public void loadFromHistory(List<DomainEvent> history) {
        history.forEach(this::handleEvent);
        this.version = history.isEmpty() ? 0 : history.get(history.size() - 1).getVersion();
    }
}
