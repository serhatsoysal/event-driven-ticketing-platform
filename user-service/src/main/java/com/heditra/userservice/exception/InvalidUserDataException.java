package com.heditra.userservice.exception;

public class InvalidUserDataException extends ValidationException {
    
    public InvalidUserDataException(String message) {
        super(message, "INVALID_USER_DATA");
    }
}
