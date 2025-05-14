package com.example.otp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.otp.model.Cart;
import com.example.otp.model.CartItem;
import com.example.otp.model.Product;
import com.example.otp.repository.CartItemRepository;
import com.example.otp.repository.CartRepository;
import com.example.otp.repository.ProductRepository;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    // Get all cart items for a cart
    public List<CartItem> getCartItemsByCartId(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));
        return cartItemRepository.findByCart(cart);
    }

    // Get cart item by ID
    public CartItem getCartItemById(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found with ID: " + id));
    }

    // Update quantity of a cart item
    public CartItem updateCartItemQuantity(Long cartItemId, int newQuantity) {
        CartItem cartItem = getCartItemById(cartItemId);
        cartItem.setQuantity(newQuantity);
        return cartItemRepository.save(cartItem);
    }

    // Delete a cart item by ID
    public void deleteCartItem(Long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new RuntimeException("Cart item not found with ID: " + id);
        }
        cartItemRepository.deleteById(id);
    }

    // Add a new cart item
    public CartItem addCartItem(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // Optional: Check if item already exists
        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            return cartItemRepository.save(item);
        }

        // New item
        CartItem newItem = new CartItem(cart, product, quantity);
        return cartItemRepository.save(newItem);
    }
}
