package com.example.otp.repository;

import com.example.otp.model.Buyer;
import com.example.otp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Find a cart by Buyer
    Optional<Cart> findByBuyer(Buyer buyer);

    // Find all carts belonging to a buyer
    List<Cart> findByBuyerId(Long buyerId);

    // Find a cart that contains a specific product for a buyer
    @Query("SELECT c FROM Cart c JOIN c.cartItems ci WHERE c.buyer.id = :buyerId AND ci.product.id = :productId")
    Optional<Cart> findCartItemByBuyerAndProduct(@Param("buyerId") Long buyerId, @Param("productId") Long productId);

    // ✅ Fetch cart with buyer eagerly
    @Query("SELECT c FROM Cart c JOIN FETCH c.buyer WHERE c.id = :cartId")
    Optional<Cart> findByIdWithBuyer(@Param("cartId") Long cartId);
}
