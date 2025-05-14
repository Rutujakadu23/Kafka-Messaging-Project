package com.example.otp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.otp.repository.BuyerRepository;
import com.example.otp.repository.CartRepository;
import com.example.otp.repository.OrderRepository;
import com.example.otp.repository.ProductRepository;
import com.example.otp.dto.OrderWithBuyerDTO;
import com.example.otp.model.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CouponService couponService;

    // Create an order from cart
    public void createOrderFromCart(Long buyerId) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        List<Cart> carts = cartRepository.findByBuyerId(buyerId);

        if (carts.isEmpty()) {
            throw new RuntimeException("Cart not found for the buyer");
        }

        Cart cart = carts.get(0);

        Order order = new Order();
        order.setBuyer(buyer);
        order.setOrderDate(LocalDate.now());
        order.setDeliveryDate(LocalDate.now().plusDays(7));
        order.setStatus("Processing");

        double totalPrice = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cart.getCartItems()) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            double price = product.getPrice();

            totalPrice += price * quantity;

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(price);

            orderItems.add(orderItem);
        }

        // Apply best coupon (based on fixed discount)
        double discount = 0.0;
        Coupon bestCoupon = couponService.getBestApplicableCoupon(totalPrice);
        if (bestCoupon != null) {
            discount = bestCoupon.getDiscountAmount();
            System.out.println("Applied Coupon: " + bestCoupon.getCode() + " | Discount: " + discount);
        }

        order.setTotalPrice(totalPrice - discount);
        order.setDiscount(discount);
        order.setOrderItems(orderItems);

        orderRepository.save(order);

        // Clear cart after order is placed
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    // Method to get orders by buyer ID (returns OrderWithBuyerDTO)
    public List<OrderWithBuyerDTO> getOrdersByBuyer(Long buyerId) {
        List<Order> orders = orderRepository.findByBuyerId(buyerId);
        return orders.stream()
                .map(OrderWithBuyerDTO::new) // Convert each Order to OrderWithBuyerDTO
                .collect(Collectors.toList());
    }

    // Get all orders (returns OrderWithBuyerDTO)
    public List<OrderWithBuyerDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderWithBuyerDTO::new) // Convert each Order to OrderWithBuyerDTO
                .collect(Collectors.toList());
    }

    // Method to get an order by its ID (returns OrderWithBuyerDTO)
    public OrderWithBuyerDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return new OrderWithBuyerDTO(order); // Return as OrderWithBuyerDTO
    }

    // Update the order status
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }

    // Method to cancel an order
    public void cancelOrder(Long orderId) {
        updateOrderStatus(orderId, "Cancelled");
    }

    // Method to return an order
    public void returnOrder(Long orderId) {
        updateOrderStatus(orderId, "Returned");
    }

    // Calculate delivery date
    public LocalDate calculateDeliveryDate(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return order.getOrderDate().plusDays(7);
    }
}
