package com.heditra.ticketservice.exception;

public class InvalidTicketStatusException extends ValidationException {
    
    public InvalidTicketStatusException(String message) {
        super(message, "INVALID_TICKET_STATUS");
    }
}
