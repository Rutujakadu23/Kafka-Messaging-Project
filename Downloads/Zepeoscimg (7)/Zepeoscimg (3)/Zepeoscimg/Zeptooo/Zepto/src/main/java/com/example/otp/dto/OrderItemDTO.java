package com.example.otp.dto;

import com.example.otp.model.OrderItem;

public class OrderItemDTO {
    private Long id;
    private ProductDTO product; // Assuming you have a ProductDTO to map product details
    private int quantity;
    private double price;

    public OrderItemDTO(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.product = new ProductDTO(orderItem.getProduct()); // Assuming ProductDTO is used for product details
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
