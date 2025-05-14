package com.example.otp.dto;

import com.example.otp.model.Buyer;
import com.example.otp.model.Product;
import com.example.otp.model.Supplier;

import java.time.LocalDate;

public class OrderDetailDTO {

    private Long id;
    private Supplier supplier;
    private Buyer buyer;
    private Product product;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private Double totalAmount;
    private String status;

    // Default constructor
    public OrderDetailDTO() {
    }

    // Parameterized constructor
    public OrderDetailDTO(Long id, Supplier supplier, Buyer buyer, Product product, LocalDate orderDate,
                          LocalDate deliveryDate, Double totalAmount, String status) {
        this.id = id;
        this.supplier = supplier;
        this.buyer = buyer;
        this.product = product;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
