package com.example.otp.controller;

import com.example.otp.model.Wishlist;
import com.example.otp.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/{buyerId}/add")
    public ResponseEntity<String> addProduct(@PathVariable Long buyerId, @RequestParam Long productId) {
        wishlistService.addToWishlist(buyerId, productId);
        return ResponseEntity.ok("Product added to wishlist");
    }

    @DeleteMapping("/{buyerId}/remove")
    public ResponseEntity<String> removeProduct(@PathVariable Long buyerId, @RequestParam Long productId) {
        wishlistService.removeFromWishlist(buyerId, productId);
        return ResponseEntity.ok("Product removed from wishlist");
    }

    @GetMapping("/{buyerId}")
    public ResponseEntity<Wishlist> getWishlist(@PathVariable Long buyerId) {
        Wishlist wishlist = wishlistService.getWishlistByBuyerId(buyerId);
        return ResponseEntity.ok(wishlist);
    }
}
