package com.example.otp.controller;

import com.example.otp.model.Coupon;
import com.example.otp.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;
    
    
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    // Endpoint to apply a coupon and get the updated total price
    @PostMapping("/apply")
    public ResponseEntity<String> applyCoupon(
            @RequestParam String couponCode,
            @RequestParam Double totalAmount) {

        try {
            // Find the coupon using the coupon code
            Coupon coupon = couponService.applyCoupon(couponCode);

            // Calculate the total after applying the coupon
            Double updatedTotal = couponService.applyDiscount(totalAmount, coupon.getDiscountAmount());

            // Return the updated total after applying the coupon
            return ResponseEntity.ok("Coupon applied successfully! Updated Total: " + updatedTotal);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Endpoint to validate if a coupon code exists
    @GetMapping("/validate/{couponCode}")
    public ResponseEntity<String> validateCoupon(@PathVariable String couponCode) {
        try {
            Coupon coupon = couponService.applyCoupon(couponCode); // Check if coupon exists
            return ResponseEntity.ok("Coupon is valid with a discount of: " + coupon.getDiscountAmount());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    

    // Add or update coupon
    @PostMapping("/save")
    public ResponseEntity<Coupon> saveCoupon(@RequestBody Coupon coupon) {
        return ResponseEntity.ok(couponService.saveCoupon(coupon));
    }

    // Get all coupons
    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }

    // Get coupon by ID
    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {
        return ResponseEntity.ok(couponService.getCouponById(id));
    }

    // Delete coupon by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.ok().build();
    }

    // ✅ Get best applicable coupon based on order amount
    @GetMapping("/best")
    public ResponseEntity<Coupon> getBestApplicableCoupon(@RequestParam double orderAmount) {
        Coupon bestCoupon = couponService.getBestApplicableCoupon(orderAmount);
        if (bestCoupon != null) {
            return ResponseEntity.ok(bestCoupon);
        } else {
            return ResponseEntity.noContent().build(); // no suitable coupon found
        }
    }
}
