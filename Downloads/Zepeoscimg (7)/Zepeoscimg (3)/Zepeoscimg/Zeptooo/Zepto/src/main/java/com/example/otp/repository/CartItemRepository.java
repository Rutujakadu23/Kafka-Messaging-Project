package com.example.otp.repository;

import com.example.otp.model.Cart;
import com.example.otp.model.CartItem;
import com.example.otp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // New method added for Cart object directly
    List<CartItem> findByCart(Cart cart);

    // Already existing methods
    List<CartItem> findByCartId(Long cartId);
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    void deleteByCartId(Long cartId);
    void deleteByCartAndProduct(Cart cart, Product product);
}
