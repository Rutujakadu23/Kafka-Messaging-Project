package com.example.otp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.otp.model.Buyer;
import com.example.otp.model.Cart;
import com.example.otp.model.CartItem;
import com.example.otp.model.Product;
import com.example.otp.repository.BuyerRepository;
import com.example.otp.repository.CartItemRepository;
import com.example.otp.repository.CartRepository;
import com.example.otp.repository.ProductRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    // ✅ Add a product to the buyer's cart
    public void addProductToCart(Long buyerId, Long productId, int quantity) {
        Assert.notNull(buyerId, "Buyer ID must not be null");
        Assert.notNull(productId, "Product ID must not be null");

        // 🔍 Fetch the buyer
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found with ID: " + buyerId));

        // 🔍 Fetch or create the buyer's cart
        List<Cart> carts = cartRepository.findByBuyerId(buyerId);
        Cart cart;

        if (carts.isEmpty()) {
            // Create new cart for the buyer
            cart = new Cart();
            cart.setBuyer(buyer); // ✅ Associate buyer with cart
            cart.setActive(true);
            cart = cartRepository.save(cart);
        } else {
            cart = carts.get(0); // Assuming one cart per buyer
        }

        // 🔍 Fetch the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found for ID: " + productId));

        // 🔍 Check if product already in cart
        CartItem existingCartItem = cartItemRepository.findByCartAndProduct(cart, product).orElse(null);

        if (existingCartItem != null) {
            // If product exists, increase the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartItemRepository.save(existingCartItem);
        } else {
            // If not, create a new cart item
            CartItem newItem = new CartItem(cart, product, quantity);
            cartItemRepository.save(newItem);
        }
    }

    // ✅ Remove a product from the buyer's cart
    public void removeProductFromCart(Long buyerId, Long productId) {
        Assert.notNull(buyerId, "Buyer ID must not be null");
        Assert.notNull(productId, "Product ID must not be null");

        // Fetch the buyer's cart
        List<Cart> carts = cartRepository.findByBuyerId(buyerId);
        if (carts.isEmpty()) {
            throw new RuntimeException("Cart not found for Buyer ID: " + buyerId);
        }

        Cart cart = carts.get(0); // Assuming one cart per buyer

        // Fetch product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found for ID: " + productId));

        // Find cart item
        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        // Delete cart item
        cartItemRepository.delete(cartItem);
    }
}
