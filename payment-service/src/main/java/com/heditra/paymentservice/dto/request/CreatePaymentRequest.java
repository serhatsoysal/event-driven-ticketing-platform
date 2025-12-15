package com.heditra.paymentservice.dto.request;

import com.heditra.paymentservice.model.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {
    
    @NotNull(message = "Ticket ID is required")
    private Long ticketId;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
}
