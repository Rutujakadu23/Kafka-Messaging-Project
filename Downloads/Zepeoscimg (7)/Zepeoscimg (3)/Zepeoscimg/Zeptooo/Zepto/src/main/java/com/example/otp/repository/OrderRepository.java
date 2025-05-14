package com.example.otp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.otp.model.Order;
import com.example.otp.model.Supplier;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // ✅ Find all orders placed by a specific buyer
    List<Order> findByBuyerId(Long buyerId);

    // ✅ Find all orders assigned to a specific supplier
    List<Order> findBySupplierId(Long supplierId); // Make sure supplier is saved correctly

    // Optional: If you already have Supplier object
    List<Order> findBySupplier(Supplier supplier);

    

    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems WHERE o.supplier.id = :supplierId")
    List<Order> findOrdersWithItemsBySupplierId(@Param("supplierId") Long supplierId);

    @Query("SELECT o FROM Order o JOIN FETCH o.product JOIN FETCH o.buyer")
    List<Order> findAllWithBuyerAndProduct();

    
    
    
    
    
    // ✅ Find order by ID
    Optional<Order> findById(Long orderId);

    // ✅ Find orders by buyer and specific status (e.g., Processing, Delivered, Cancelled)
    List<Order> findByBuyerIdAndStatus(Long buyerId, String status);

    // ✅ Update order status directly in the database
    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
    void updateOrderStatus(@Param("orderId") Long orderId, @Param("status") String status);

    // ✅ Get all orders placed between two dates
    List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    
    
    
    // 🔍 (Optional) Find all orders by status for admin/supplier dashboards
    List<Order> findByStatus(String status);
}
