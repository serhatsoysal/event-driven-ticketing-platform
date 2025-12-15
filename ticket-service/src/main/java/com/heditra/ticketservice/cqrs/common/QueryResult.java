package com.heditra.ticketservice.cqrs.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryResult<T> {
    
    private boolean success;
    private T data;
    private String message;
    private String errorCode;
    
    public static <T> QueryResult<T> success(T data) {
        return QueryResult.<T>builder()
                .success(true)
                .data(data)
                .build();
    }
    
    public static <T> QueryResult<T> failure(String message, String errorCode) {
        return QueryResult.<T>builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .build();
    }
}
