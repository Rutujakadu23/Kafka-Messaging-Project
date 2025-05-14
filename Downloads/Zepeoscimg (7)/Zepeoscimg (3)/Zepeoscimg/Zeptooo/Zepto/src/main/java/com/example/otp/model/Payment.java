package com.example.otp.model;


import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long buyerId;

    private Double originalAmount;
    private Double gstAmount;
    private Double deliveryCharge;
    private Double taxAmount;

    private Double discountAmount;
    private Double savedAmount;
    private Double finalAmount;

    private String couponCode;
    private String paymentId;
    private String status;  // CREATED, PAID, FAILED

    public Payment() {}

    public Payment(Long buyerId, Double originalAmount, Double gstAmount, Double deliveryCharge, Double taxAmount,
                   Double discountAmount, Double savedAmount, Double finalAmount, String couponCode, String paymentId, String status) {
        this.buyerId = buyerId;
        this.originalAmount = originalAmount;
        this.gstAmount = gstAmount;
        this.deliveryCharge = deliveryCharge;
        this.taxAmount = taxAmount;
        this.discountAmount = discountAmount;
        this.savedAmount = savedAmount;
        this.finalAmount = finalAmount;
        this.couponCode = couponCode;
        this.paymentId = paymentId;
        this.status = status;
    }

    // Getters and Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getBuyerId() { return buyerId; }

    public void setBuyerId(Long buyerId) { this.buyerId = buyerId; }

    public Double getOriginalAmount() { return originalAmount; }

    public void setOriginalAmount(Double originalAmount) { this.originalAmount = originalAmount; }

    public Double getGstAmount() { return gstAmount; }

    public void setGstAmount(Double gstAmount) { this.gstAmount = gstAmount; }

    public Double getDeliveryCharge() { return deliveryCharge; }

    public void setDeliveryCharge(Double deliveryCharge) { this.deliveryCharge = deliveryCharge; }

    public Double getTaxAmount() { return taxAmount; }

    public void setTaxAmount(Double taxAmount) { this.taxAmount = taxAmount; }

    public Double getDiscountAmount() { return discountAmount; }

    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }

    public Double getSavedAmount() { return savedAmount; }

    public void setSavedAmount(Double savedAmount) { this.savedAmount = savedAmount; }

    public Double getFinalAmount() { return finalAmount; }

    public void setFinalAmount(Double finalAmount) { this.finalAmount = finalAmount; }

    public String getCouponCode() { return couponCode; }

    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }

    public String getPaymentId() { return paymentId; }

    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
