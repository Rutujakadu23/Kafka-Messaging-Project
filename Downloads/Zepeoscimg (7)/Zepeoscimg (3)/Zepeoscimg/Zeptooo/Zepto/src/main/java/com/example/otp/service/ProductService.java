package com.example.otp.service;

import com.example.otp.model.Product;
import com.example.otp.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
	
	

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Add a new product with image (renamed from addProduct to saveProduct)
    public Product saveProduct(Product product, MultipartFile image) {
        // Save the image and set the image path in the product
        String imagePath = saveImage(image);
        product.setImagePath(imagePath);

        // Save the product using the repository and return it
        return productRepository.save(product);
    }

    // Update a product
    public Product updateProduct(Long id, Product productDetails, MultipartFile image) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setStockQuantity(productDetails.getStockQuantity());

            if (image != null && !image.isEmpty()) {
                String imagePath = saveImage(image);  // Update the image if provided
                product.setImagePath(imagePath);
            }

            return productRepository.save(product);
        }).orElse(null);
    }
    
    
    public void saveImage(MultipartFile file, Path path) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    
    // Delete a product
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Search products by name
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    // Search products by description
    public List<Product> searchProductsByDescription(String description) {
        return productRepository.findByDescriptionContaining(description);
    }

    // Get out-of-stock products
    public List<Product> getOutOfStockProducts() {
        return productRepository.findByStockQuantity(0);
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

            // Save the image
            String fileName = image.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Return the full path of the saved image
            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }
}

