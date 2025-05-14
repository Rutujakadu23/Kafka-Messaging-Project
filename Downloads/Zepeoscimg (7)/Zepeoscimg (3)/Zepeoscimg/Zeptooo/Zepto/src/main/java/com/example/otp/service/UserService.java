package com.example.otp.service;

import com.example.otp.model.User;
import com.example.otp.model.LoginRequest; // Ensure you have this class defined
import com.example.otp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new User
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Get a User by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Get all Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update a User
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPhoneNumber(updatedUser.getPhoneNumber()); // Update phone number
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    // Delete a User
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Authentication method
    public boolean authenticateUser(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Check if the password matches (You should hash passwords in production)
            return loginRequest.getPassword().equals(user.getPassword());
        }
        return false;
    }
}
