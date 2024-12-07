package main.java.com.example.cse360;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class RegisterController {
    // Static list to store registered users in memory
    private static List<User> registeredUsers = new ArrayList<>();

    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField middleNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField preferredNameField;
    @FXML
    private Label errorLabel;

    @FXML
    public void handleRegistration() {
        String email = emailField.getText();
        String username = usernameField.getText();
        char[] password = passwordField.getText().toCharArray(); // Storing password as char array
        String firstName = firstNameField.getText();
        String middleName = middleNameField.getText();
        String lastName = lastNameField.getText();
        String preferredName = preferredNameField.getText();

        // Simple validation logic (add more validation as needed)
        if (email.isEmpty() || username.isEmpty() || password.length == 0) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        // Create new user
        User newUser = new User(email, username, password, firstName, middleName, lastName, preferredName);
        
        // Save user to the list
        registeredUsers.add(newUser);
        
        System.out.println("User registered successfully: " + username);
        errorLabel.setText("User registered successfully.");

        // Clear fields after registration
        emailField.clear();
        usernameField.clear();
        passwordField.clear();
        firstNameField.clear();
        middleNameField.clear();
        lastNameField.clear();
        preferredNameField.clear();
    }

    @FXML
    public void goToLogin() {
        try {
            Parent loginPage = FXMLLoader.load(getClass().getResource("/main/resources/login.fxml"));
            Scene loginScene = new Scene(loginPage);
            Stage currentStage = (Stage) emailField.getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to return the list of registered users
    public static List<User> getRegisteredUsers() {
        return registeredUsers;
    }
}