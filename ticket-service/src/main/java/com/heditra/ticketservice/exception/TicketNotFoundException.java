package com.heditra.ticketservice.exception;

public class TicketNotFoundException extends BusinessException {
    
    public TicketNotFoundException(String message) {
        super(message, "TICKET_NOT_FOUND");
    }
    
    public TicketNotFoundException(Long ticketId) {
        super("Ticket not found with ID: " + ticketId, "TICKET_NOT_FOUND");
    }
}
