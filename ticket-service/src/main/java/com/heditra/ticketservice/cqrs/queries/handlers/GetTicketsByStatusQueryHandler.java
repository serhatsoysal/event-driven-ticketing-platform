package com.heditra.ticketservice.cqrs.queries.handlers;

import com.heditra.ticketservice.cqrs.common.QueryResult;
import com.heditra.ticketservice.cqrs.queries.GetTicketsByStatusQuery;
import com.heditra.ticketservice.model.Ticket;
import com.heditra.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetTicketsByStatusQueryHandler {
    
    private final TicketRepository ticketRepository;
    
    @Transactional(readOnly = true)
    public QueryResult<List<Ticket>> handle(GetTicketsByStatusQuery query) {
        log.debug("Handling GetTicketsByStatusQuery for status: {}", query.getStatus());
        
        List<Ticket> tickets = ticketRepository.findByStatus(query.getStatus());
        return QueryResult.success(tickets);
    }
}
