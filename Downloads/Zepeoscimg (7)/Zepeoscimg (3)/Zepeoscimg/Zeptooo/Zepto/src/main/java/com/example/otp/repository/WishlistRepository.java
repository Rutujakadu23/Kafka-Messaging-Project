package com.example.otp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.otp.model.Buyer;
import com.example.otp.model.Wishlist;


import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    
    // Find wishlist by buyer
    Optional<Wishlist> findByBuyer(Buyer buyer);
    
    Optional<Wishlist> findByBuyerId(Long buyerId); // Custom query method

    // Find all items in the wishlist of a buyer
    @Query("SELECT w FROM Wishlist w JOIN w.products p WHERE w.buyer.id = :buyerId")
    List<Wishlist> findWishlistItemsByBuyer(@Param("buyerId") Long buyerId);

    
    
   
}
