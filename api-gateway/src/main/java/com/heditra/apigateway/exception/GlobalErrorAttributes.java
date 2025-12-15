package com.heditra.apigateway.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@Slf4j
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
        
        log.error("Gateway error: {} at {}", errorAttributes.get("error"), errorAttributes.get("path"));
        
        errorAttributes.put("success", false);
        errorAttributes.put("errorCode", errorAttributes.get("error"));
        errorAttributes.put("timestamp", LocalDateTime.now().toString());
        
        return errorAttributes;
    }
}
