package main.java.com.example.cse360;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

public class User {
    private String email;
    private String username;
    private char[] password; // Storing password as char array for security
    private String firstName;
    private String middleName;
    private String lastName;
    private String preferredName;

    // Constructor matching your RegisterController
    public User(String email, String username, char[] password, String firstName, String middleName, String lastName, String preferredName) {
        this.email = email;
        this.username = username;
        this.password = hashPassword(password); // Store the hashed password
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.preferredName = preferredName;
    }

    // Hashing the password using SHA-256
    private String hashPassword(char[] password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(new String(password).getBytes());
            return Base64.getEncoder().encodeToString(hash); // Store the password hash
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to check if the provided password matches the stored password hash
    public boolean checkPassword(char[] inputPassword) {
        String hashedInput = hashPassword(inputPassword);
        return hashedInput.equals(password); // Compare the hashed password
    }

    // Method to set a new password (after hashing)
    public void setPassword(char[] newPassword) {
        this.password = hashPassword(newPassword); // Hash the new password
    }

    // Method to clear the password array from memory for security
    public void clearPassword() {
        Arrays.fill(password, ' '); // Fill the password array with spaces to erase its contents
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password; // Return the password hash, not the actual password
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", preferredName='" + preferredName + '\'' +
                '}';
    }

    // Override equals and hashCode methods to allow comparison of User objects
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}