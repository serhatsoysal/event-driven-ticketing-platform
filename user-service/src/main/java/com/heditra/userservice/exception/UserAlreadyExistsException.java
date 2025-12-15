package com.heditra.userservice.exception;

public class UserAlreadyExistsException extends BusinessException {
    
    public UserAlreadyExistsException(String message) {
        super(message, "USER_ALREADY_EXISTS");
    }
}
