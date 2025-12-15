package com.heditra.apigateway.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
    
    private boolean success;
    private String errorCode;
    private String message;
    private LocalDateTime timestamp;
    private String path;
    
    public static ApiErrorResponse of(String errorCode, String message, String path) {
        return ApiErrorResponse.builder()
                .success(false)
                .errorCode(errorCode)
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }
}
