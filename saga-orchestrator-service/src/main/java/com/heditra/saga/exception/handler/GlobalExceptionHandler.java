package com.heditra.saga.exception.handler;

import com.heditra.saga.dto.common.ApiErrorResponse;
import com.heditra.saga.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(
            BusinessException ex, HttpServletRequest request) {
        log.warn("Business exception occurred: {}", ex.getMessage());
        
        ApiErrorResponse error = ApiErrorResponse.of(
                ex.getErrorCode(),
                ex.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {
        log.error("Unexpected exception occurred: {}", ex.getMessage(), ex);
        
        ApiErrorResponse error = ApiErrorResponse.of(
                "INTERNAL_ERROR",
                "An unexpected error occurred. Please try again later.",
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
