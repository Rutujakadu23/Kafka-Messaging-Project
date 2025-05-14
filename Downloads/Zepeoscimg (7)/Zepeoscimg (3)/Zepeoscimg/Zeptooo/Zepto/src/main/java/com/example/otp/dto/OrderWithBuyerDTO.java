package com.example.otp.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.otp.model.Order;
import com.example.otp.model.OrderItem;

public class OrderWithBuyerDTO {

    private Long orderId;
    private String orderStatus;
    private String buyerName;
    private String buyerPhone;
    private Long buyerId;
    private Long supplierId;
    private String supplierName;
    private ProductDTO product;
    private int quantity;
    private String orderDate;
    private String deliveryDate;
    private double totalPrice;
    private double discount;
    private List<OrderItemDTO> orderItems;

    public OrderWithBuyerDTO(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getStatus();

        // Buyer details
        this.buyerName = order.getBuyer() != null ? order.getBuyer().getName() : null;
        this.buyerPhone = order.getBuyer() != null ? order.getBuyer().getPhoneNumber() : null;
        this.buyerId = order.getBuyer() != null ? order.getBuyer().getId() : null;

        // Supplier details
        this.supplierId = order.getSupplier() != null ? order.getSupplier().getId() : null;
        this.supplierName = order.getSupplier() != null ? order.getSupplier().getSupplierName() : null;

        // Product details
        if (order.getProduct() != null) {
            this.product = new ProductDTO(order.getProduct());
        }

        this.quantity = order.getQuantity();
        this.orderDate = order.getOrderDate() != null ? order.getOrderDate().toString() : null;
        this.deliveryDate = order.getDeliveryDate() != null ? order.getDeliveryDate().toString() : null;
        this.totalPrice = order.getTotalPrice();
        this.discount = order.getDiscount();

        this.orderItems = new ArrayList<>();
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                this.orderItems.add(new OrderItemDTO(item));
            }
        }
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
