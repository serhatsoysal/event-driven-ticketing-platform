package com.heditra.notificationservice.exception;

public class NotificationSendException extends TechnicalException {
    
    public NotificationSendException(String message) {
        super(message, "NOTIFICATION_SEND_ERROR");
    }
    
    public NotificationSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
