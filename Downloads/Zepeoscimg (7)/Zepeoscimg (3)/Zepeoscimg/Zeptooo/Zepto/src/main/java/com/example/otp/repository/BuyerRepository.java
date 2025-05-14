package com.example.otp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.otp.model.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {

    Optional<Buyer> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);
    
    List<Buyer> findAll();
}
