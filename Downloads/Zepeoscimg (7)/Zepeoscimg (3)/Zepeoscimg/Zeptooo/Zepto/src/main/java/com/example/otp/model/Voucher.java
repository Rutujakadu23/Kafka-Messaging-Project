package com.example.otp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

@Entity
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private double discountValue;
    private boolean isRedeemed;

    @Version
    private Integer version;
    
    // Default Constructor
    public Voucher() {}

    // Parameterized Constructor
    public Voucher(String code, double discountValue, boolean isRedeemed) {
        this.code = code;
        this.discountValue = discountValue;
        this.isRedeemed = isRedeemed;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public double getDiscountValue() {
        return discountValue;
    }
    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }
    public boolean isRedeemed() {
        return isRedeemed;
    }
    public void setRedeemed(boolean isRedeemed) {
        this.isRedeemed = isRedeemed;
    }
}

