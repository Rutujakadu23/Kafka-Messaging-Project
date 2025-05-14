package com.example.otp.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.otp.dto.BuyerLoginRequest;
import com.example.otp.dto.SupplierLoginRequest;
import com.example.otp.model.Buyer;
import com.example.otp.model.Supplier;
import com.example.otp.repository.BuyerRepository;
import com.example.otp.repository.SupplierRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    // 🧾 Buyer Login (phone + password)
    @PostMapping("/buyer/login")
    public ResponseEntity<?> loginBuyer(@RequestBody BuyerLoginRequest loginRequest) {
        Optional<Buyer> buyerOpt = buyerRepository.findByPhoneNumber(loginRequest.getPhoneNumber());
        if (buyerOpt.isPresent()) {
            Buyer buyer = buyerOpt.get();
            if (buyer.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok("Buyer login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid password");
            }
        } else {
            return ResponseEntity.status(404).body("Buyer not found");
        }
    }

    @PostMapping("/supplier/login")
    public ResponseEntity<?> loginSupplier(@RequestBody SupplierLoginRequest loginRequest) {
        Supplier supplier = supplierRepository.findByEmail(loginRequest.getEmail());
        
        if (supplier != null) {
            if (supplier.getPassword().equals(loginRequest.getPassword())) {
                // Success response with JSON format
                Map<String, String> response = new HashMap<>();
                response.put("status", "success");
                response.put("message", "Supplier login successful");
                return ResponseEntity.ok(response);
            } else {
                // Invalid password response with JSON format
                Map<String, String> response = new HashMap<>();
                response.put("status", "error");
                response.put("message", "Invalid password");
                return ResponseEntity.status(401).body(response);
            }
        } else {
            // Supplier not found response with JSON format
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Supplier not found");
            return ResponseEntity.status(404).body(response);
        }
    }


}
