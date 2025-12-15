package com.heditra.ticketservice.cqrs.queries.handlers;

import com.heditra.ticketservice.cqrs.common.QueryResult;
import com.heditra.ticketservice.cqrs.queries.GetTicketQuery;
import com.heditra.ticketservice.model.Ticket;
import com.heditra.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetTicketQueryHandler {
    
    private final TicketRepository ticketRepository;
    
    @Transactional(readOnly = true)
    @Cacheable(value = "tickets", key = "#query.ticketId")
    public QueryResult<Ticket> handle(GetTicketQuery query) {
        log.debug("Handling GetTicketQuery for ticket ID: {}", query.getTicketId());
        
        return ticketRepository.findById(query.getTicketId())
                .map(QueryResult::success)
                .orElse(QueryResult.failure("Ticket not found", "TICKET_NOT_FOUND"));
    }
}
