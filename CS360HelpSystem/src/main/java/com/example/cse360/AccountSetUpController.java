package main.java.com.example.cse360;

import java.util.regex.Pattern;

public class AccountSetUpController {
    private static final String PASSWORD_COMPLEXITY_REGEX = "^(?=.[a-z])(?=.[A-Z])(?=.\d)(?=.[@$!%?&])[A-Za-z\d@$!%?&]{8,}$";

    // Validates password complexity with enhanced error messages
    public String validatePassword(String password) {
        if (!Pattern.matches(PASSWORD_COMPLEXITY_REGEX, password)) {
            if (password.length() < 8) {
                return "Error: Password must be at least 8 characters long.";
            }
            if (!password.matches(".[A-Z].")) {
                return "Error: Password must include at least one uppercase letter.";
            }
            if (!password.matches(".[a-z].")) {
                return "Error: Password must include at least one lowercase letter.";
            }
            if (!password.matches(".\d.")) {
                return "Error: Password must include at least one number.";
            }
            if (!password.matches(".[@$!%?&].")) {
                return "Error: Password must include at least one special character (@$!%?&).";
            }
        }
        return "Password is valid.";
    }

    // Handles user registration
    public String registerUser(String email, String username, String password) {
        String passwordValidation = validatePassword(password);
        if (!"Password is valid.".equals(passwordValidation)) {
            return passwordValidation; // Return specific error message
        }

        // Registration logic here (omitted for brevity)
        return "User registered successfully!";
    }
}