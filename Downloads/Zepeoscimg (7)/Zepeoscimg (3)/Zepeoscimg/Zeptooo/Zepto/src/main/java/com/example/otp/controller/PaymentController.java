package com.example.otp.controller;

import com.example.otp.service.PaymentService;
import com.razorpay.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin("*")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestParam Long buyerId,
                                         @RequestParam Double amount,
                                         @RequestParam(required = false) String couponCode) {
        try {
            Order order = paymentService.createOrder(buyerId, amount, couponCode);
            return ResponseEntity.ok(order.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to create payment order: " + e.getMessage());
        }
    }

    @PostMapping("/status")
    public ResponseEntity<?> updateStatus(@RequestParam String paymentId,
                                          @RequestParam String status) {
        try {
            paymentService.updatePaymentStatus(paymentId, status);
            return ResponseEntity.ok("Payment status updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating payment status: " + e.getMessage());
        }
    }
}
