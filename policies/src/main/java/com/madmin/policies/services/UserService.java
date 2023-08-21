package com.madmin.policies.services;

import com.madmin.policies.object.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public abstract class UserService {

    // A temporary in-memory map to store user data
    private Map<String, User> users;

    public UserService() {
        // In a real application, user data will be retrieved from a database or other data source
        users = new HashMap<>();

        // Sample user data (for demonstration purposes)
        User adminUser = new User("admin", "admin123", "Administrator");
        User regularUser = new User("user", "user123", "User");

        // Passwords should be hashed in a real application, but for simplicity, we are storing them as plain text here
        users.put(adminUser.getUsername(), adminUser);
        users.put(regularUser.getUsername(), regularUser);
    }

    public User authenticateUser(String username, String password) {
        // In a real application, you will validate the user's credentials against the database or any other source
        // For demonstration purposes, we are using a simple in-memory map
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null; // User not found or invalid credentials
    }

    public abstract UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

