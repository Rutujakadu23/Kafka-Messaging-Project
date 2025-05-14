package com.example.otp.service;

import com.example.otp.model.Coupon;
import com.example.otp.model.Payment;
import com.example.otp.repository.CouponRepository;
import com.example.otp.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    private final RazorpayClient razorpayClient;
    private final CouponRepository couponRepository;
    private final PaymentRepository paymentRepository;

    public PaymentService(@Value("${razorpay.key}") String key,
            @Value("${razorpay.secret}") String secret,
            CouponRepository couponRepository,
            PaymentRepository paymentRepository) throws Exception {
System.out.println("Key: " + key);
System.out.println("Secret: " + secret); // remove later for security

this.razorpayClient = new RazorpayClient(key, secret);
this.couponRepository = couponRepository;
this.paymentRepository = paymentRepository;
}

    public Order createOrder(Long buyerId, Double baseAmount, String couponCode) throws Exception {
        double gst = baseAmount * 0.18; // 18% GST
        double tax = baseAmount * 0.05; // 5% tax
        double deliveryCharge = 50.0;
        double totalBeforeDiscount = baseAmount + gst + tax + deliveryCharge;

        double discountAmount = 0.0;
        double savedAmount = 0.0;

        if (couponCode != null && !couponCode.isEmpty()) {
            Optional<Coupon> optionalCoupon = couponRepository.findByCode(couponCode);
            if (optionalCoupon.isPresent()) {
                discountAmount = optionalCoupon.get().getDiscountAmount();
                savedAmount = discountAmount;
            } else {
                throw new RuntimeException("Invalid coupon code");
            }
        }

        double finalAmount = totalBeforeDiscount - discountAmount;

        // Save initial payment info
        Payment payment = new Payment();
        payment.setBuyerId(buyerId);
        payment.setOriginalAmount(baseAmount);
        payment.setGstAmount(gst);
        payment.setTaxAmount(tax);
        payment.setDeliveryCharge(deliveryCharge);
        payment.setDiscountAmount(discountAmount);
        payment.setSavedAmount(savedAmount);
        payment.setFinalAmount(finalAmount);
        payment.setCouponCode(couponCode);
        payment.setStatus("CREATED");

        Payment savedPayment = paymentRepository.save(payment);

        // Create Razorpay order
        JSONObject options = new JSONObject();
        options.put("amount", (int)(finalAmount * 100)); // Amount in paise
        options.put("currency", "INR");
        options.put("receipt", "receipt_" + savedPayment.getId());

        Order order = razorpayClient.orders.create(options);

        // Update payment ID in database
        savedPayment.setPaymentId(order.get("id"));
        paymentRepository.save(savedPayment);

        return order;
    }

    public void updatePaymentStatus(String paymentId, String status) {
        Payment payment = paymentRepository.findByPaymentId(paymentId);
        if (payment != null) {
            payment.setStatus(status);
            paymentRepository.save(payment);
        }
    }
}
