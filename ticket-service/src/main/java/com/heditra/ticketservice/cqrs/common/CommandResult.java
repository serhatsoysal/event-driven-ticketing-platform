package com.heditra.ticketservice.cqrs.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandResult<T> {
    
    private boolean success;
    private T data;
    private String message;
    private String errorCode;
    
    public static <T> CommandResult<T> success(T data) {
        return CommandResult.<T>builder()
                .success(true)
                .data(data)
                .build();
    }
    
    public static <T> CommandResult<T> success(T data, String message) {
        return CommandResult.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .build();
    }
    
    public static <T> CommandResult<T> failure(String message, String errorCode) {
        return CommandResult.<T>builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .build();
    }
}
