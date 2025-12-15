package com.heditra.userservice.exception;

public class UserNotFoundException extends BusinessException {
    
    public UserNotFoundException(String message) {
        super(message, "USER_NOT_FOUND");
    }
    
    public UserNotFoundException(Long userId) {
        super("User not found with ID: " + userId, "USER_NOT_FOUND");
    }
}
