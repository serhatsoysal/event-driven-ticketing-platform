package com.heditra.ticketservice.cqrs.commands.handlers;

import com.heditra.events.core.EventPublisher;
import com.heditra.events.ticket.TicketConfirmedEvent;
import com.heditra.ticketservice.cqrs.commands.UpdateTicketStatusCommand;
import com.heditra.ticketservice.cqrs.common.CommandResult;
import com.heditra.ticketservice.model.Ticket;
import com.heditra.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateTicketStatusCommandHandler {
    
    private final TicketRepository ticketRepository;
    private final EventPublisher eventPublisher;
    
    @Transactional
    public CommandResult<Void> handle(UpdateTicketStatusCommand command) {
        log.info("Handling UpdateTicketStatusCommand for ticket: {}, status: {}", 
                command.getTicketId(), command.getStatus());
        
        Ticket ticket = ticketRepository.findById(command.getTicketId())
                .orElse(null);
        
        if (ticket == null) {
            return CommandResult.failure("Ticket not found", "TICKET_NOT_FOUND");
        }
        
        ticket.setStatus(command.getStatus());
        ticket.setUpdatedAt(java.time.LocalDateTime.now());
        ticketRepository.save(ticket);
        
        // Publish event if confirmed
        if (command.getStatus() == com.heditra.ticketservice.model.TicketStatus.CONFIRMED) {
            publishTicketConfirmedEvent(ticket);
        }
        
        log.info("Ticket status updated successfully for ID: {}", command.getTicketId());
        return CommandResult.success(null, "Ticket status updated successfully");
    }
    
    private void publishTicketConfirmedEvent(Ticket ticket) {
        TicketConfirmedEvent event = TicketConfirmedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .aggregateId(ticket.getId().toString())
                .version(2)
                .ticketId(ticket.getId())
                .userId(ticket.getUserId())
                .eventName(ticket.getEventName())
                .build();
        
        eventPublisher.publish("ticket-confirmed", event);
    }
}
