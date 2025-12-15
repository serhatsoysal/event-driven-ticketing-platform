package com.heditra.paymentservice.mapper;

import com.heditra.paymentservice.dto.request.CreatePaymentRequest;
import com.heditra.paymentservice.dto.response.PaymentResponse;
import com.heditra.paymentservice.model.Payment;
import com.heditra.paymentservice.model.PaymentStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMapper {
    
    public Payment toEntity(CreatePaymentRequest request) {
        return Payment.builder()
                .ticketId(request.getTicketId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .build();
    }
    
    public PaymentResponse toResponse(Payment entity) {
        return PaymentResponse.builder()
                .id(entity.getId())
                .ticketId(entity.getTicketId())
                .userId(entity.getUserId())
                .amount(entity.getAmount())
                .paymentMethod(entity.getPaymentMethod())
                .status(entity.getStatus())
                .transactionId(entity.getTransactionId())
                .createdAt(entity.getCreatedAt())
                .build();
    }
    
    public List<PaymentResponse> toResponseList(List<Payment> entities) {
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
