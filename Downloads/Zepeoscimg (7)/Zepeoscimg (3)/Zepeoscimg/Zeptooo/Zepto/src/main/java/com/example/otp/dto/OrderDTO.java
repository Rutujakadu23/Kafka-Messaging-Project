package com.example.otp.dto;

import java.time.LocalDate;

public class OrderDTO {

    private Long id;
    private String buyerName;
    private String productName;
    private int quantity;
    private double totalPrice;
    private String status;
    private LocalDate deliveryDate;

    // Constructor
    public OrderDTO(Long id, String buyerName, String productName, int quantity, double totalPrice, String status, LocalDate deliveryDate) {
        this.id = id;
        this.buyerName = buyerName;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
        this.deliveryDate = deliveryDate;
    }

    // Getters and Setters (Important if using Jackson to serialize)
    public Long getId() { return id; }
    public String getBuyerName() { return buyerName; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public LocalDate getDeliveryDate() { return deliveryDate; }
}
