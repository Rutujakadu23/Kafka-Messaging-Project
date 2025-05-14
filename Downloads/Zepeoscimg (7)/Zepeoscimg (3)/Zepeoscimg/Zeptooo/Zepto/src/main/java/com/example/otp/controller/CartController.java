package com.example.otp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.otp.model.CartItem;
import com.example.otp.service.CartItemService;
import com.example.otp.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    // ✅ Add product to cart by buyer ID
    @PostMapping("/add/{buyerId}")
    public ResponseEntity<String> addProductToCart(
            @PathVariable Long buyerId,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        cartService.addProductToCart(buyerId, productId, quantity);
        return ResponseEntity.ok("Product added to cart successfully!");
    }

    // ✅ Remove product from cart by buyer ID
    @DeleteMapping("/remove/{buyerId}")
    public ResponseEntity<String> removeProductFromCart(
            @PathVariable Long buyerId,
            @RequestParam Long productId) {
        cartService.removeProductFromCart(buyerId, productId);
        return ResponseEntity.ok("Product removed from cart successfully!");
    }

    // ✅ Get all cart items for a specific cart
    @GetMapping("/items/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long cartId) {
        List<CartItem> cartItems = cartItemService.getCartItemsByCartId(cartId);
        return ResponseEntity.ok(cartItems);
    }

    // ✅ Update quantity of a specific cart item
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestParam int quantity) {
        CartItem updatedItem = cartItemService.updateCartItemQuantity(cartItemId, quantity);
        return ResponseEntity.ok(updatedItem);
    }

    // ✅ Delete a cart item by its ID
    @DeleteMapping("/item/delete/{cartItemId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
        return ResponseEntity.ok("Cart item deleted successfully!");
    }

    // ✅ Add cart item directly via cart ID and product ID (optional utility)
    @PostMapping("/item/add")
    public ResponseEntity<CartItem> addCartItem(
            @RequestParam Long cartId,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        CartItem cartItem = cartItemService.addCartItem(cartId, productId, quantity);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }
}
