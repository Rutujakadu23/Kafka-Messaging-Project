package com.example.otp.repository;

import com.example.otp.model.Product;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find products by name containing a specific substring
	 // Find products where the name contains the specified string
    List<Product> findByNameContaining(String name);

    // Find products where the description contains the specified string
    List<Product> findByDescriptionContaining(String description);
   
    @EntityGraph(attributePaths = {"supplier"})
    Optional<Product> findWithSupplierById(Long id);

    
    List<Product> findByStockQuantity(int stockQuantity); 
    
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
   }
