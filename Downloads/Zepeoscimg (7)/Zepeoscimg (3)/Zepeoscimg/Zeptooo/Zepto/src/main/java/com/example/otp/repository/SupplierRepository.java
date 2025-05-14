package com.example.otp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.otp.model.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findByEmail(String email);
    
    

    @Query("SELECT s FROM Supplier s LEFT JOIN FETCH s.products")
    List<Supplier> findAllWithProducts();
    

}

