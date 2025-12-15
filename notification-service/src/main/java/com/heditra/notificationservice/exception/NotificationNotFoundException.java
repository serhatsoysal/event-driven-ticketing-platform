package com.heditra.notificationservice.exception;

public class NotificationNotFoundException extends BusinessException {
    
    public NotificationNotFoundException(String message) {
        super(message, "NOTIFICATION_NOT_FOUND");
    }
    
    public NotificationNotFoundException(Long notificationId) {
        super("Notification not found with ID: " + notificationId, "NOTIFICATION_NOT_FOUND");
    }
}
