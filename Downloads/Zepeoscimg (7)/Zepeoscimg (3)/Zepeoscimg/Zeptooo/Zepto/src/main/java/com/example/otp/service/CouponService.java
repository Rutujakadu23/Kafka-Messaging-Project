package com.example.otp.service;

import com.example.otp.model.Coupon;
import com.example.otp.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;
    
    
    
    
    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    // Find coupon by code
    public Coupon applyCoupon(String couponCode) {
        return couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new RuntimeException("Invalid coupon code"));
    }

    // Apply the fixed discount amount to the total order amount
    public Double applyDiscount(Double totalAmount, Double discountAmount) {
        return totalAmount - discountAmount;  // Subtract the discountAmount from the total price
    }


    // Add or update coupon
    public Coupon saveCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    // Get all coupons
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    // Get coupon by ID
    public Coupon getCouponById(Long id) {
        return couponRepository.findById(id).orElse(null);
    }

    // Delete coupon by ID
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    // ✅ Find best applicable coupon based on order amount
    public Coupon getBestApplicableCoupon(double orderAmount) {
        List<Coupon> allCoupons = couponRepository.findAll();

        Coupon bestCoupon = null;

        for (Coupon coupon : allCoupons) {
            boolean isEligible = coupon.isActive()
                    && coupon.getExpiryDate().isAfter(LocalDate.now())
                    && orderAmount >= coupon.getMinOrderAmount();

            if (isEligible) {
                if (bestCoupon == null || coupon.getDiscountAmount() > bestCoupon.getDiscountAmount()) {
                    bestCoupon = coupon;
                }
            }
        }

        return bestCoupon;
    }
}
