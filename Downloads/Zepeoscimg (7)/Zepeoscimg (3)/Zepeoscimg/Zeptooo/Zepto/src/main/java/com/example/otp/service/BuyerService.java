package com.example.otp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.otp.dto.BuyerDTO;
import com.example.otp.model.Buyer;
import com.example.otp.model.Cart;
import com.example.otp.model.CartItem;
import com.example.otp.model.Order;
import com.example.otp.model.OrderItem;
import com.example.otp.model.Product;
import com.example.otp.model.Wishlist;
import com.example.otp.repository.BuyerRepository;
import com.example.otp.repository.CartRepository;
import com.example.otp.repository.OrderRepository;
import com.example.otp.repository.ProductRepository;
import com.example.otp.repository.WishlistRepository;

import jakarta.transaction.Transactional;

@Service
public class BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Login or Create Buyer
    public Buyer loginOrCreate(String phoneNumber, String name, String address) {
        Optional<Buyer> buyerOptional = buyerRepository.findByPhoneNumber(phoneNumber);

        Buyer buyer = buyerOptional.orElseGet(() -> {
            Buyer newBuyer = new Buyer(name, phoneNumber, address);
            Cart cart = new Cart(newBuyer); // ✅ link cart to buyer
            newBuyer.setCart(cart);
            cartRepository.save(cart); // ✅ persist cart separately
            return buyerRepository.save(newBuyer);
        });

        // If cart is null (older records), create and save
        if (buyer.getCart() == null) {
            Cart cart = new Cart(buyer);
            cartRepository.save(cart);
            buyer.setCart(cart);
            buyerRepository.save(buyer);
        }

        return buyer;
    }
    
    

    public Buyer registerBuyer(Buyer buyer) {
        if (buyer.getPassword() == null || buyer.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        return buyerRepository.save(buyer);
    }
    
    
    public List<BuyerDTO> getAllBuyers() {
        List<Buyer> buyers = buyerRepository.findAll();
        return buyers.stream()
                .map(buyer -> new BuyerDTO(buyer.getId(), buyer.getName(), buyer.getPhoneNumber(), buyer.getAddress()))
                .collect(Collectors.toList());
    }
    
    
    public List<Order> getOrdersByBuyer(Long buyerId) {
        return orderRepository.findByBuyerId(buyerId);
    }

    
    
    

    // Method to view all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Method to search products by name
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }

    // Add product to cart using buyerId and productId
    public void addToCart(Long buyerId, Long productId, int quantity) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        Cart cart = cartRepository.findByBuyer(buyer)
                .orElseGet(() -> {
                    Cart newCart = new Cart(buyer);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Create and set bidirectional relationship
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart); // ✅ VERY IMPORTANT

        cart.getCartItems().add(cartItem);

        cartRepository.save(cart); // ✅ will also save CartItems if CascadeType.ALL is set
    }

    
    

    public Order placeOrder(Long buyerId) {
        // Get buyer
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        // Get cart
        List<Cart> cartList = cartRepository.findByBuyerId(buyerId);

        if (cartList.isEmpty() || cartList.get(0).getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty or not found for buyer");
        }

        Cart cart = cartList.get(0); // Get the first cart


        // Create Order
        Order order = new Order();
        order.setBuyer(buyer);
        order.setOrderDate(LocalDate.now());
        order.setDeliveryDate(LocalDate.now().plusDays(3)); // example
        order.setStatus("Pending");

        List<CartItem> cartItems = cart.getCartItems();

        // Set first item’s product, supplier, quantity in Order (as per your requirement)
        CartItem firstItem = cartItems.get(0);
        order.setProduct(firstItem.getProduct());
        order.setSupplier(firstItem.getProduct().getSupplier());
        order.setQuantity(firstItem.getQuantity());

        // Set total price
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        order.setTotalPrice(total);

        // Save order
        return orderRepository.save(order);
    }


    // Add product to wishlist
    @Transactional
    public void addToWishlist(Long buyerId, Long productId) {
        Optional<Buyer> buyer = buyerRepository.findById(buyerId);
        Optional<Product> product = productRepository.findById(productId);

        if (buyer.isPresent() && product.isPresent()) {
            Wishlist wishlist = wishlistRepository.findByBuyer(buyer.get())
                    .orElseGet(() -> new Wishlist(buyer.get()));

            wishlist.getProducts().add(product.get());
            wishlistRepository.save(wishlist);
        } else {
            throw new IllegalArgumentException("Buyer or Product not found");
        }
    }
}
