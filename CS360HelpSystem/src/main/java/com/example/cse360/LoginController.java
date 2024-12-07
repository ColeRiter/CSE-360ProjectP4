package main.java.com.example.cse360;

package main.java.com.example.cse360;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private UserManager userManager = new UserManager();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        char[] password = passwordField.getText().toCharArray();

        User user = userManager.login(username, password);
        if (user != null) {
            errorLabel.setText("Login successful.");
            // Proceed to next view or functionality
        } else {
            errorLabel.setText("Invalid credentials.");
        }
    }

    @FXML
    private void goToRegister() {
        // Navigate to Register screen
    }
}