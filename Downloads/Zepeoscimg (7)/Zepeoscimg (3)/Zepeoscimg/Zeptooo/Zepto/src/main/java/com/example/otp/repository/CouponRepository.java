package com.example.otp.repository;

import com.example.otp.model.Coupon;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
	
	 Optional<Coupon> findByCode(String code);
}
