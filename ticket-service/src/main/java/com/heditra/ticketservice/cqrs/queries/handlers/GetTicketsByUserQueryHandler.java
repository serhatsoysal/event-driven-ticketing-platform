package com.heditra.ticketservice.cqrs.queries.handlers;

import com.heditra.ticketservice.cqrs.common.QueryResult;
import com.heditra.ticketservice.cqrs.queries.GetTicketsByUserQuery;
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
public class GetTicketsByUserQueryHandler {
    
    private final TicketRepository ticketRepository;
    
    @Transactional(readOnly = true)
    public QueryResult<List<Ticket>> handle(GetTicketsByUserQuery query) {
        log.debug("Handling GetTicketsByUserQuery for user ID: {}", query.getUserId());
        
        List<Ticket> tickets = ticketRepository.findByUserId(query.getUserId());
        return QueryResult.success(tickets);
    }
}
