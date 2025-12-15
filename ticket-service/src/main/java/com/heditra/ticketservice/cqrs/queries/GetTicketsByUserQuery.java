package com.heditra.ticketservice.cqrs.queries;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTicketsByUserQuery {
    
    private Long userId;
}
