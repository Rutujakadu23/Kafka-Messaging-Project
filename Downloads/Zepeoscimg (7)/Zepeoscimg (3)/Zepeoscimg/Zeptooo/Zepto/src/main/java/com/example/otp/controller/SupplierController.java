package com.example.otp.controller;

import com.example.otp.model.Order;
import com.example.otp.model.Product;
import com.example.otp.model.Supplier;
import com.example.otp.repository.OrderRepository;
import com.example.otp.repository.ProductRepository;
import com.example.otp.repository.SupplierRepository;
import com.example.otp.service.SupplierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("/supplier")
@CrossOrigin("*")
public class SupplierController {

    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final SupplierService supplierService;
    private final OrderRepository orderRepository;

    private final String uploadDir = "C:/Users/Admin/eclipse-workspace/Zeptooo/Zepto/src/main/resources/static/uploads/";

    @Autowired
    public SupplierController(SupplierRepository supplierRepository,
                              ProductRepository productRepository,
                              SupplierService supplierService,
                              OrderRepository orderRepository) {
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
        this.supplierService = supplierService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/all-with-products")
    public ResponseEntity<List<Supplier>> getAllSuppliersWithProducts() {
        List<Supplier> suppliers = supplierService.getAllSuppliersWithProducts();
        return ResponseEntity.ok(suppliers);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerSupplier(@RequestBody Supplier supplier) {
        if (supplier.getPassword() == null || supplier.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required.");
        }

        Supplier savedSupplier = supplierService.registerSupplier(supplier);
        return new ResponseEntity<>(savedSupplier, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getSupplierProducts(@PathVariable Long id) {
        List<Product> products = supplierService.getSupplierProducts(id);
        return ResponseEntity.ok(products);
    }

    @PutMapping(value = "/{supplierId}/product/{productId}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProduct(
            @PathVariable Long supplierId,
            @PathVariable Long productId,
            @RequestParam("product") String productData,
            @RequestParam(value = "image", required = false) MultipartFile file) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Product updatedProductData = objectMapper.readValue(productData, Product.class);

            String imagePath = null;

            if (file != null && !file.isEmpty()) {
                String fileName = Path.of(file.getOriginalFilename()).getFileName().toString();
                Path targetPath = Paths.get(uploadDir, fileName);
                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
                imagePath = "/uploads/" + fileName;
            }

            Product updatedProduct = supplierService.updateProduct(supplierId, productId, updatedProductData, imagePath);
            return ResponseEntity.ok(updatedProduct);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while updating product: " + e.getMessage());
        }
    }
    
    
    
    @GetMapping("/{supplierId}/orders")
    public ResponseEntity<?> getOrdersBySupplier(@PathVariable Long supplierId) {
        List<Order> orders = supplierService.getOrdersBySupplierId(supplierId);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found for this supplier.");
        }
        return ResponseEntity.ok(orders);
    }

    @PostMapping(value = "/{supplierId}/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(
            @PathVariable Long supplierId,
            @RequestParam("product") String productData,
            @RequestParam("image") MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(productData, Product.class);

            Supplier supplier = supplierRepository.findById(supplierId)
                    .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + supplierId));

            product.setSupplier(supplier);

            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty.");
            }

            String fileName = Path.of(file.getOriginalFilename()).getFileName().toString();
            Path targetPath = Paths.get(uploadDir, fileName);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }

            product.setImagePath("/uploads/" + fileName);
            Product savedProduct = productRepository.save(product);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while saving product or uploading image: " + e.getMessage());
        }
    }

    @DeleteMapping("/{supplierId}/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long supplierId, @PathVariable Long productId) {
        try {
            supplierService.deleteProduct(supplierId, productId);
            return ResponseEntity.ok("Product deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{supplierId}/order/{orderId}/fulfill")
    public ResponseEntity<?> fulfillOrder(@PathVariable Long supplierId, @PathVariable Long orderId) {
        try {
            supplierService.fulfillOrder(supplierId, orderId);
            return ResponseEntity.ok("Order fulfilled successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
