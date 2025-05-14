package com.example.otp.service;

import com.example.otp.model.Buyer;
import com.example.otp.model.Product;
import com.example.otp.model.Wishlist;
import com.example.otp.repository.BuyerRepository;
import com.example.otp.repository.ProductRepository;
import com.example.otp.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addToWishlist(Long buyerId, Long productId) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Wishlist wishlist = buyer.getWishlist();
        if (wishlist == null) {
            wishlist = new Wishlist(buyer);
        }

        wishlist.addProduct(product);
        buyer.setWishlist(wishlist);
        wishlistRepository.save(wishlist);
        buyerRepository.save(buyer);
    }

    public void removeFromWishlist(Long buyerId, Long productId) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        Wishlist wishlist = buyer.getWishlist();
        if (wishlist == null) {
            throw new RuntimeException("Wishlist not found for buyer");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        wishlist.removeProduct(product);
        wishlistRepository.save(wishlist);
    }

    public Wishlist getWishlistByBuyerId(Long buyerId) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        return buyer.getWishlist();
    }
}
