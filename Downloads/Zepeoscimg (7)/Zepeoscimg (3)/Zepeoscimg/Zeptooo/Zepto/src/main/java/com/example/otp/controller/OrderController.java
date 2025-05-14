package com.example.otp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.otp.dto.OrderWithBuyerDTO;  // Updated DTO import
import com.example.otp.service.OrderService;
import com.example.otp.util.InvoicePdfGenerator;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // ✅ Create orders from buyer's cart (1 product = 1 order)
    @PostMapping("/{buyerId}/create")
    public ResponseEntity<String> createOrderFromCart(@PathVariable Long buyerId) {
        orderService.createOrderFromCart(buyerId);
        return ResponseEntity.ok("Order(s) placed successfully!");
    }

    // ✅ Get all orders by buyer (returns OrderWithBuyerDTO)
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<OrderWithBuyerDTO>> getOrdersByBuyer(@PathVariable Long buyerId) {
        List<OrderWithBuyerDTO> orders = orderService.getOrdersByBuyer(buyerId);
        return ResponseEntity.ok(orders);
    }

    // ✅ Get specific order by ID (returns OrderWithBuyerDTO)
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderWithBuyerDTO> getOrderById(@PathVariable Long orderId) {
        OrderWithBuyerDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    // ✅ Update order status (e.g., pending → shipped)
    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok("Order status updated successfully!");
    }

    // ✅ Get all orders (returns OrderWithBuyerDTO)
    @GetMapping("/all")
    public ResponseEntity<List<OrderWithBuyerDTO>> getAllOrders() {
        List<OrderWithBuyerDTO> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // ✅ Cancel order (update status to "Canceled")
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order canceled successfully!");
    }

    // ✅ Return order (update status to "Returned")
    @PutMapping("/{orderId}/return")
    public ResponseEntity<String> returnOrder(@PathVariable Long orderId) {
        orderService.returnOrder(orderId);
        return ResponseEntity.ok("Order returned successfully!");
    }
 // ✅ Generate invoice for order
    @GetMapping("/invoice/{orderId}")
    public void generateInvoice(@PathVariable Long orderId, HttpServletResponse response) throws Exception {
        // Get OrderWithBuyerDTO from orderService by orderId
        OrderWithBuyerDTO order = orderService.getOrderById(orderId);

        // Set response content type to PDF
        response.setContentType("application/pdf");

        // Set content disposition to indicate that the file will be downloaded as an attachment
        response.setHeader("Content-Disposition", "attachment; filename=invoice.pdf");

        // Pass the OrderWithBuyerDTO to the InvoicePdfGenerator
        InvoicePdfGenerator.generateInvoice(response.getOutputStream(), order);
    }

    // ✅ Calculate expected delivery date for the order
    @GetMapping("/{orderId}/delivery-date")
    public ResponseEntity<LocalDate> calculateDeliveryDate(@PathVariable Long orderId) {
        LocalDate deliveryDate = orderService.calculateDeliveryDate(orderId);
        return ResponseEntity.ok(deliveryDate);
    }
}
