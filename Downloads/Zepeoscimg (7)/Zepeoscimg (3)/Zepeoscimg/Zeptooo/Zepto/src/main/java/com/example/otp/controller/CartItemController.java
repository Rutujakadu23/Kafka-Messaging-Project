package com.example.otp.controller;

import com.example.otp.model.CartItem;
import com.example.otp.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    // ✅ Get all cart items for a specific cart
    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItemsByCartId(@PathVariable Long cartId) {
        List<CartItem> items = cartItemService.getCartItemsByCartId(cartId);
        return ResponseEntity.ok(items);
    }

    // ✅ Add a new cart item using cartId and productId
    @PostMapping("/add")
    public ResponseEntity<CartItem> addCartItem(
            @RequestParam Long cartId,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        CartItem newItem = cartItemService.addCartItem(cartId, productId, quantity);
        return ResponseEntity.ok(newItem);
    }

    // ✅ Update the quantity of a cart item
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestParam int quantity) {
        CartItem updatedItem = cartItemService.updateCartItemQuantity(cartItemId, quantity);
        return ResponseEntity.ok(updatedItem);
    }

    // ✅ Delete a specific cart item by ID
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
        return ResponseEntity.ok("Cart item deleted successfully.");
    }
}
