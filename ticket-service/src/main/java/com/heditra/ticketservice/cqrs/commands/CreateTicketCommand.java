package com.heditra.ticketservice.cqrs.commands;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateTicketCommand {
    
    private Long userId;
    private String eventName;
    private Integer quantity;
    private BigDecimal pricePerTicket;
}
