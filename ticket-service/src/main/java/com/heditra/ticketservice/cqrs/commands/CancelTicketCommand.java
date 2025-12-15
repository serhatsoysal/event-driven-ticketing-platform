package com.heditra.ticketservice.cqrs.commands;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelTicketCommand {
    
    private Long ticketId;
    private String cancellationReason;
}
