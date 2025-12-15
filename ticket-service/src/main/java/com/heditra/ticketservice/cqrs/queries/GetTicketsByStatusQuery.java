package com.heditra.ticketservice.cqrs.queries;

import com.heditra.ticketservice.model.TicketStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTicketsByStatusQuery {
    
    private TicketStatus status;
}
