package com.heditra.paymentservice.controller;

import com.heditra.paymentservice.dto.request.CreatePaymentRequest;
import com.heditra.paymentservice.dto.response.ApiResponse;
import com.heditra.paymentservice.dto.response.PaymentResponse;
import com.heditra.paymentservice.model.PaymentStatus;
import com.heditra.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Management", description = "APIs for managing payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Create a new payment", description = "Creates a new payment for a ticket")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Payment created successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        PaymentResponse payment = paymentService.createPayment(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(payment, "Payment created successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID", description = "Retrieves a payment by its ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payment found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(@PathVariable Long id) {
        PaymentResponse payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(ApiResponse.success(payment));
    }

    @GetMapping("/ticket/{ticketId}")
    @Operation(summary = "Get payment by ticket ID", description = "Retrieves a payment by ticket ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payment found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentByTicketId(@PathVariable Long ticketId) {
        PaymentResponse payment = paymentService.getPaymentByTicketId(ticketId);
        return ResponseEntity.ok(ApiResponse.success(payment));
    }

    @GetMapping("/transaction/{transactionId}")
    @Operation(summary = "Get payment by transaction ID", description = "Retrieves a payment by transaction ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payment found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentByTransactionId(@PathVariable String transactionId) {
        PaymentResponse payment = paymentService.getPaymentByTransactionId(transactionId);
        return ResponseEntity.ok(ApiResponse.success(payment));
    }

    @GetMapping
    @Operation(summary = "Get all payments", description = "Retrieves all payments in the system")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payments retrieved successfully")
    })
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {
        List<PaymentResponse> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(ApiResponse.success(payments));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get payments by user ID", description = "Retrieves all payments for a specific user")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payments retrieved successfully")
    })
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByUserId(@PathVariable Long userId) {
        List<PaymentResponse> payments = paymentService.getPaymentsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(payments));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get payments by status", description = "Retrieves all payments with a specific status")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payments retrieved successfully")
    })
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        List<PaymentResponse> payments = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(payments));
    }

    @PostMapping("/{id}/process")
    @Operation(summary = "Process payment", description = "Processes a pending payment")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payment processed successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Payment cannot be processed"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<ApiResponse<PaymentResponse>> processPayment(@PathVariable Long id) {
        PaymentResponse payment = paymentService.processPayment(id);
        return ResponseEntity.ok(ApiResponse.success(payment, "Payment processed successfully"));
    }

    @PostMapping("/{id}/refund")
    @Operation(summary = "Refund payment", description = "Refunds a successful payment")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payment refunded successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Payment cannot be refunded"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<ApiResponse<PaymentResponse>> refundPayment(@PathVariable Long id) {
        PaymentResponse payment = paymentService.refundPayment(id);
        return ResponseEntity.ok(ApiResponse.success(payment, "Payment refunded successfully"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete payment", description = "Deletes a payment from the system")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Payment deleted successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}

