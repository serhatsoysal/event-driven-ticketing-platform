package com.heditra.paymentservice.dto.response;

import com.heditra.paymentservice.model.PaymentMethod;
import com.heditra.paymentservice.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    
    private Long id;
    private Long ticketId;
    private Long userId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String transactionId;
    private LocalDateTime createdAt;
}
