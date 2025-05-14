package com.example.otp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.otp.model.Order;
import com.example.otp.model.Product;
import com.example.otp.model.Supplier;
import com.example.otp.repository.OrderRepository;
import com.example.otp.repository.ProductRepository;
import com.example.otp.repository.SupplierRepository;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Supplier registerSupplier(Supplier supplier) {
        if (supplier.getPassword() == null || supplier.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        return supplierRepository.save(supplier);
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id).orElse(null);
    }

    public List<Product> getSupplierProducts(Long supplierId) {
        Supplier supplier = getSupplierById(supplierId);
        return supplier != null ? supplier.getProducts() : null;
    }

    public List<Supplier> getAllSuppliersWithProducts() {
        return supplierRepository.findAll();
    }

    // ✅ Method 1 - used internally
    public List<Order> getSupplierOrders(Long supplierId) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierId);
        if (optionalSupplier.isEmpty()) {
            System.out.println("❌ Supplier not found with ID: " + supplierId);
            return new ArrayList<>();
        }

        Supplier supplier = optionalSupplier.get();
        List<Order> orders = orderRepository.findBySupplier(supplier);
        
        System.out.println("✅ Orders fetched for supplier ID " + supplierId + ": " + orders.size());
        return orders;
    }
    
    
    
    
    public Product updateProduct(Long supplierId, Long productId, Product updatedProductData, String imagePath) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + supplierId));

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // Ensure the product belongs to the supplier
        if (!existingProduct.getSupplier().getId().equals(supplierId)) {
            throw new RuntimeException("Product does not belong to this supplier.");
        }

        // Update fields
        existingProduct.setName(updatedProductData.getName());
        existingProduct.setDescription(updatedProductData.getDescription());
        existingProduct.setPrice(updatedProductData.getPrice());
        existingProduct.setStockQuantity(updatedProductData.getStockQuantity());

        // Update image path if provided
        if (imagePath != null && !imagePath.isEmpty()) {
            existingProduct.setImagePath(imagePath);
        }

        return productRepository.save(existingProduct);
    }

    
    

    // ✅ Method 2 - same as above but more generic signature, matching earlier interface
    @Transactional
    public List<Order> getOrdersBySupplierId(Long supplierId) {
        List<Order> orders = orderRepository.findBySupplierId(supplierId);
        
        // Optionally force initialization
        for (Order order : orders) {
            order.getOrderItems().size(); // forces loading of the lazy collection
        }

        return orders;
    }



    public void deleteProduct(Long supplierId, Long productId) {
        Supplier supplier = getSupplierById(supplierId);
        if (supplier != null) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null && product.getSupplier().getId().equals(supplierId)) {
                productRepository.delete(product);
            }
        }
    }

    public void fulfillOrder(Long supplierId, Long orderId) {
        Supplier supplier = getSupplierById(supplierId);
        if (supplier != null) {
            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                if (order.getSupplier().getId().equals(supplierId)) {
                    order.setStatus("Fulfilled");
                    orderRepository.save(order);
                }
            }
        }
    }
}
