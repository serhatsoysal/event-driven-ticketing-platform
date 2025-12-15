package com.heditra.ticketservice.cqrs.commands;

import com.heditra.ticketservice.model.TicketStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTicketStatusCommand {
    
    private Long ticketId;
    private TicketStatus status;
}
