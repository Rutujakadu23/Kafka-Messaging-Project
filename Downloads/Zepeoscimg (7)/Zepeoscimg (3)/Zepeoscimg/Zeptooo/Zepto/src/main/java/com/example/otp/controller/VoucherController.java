package com.example.otp.controller;

import com.example.otp.model.Voucher;
import com.example.otp.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vouchers")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    // Add or update voucher
    @PostMapping("/save")
    public ResponseEntity<Voucher> saveVoucher(@RequestBody Voucher voucher) {
        try {
            Voucher savedVoucher = voucherService.saveVoucher(voucher);
            return ResponseEntity.ok(savedVoucher);
        } catch (RuntimeException e) {
            // Handle the exception, e.g., by returning an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get all vouchers
    @GetMapping
    public ResponseEntity<List<Voucher>> getAllVouchers() {
        return ResponseEntity.ok(voucherService.getAllVouchers());
    }

    // Get voucher by ID
    @GetMapping("/{id}")
    public ResponseEntity<Voucher> getVoucherById(@PathVariable Long id) {
        return ResponseEntity.ok(voucherService.getVoucherById(id));
    }

    // Delete voucher by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Long id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.ok().build();
    }
}
