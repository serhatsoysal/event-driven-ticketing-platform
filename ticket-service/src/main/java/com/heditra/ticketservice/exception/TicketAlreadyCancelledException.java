package com.heditra.ticketservice.exception;

public class TicketAlreadyCancelledException extends BusinessException {
    
    public TicketAlreadyCancelledException(String message) {
        super(message, "TICKET_ALREADY_CANCELLED");
    }
}
