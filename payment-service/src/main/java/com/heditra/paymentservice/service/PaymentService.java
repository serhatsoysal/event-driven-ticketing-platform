package com.heditra.paymentservice.service;

import com.heditra.paymentservice.dto.request.CreatePaymentRequest;
import com.heditra.paymentservice.dto.response.PaymentResponse;
import com.heditra.paymentservice.model.PaymentStatus;

import java.util.List;

public interface PaymentService {
    
    PaymentResponse createPayment(CreatePaymentRequest request);
    
    PaymentResponse getPaymentById(Long id);
    
    PaymentResponse getPaymentByTicketId(Long ticketId);
    
    PaymentResponse getPaymentByTransactionId(String transactionId);
    
    List<PaymentResponse> getAllPayments();
    
    List<PaymentResponse> getPaymentsByUserId(Long userId);
    
    List<PaymentResponse> getPaymentsByStatus(PaymentStatus status);
    
    PaymentResponse processPayment(Long paymentId);
    
    PaymentResponse refundPayment(Long paymentId);
    
    void deletePayment(Long id);
}
