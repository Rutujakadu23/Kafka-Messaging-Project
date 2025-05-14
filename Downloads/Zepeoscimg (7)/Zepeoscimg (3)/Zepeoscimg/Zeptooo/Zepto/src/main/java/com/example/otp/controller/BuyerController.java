package com.example.otp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.otp.dto.BuyerDTO;
import com.example.otp.model.Buyer;
import com.example.otp.model.Order;
import com.example.otp.model.Product;
import com.example.otp.service.BuyerService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/buyers")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;


    @PostMapping("/register")
    public ResponseEntity<?> registerBuyer(@RequestBody Buyer buyer) {
        if (buyer.getPassword() == null || buyer.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required.");
        }

        Buyer savedBuyer = buyerService.registerBuyer(buyer);
        return new ResponseEntity<>(savedBuyer, HttpStatus.CREATED);
    }

    
    @GetMapping("/all")
    public List<BuyerDTO> getAllBuyers() {
        return buyerService.getAllBuyers();
    }


    @GetMapping("/product")
    public List<Product> getAllProducts() {
        return buyerService.getAllProducts();
    }

    @GetMapping("/products/search")
    public List<Product> searchProducts(@RequestParam String keyword) {
        return buyerService.searchProducts(keyword);
    }

    @PostMapping("/{buyerId}/cart")
    public void addToCart(@PathVariable Long buyerId, @RequestParam Long productId, @RequestParam int quantity) {
        buyerService.addToCart(buyerId, productId, quantity);
    }

    @PostMapping("/{buyerId}/wishlist")
    public void addToWishlist(@PathVariable Long buyerId, @RequestParam Long productId) {
        buyerService.addToWishlist(buyerId, productId);
    }
    
    @GetMapping("/{buyerId}/orders")
    public ResponseEntity<List<Order>> getOrdersByBuyer(@PathVariable Long buyerId) {
        List<Order> orders = buyerService.getOrdersByBuyer(buyerId);
        return ResponseEntity.ok(orders);
    }

    
    
    
    @PostMapping("/{buyerId}/order")
    public ResponseEntity<String> placeOrder(@PathVariable Long buyerId) {
        try {
            buyerService.placeOrder(buyerId);
            return ResponseEntity.ok("Order placed successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

}
