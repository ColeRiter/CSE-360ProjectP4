package main.java.com.example.cse360;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserManager {
    private Map<String, User> users = new HashMap<>();

    // Register a new user
    public void registerUser(User user) {
        users.put(user.getUsername(), user);
    }

    // Login method with Optional return type for better handling
    public Optional<User> login(String username, char[] password) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            // Clear the password array after usage for security
            Arrays.fill(password, ' ');
            return Optional.of(user);
        }
        // Clear the password array if login fails
        Arrays.fill(password, ' ');
        return Optional.empty();
    }

    // Get a list of all registered users
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    // Reset the user's password
    public void resetPassword(String username, char[] newPassword) {
        User user = users.get(username);
        if (user != null) {
            user.setPassword(newPassword);
            // Clear the new password array after setting
            Arrays.fill(newPassword, ' ');
        } else {
            System.out.println("User not found: " + username);
        }
    }

    // Clear password array from memory for security after use
    private void clearPassword(char[] password) {
        Arrays.fill(password, ' ');
    }
}