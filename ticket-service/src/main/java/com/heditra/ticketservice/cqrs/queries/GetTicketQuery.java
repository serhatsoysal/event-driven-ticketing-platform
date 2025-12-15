package com.heditra.ticketservice.cqrs.queries;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTicketQuery {
    
    private Long ticketId;
}
