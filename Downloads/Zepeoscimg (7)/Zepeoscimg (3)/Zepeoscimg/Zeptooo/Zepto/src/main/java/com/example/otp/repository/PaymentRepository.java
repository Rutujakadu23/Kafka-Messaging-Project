package com.example.otp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.otp.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByPaymentId(String paymentId);
    
    
}