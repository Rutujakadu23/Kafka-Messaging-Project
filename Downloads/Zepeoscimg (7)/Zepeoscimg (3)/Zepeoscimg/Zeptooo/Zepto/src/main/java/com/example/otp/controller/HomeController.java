package com.example.otp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	

	 @GetMapping("/")
	    public String showLoginPage() {
	        return "login";  // Return the login view (login.html)
	    }
	 

	 @PostMapping("/login")
    public String loginSubmit(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              Model model) {
        // Simple check for email and password
        if (email.equals("admin@example.com") && password.equals("password123")) {
            // If login is successful, redirect to index page
            return "redirect:/index";
        } else {
            // If login fails, send error message to view
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
	
	 
	 
	  @PostMapping("/logout")
	    public String logout(HttpSession session) {
	        // Invalidate the session to log the user out
	        session.invalidate();
	        // Redirect to login page after logging out
	        return "redirect:/";
	    }
	
	
    @GetMapping("/index")
    public String index() {
        return "index";  // Do not include ".html"
    }
    
    @GetMapping("/user-page") // Change this mapping to avoid conflict
    public String userPage() {
        return "user";  // Return the view name (without .html)
    }
    
    
    @GetMapping("/product-page") // Change this mapping to avoid conflict
    public String productPage() {
        return "product";  // Return the view name (without .html)
    }
    
    @GetMapping("/category-page") // Define a mapping for the category page
    public String categoryPage() {
        return "category";  // Return the view name (without .html)
    }
    
    @GetMapping("/order-page") // Define a mapping for the category page
    public String orderPage() {
        return "order";  // Return the view name (without .html)
    }

    @GetMapping("/paymentintegration-page") // Define a mapping for the category page
    public String paymentintegrationPage() {
        return "paymentintegration";  // Return the view name (without .html)
    }
    
    @GetMapping("/offersVouchersCoupon-page") // Define a mapping for the category page
    public String offersVouchersCouponPage() {
        return "offersVouchersCoupon";  // Return the view name (without .html)
    }
    
    @GetMapping("/analytics-page") // Define a mapping for the category page
    public String analyticsPage() {
        return "analytics";  // Return the view name (without .html)
    }
    
    @GetMapping("/inventory-page") // Define a mapping for the category page
    public String inventoryPage() {
        return "inventory";  // Return the view name (without .html)
    }

    @GetMapping("/feedback-page") // Define a mapping for the category page
    public String feedbackPage() {
        return "feedback";  // Return the view name (without .html)
    }

    
    @GetMapping("/notifications-page") 
    public String notificationsPage() {
        return "notifications";  
    }
    
    @GetMapping("/buyer-page") // Define a mapping for the category page
    public String buyerPage() {
        return "buyer";  // Return the view name (without .html)
    }
    
    
    
    
    @GetMapping("/supplier-page") 
    public String supplierPage() {
        return "supplier";
    
}
    
    @GetMapping("/profile-page") 
    public String profilePage() {
        return "profile";  
    }
    
 //Supplier details
    
    @GetMapping("/supplier/dashboard")
    public String showDashboard() {
        return "supplierdashboard"; // This will load supplierdashboard.html from templates folder
    }
    
    @GetMapping("/supplier/login")
    public String showSupplierLoginPage() {
        return "supplierlogin";  // Will load supplierlogin.html from templates
    }
  
    @GetMapping("/supplier/register")
    public String showSupplierRegisterPage() {
        return "supplierregister";  // Will load supplierlogin.html from templates
    }
    
    @GetMapping("/supplier/products")
    public String showSupplierProductPage() {
        return "supplierproduct";  // This will load supplierproduct.html from templates
    }
    
    @GetMapping("/buyer/order")
    public String showBuyerOrderPage() {
        return "buyerorder"; // This will load buyerorder.html from the templates folder
    }

    
    
}