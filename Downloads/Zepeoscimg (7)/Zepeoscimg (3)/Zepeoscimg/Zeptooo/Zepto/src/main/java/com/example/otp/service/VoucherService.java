package com.example.otp.service;

import com.example.otp.model.Voucher;
import com.example.otp.repository.VoucherRepository;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;


    @Transactional
    public Voucher saveVoucher(Voucher voucher) {
        try {
            return voucherRepository.save(voucher);
        } catch (OptimisticLockException e) {
            // Handle the exception, e.g., by retrying the operation or notifying the user
            throw new RuntimeException("Optimistic locking failure: " + e.getMessage());
        }
    }
    // Get all vouchers
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    // Get voucher by ID
    public Voucher getVoucherById(Long id) {
        return voucherRepository.findById(id).orElse(null);
    }

    // Delete voucher by ID
    public void deleteVoucher(Long id) {
        voucherRepository.deleteById(id);
    }
}
