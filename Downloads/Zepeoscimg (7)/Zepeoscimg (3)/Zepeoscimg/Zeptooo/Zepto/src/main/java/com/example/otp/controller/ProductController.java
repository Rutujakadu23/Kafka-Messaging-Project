package com.example.otp.controller;
import com.example.otp.model.Product;
import com.example.otp.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;



@Controller 
@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    private final String uploadDir = "C:/uploads/"; // Use external path

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    
    
    
    

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Add new product with image
    @PostMapping
    public ResponseEntity<Product> addProduct(
        @RequestParam("product") String productData,
        @RequestParam("image") MultipartFile file) {
        try {
            // Convert the 'productData' string into a Product object
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(productData, Product.class);

            // Save the image
            String fileName = file.getOriginalFilename();
            try (InputStream inputStream = file.getInputStream()) {
                Path targetPath = Paths.get("C:/Users/Admin/eclipse-workspace/Zeptooo/Zepto/src/main/resources/static/uploads/", fileName);
                Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
               
                product.setImagePath("/uploads/" + fileName); // Set image path in the product
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image", e);
            }

            // Save the product in the database
            Product savedProduct = productService.saveProduct(product, file);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing product with an optional image
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") String productData,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        ObjectMapper objectMapper = new ObjectMapper();
        Product product;
        try {
            product = objectMapper.readValue(productData, Product.class);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Process image if provided
        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(image);
            if (imagePath != null) {
                product.setImagePath(imagePath);
            }
        }

        Product updatedProduct = productService.updateProduct(id, product, image);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Search products by name
    @GetMapping("/search/name")
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String name) {
        List<Product> products = productService.searchProductsByName(name);
        return products.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                                  : ResponseEntity.ok(products);
    }

    // Search products by description
    @GetMapping("/search/description")
    public ResponseEntity<List<Product>> searchProductsByDescription(@RequestParam String description) {
        List<Product> products = productService.searchProductsByDescription(description);
        return products.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                                  : ResponseEntity.ok(products);
    }

    // Get out-of-stock products
    @GetMapping("/out-of-stock")
    public ResponseEntity<List<Product>> getOutOfStockProducts() {
        List<Product> products = productService.getOutOfStockProducts();
        return products.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                                  : ResponseEntity.ok(products);
    }

    // Utility method to save image
    private String saveImage(MultipartFile image) {
        if (image.isEmpty()) {
            return null;
        }

        try {
        	
        	// Directory path for saving the images
            Path uploadPath = Paths.get("C:/Users/Admin/Downloads/Zeptooo/Zepto/src/main/resources/static/uploads/");
          
            
            // Create the directory if it doesn't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        	
            
        	
        	 // Get the image's original file name and create a path to save it
            String fileName = image.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);

            // Save the image to the upload directory
            image.transferTo(path);

            // Return the relative path for storing in the database
            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }
}
