package com.heditra.paymentservice.service.impl;

import com.heditra.events.core.EventPublisher;
import com.heditra.events.payment.PaymentCompletedEvent;
import com.heditra.events.payment.PaymentFailedEvent;
import com.heditra.events.payment.PaymentInitiatedEvent;
import com.heditra.events.payment.PaymentRefundedEvent;
import com.heditra.paymentservice.dto.request.CreatePaymentRequest;
import com.heditra.paymentservice.dto.response.PaymentResponse;
import com.heditra.paymentservice.exception.PaymentNotFoundException;
import com.heditra.paymentservice.exception.PaymentProcessingException;
import com.heditra.paymentservice.mapper.PaymentMapper;
import com.heditra.paymentservice.model.Payment;
import com.heditra.paymentservice.model.PaymentStatus;
import com.heditra.paymentservice.repository.PaymentRepository;
import com.heditra.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public PaymentResponse createPayment(CreatePaymentRequest request) {
        log.info("Creating new payment for ticket ID: {}", request.getTicketId());

        Payment payment = paymentMapper.toEntity(request);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTransactionId(generateTransactionId());

        Payment savedPayment = paymentRepository.save(payment);
        
        publishPaymentInitiatedEvent(savedPayment);

        log.info("Payment created successfully with ID: {}", savedPayment.getId());
        return paymentMapper.toResponse(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {
        log.debug("Fetching payment by ID: {}", id);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentByTicketId(Long ticketId) {
        log.debug("Fetching payment by ticket ID: {}", ticketId);
        Payment payment = paymentRepository.findByTicketId(ticketId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found for ticket ID: " + ticketId));
        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentByTransactionId(String transactionId) {
        log.debug("Fetching payment by transaction ID: {}", transactionId);
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found for transaction ID: " + transactionId));
        return paymentMapper.toResponse(payment);
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        log.debug("Fetching all payments");
        List<Payment> payments = paymentRepository.findAll();
        return paymentMapper.toResponseList(payments);
    }

    @Override
    public List<PaymentResponse> getPaymentsByUserId(Long userId) {
        log.debug("Fetching payments for user ID: {}", userId);
        List<Payment> payments = paymentRepository.findByUserId(userId);
        return paymentMapper.toResponseList(payments);
    }

    @Override
    public List<PaymentResponse> getPaymentsByStatus(PaymentStatus status) {
        log.debug("Fetching payments by status: {}", status);
        List<Payment> payments = paymentRepository.findByStatus(status);
        return paymentMapper.toResponseList(payments);
    }

    @Override
    @Transactional
    public PaymentResponse processPayment(Long paymentId) {
        log.info("Processing payment with ID: {}", paymentId);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new PaymentProcessingException("Payment is not in PENDING status");
        }

        boolean paymentSuccess = executePaymentGateway(payment);

        if (paymentSuccess) {
            payment.setStatus(PaymentStatus.SUCCESS);
            Payment successfulPayment = paymentRepository.save(payment);
            publishPaymentCompletedEvent(successfulPayment);
            log.info("Payment processed successfully with ID: {}", paymentId);
            return paymentMapper.toResponse(successfulPayment);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            Payment failedPayment = paymentRepository.save(payment);
            publishPaymentFailedEvent(failedPayment);
            log.warn("Payment processing failed for ID: {}", paymentId);
            return paymentMapper.toResponse(failedPayment);
        }
    }

    @Override
    @Transactional
    public PaymentResponse refundPayment(Long paymentId) {
        log.info("Refunding payment with ID: {}", paymentId);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));

        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            throw new PaymentProcessingException("Only successful payments can be refunded");
        }

        payment.setStatus(PaymentStatus.REFUNDED);
        Payment refundedPayment = paymentRepository.save(payment);
        
        publishPaymentRefundedEvent(refundedPayment);

        log.info("Payment refunded successfully with ID: {}", paymentId);
        return paymentMapper.toResponse(refundedPayment);
    }

    @Override
    @Transactional
    public void deletePayment(Long id) {
        log.info("Deleting payment with ID: {}", id);

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
        
        paymentRepository.delete(payment);

        log.info("Payment deleted successfully with ID: {}", id);
    }

    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }

    private boolean executePaymentGateway(Payment payment) {
        log.debug("Executing payment gateway for transaction: {}", payment.getTransactionId());
        return true;
    }

    private void publishPaymentInitiatedEvent(Payment payment) {
        PaymentInitiatedEvent event = PaymentInitiatedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .aggregateId(payment.getId().toString())
                .version(1)
                .paymentId(payment.getId())
                .ticketId(payment.getTicketId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod().toString())
                .transactionId(payment.getTransactionId())
                .build();
        
        eventPublisher.publish("payment-initiated", event)
                .exceptionally(ex -> {
                    log.error("Failed to publish PaymentInitiatedEvent", ex);
                    return null;
                });
    }

    private void publishPaymentCompletedEvent(Payment payment) {
        PaymentCompletedEvent event = PaymentCompletedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .aggregateId(payment.getId().toString())
                .version(2)
                .paymentId(payment.getId())
                .ticketId(payment.getTicketId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .transactionId(payment.getTransactionId())
                .build();
        
        eventPublisher.publish("payment-completed", event)
                .exceptionally(ex -> {
                    log.error("Failed to publish PaymentCompletedEvent", ex);
                    return null;
                });
    }

    private void publishPaymentFailedEvent(Payment payment) {
        PaymentFailedEvent event = PaymentFailedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .aggregateId(payment.getId().toString())
                .version(2)
                .paymentId(payment.getId())
                .ticketId(payment.getTicketId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .transactionId(payment.getTransactionId())
                .failureReason("Gateway rejected payment")
                .build();
        
        eventPublisher.publish("payment-failed", event)
                .exceptionally(ex -> {
                    log.error("Failed to publish PaymentFailedEvent", ex);
                    return null;
                });
    }

    private void publishPaymentRefundedEvent(Payment payment) {
        PaymentRefundedEvent event = PaymentRefundedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .aggregateId(payment.getId().toString())
                .version(3)
                .paymentId(payment.getId())
                .ticketId(payment.getTicketId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .transactionId(payment.getTransactionId())
                .refundReason("User requested refund")
                .build();
        
        eventPublisher.publish("payment-refunded", event)
                .exceptionally(ex -> {
                    log.error("Failed to publish PaymentRefundedEvent", ex);
                    return null;
                });
    }
}

