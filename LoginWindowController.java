import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import javafx.event.ActionEvent;

import javafx.scene.control.Alert.AlertType;

public class LoginWindowController extends WindowController {

    @FXML
    private Button loginButton;

    @FXML
    private Button profileButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;


    @FXML
    void performLogin(ActionEvent _event) {
        // Retrieve user input
        String inputUsername = usernameField.getText();
        String inputPassword = String.valueOf(passwordField.getText());

        String errorString = validateInput(inputUsername, inputPassword);

        if (errorString.isEmpty()) {
            // Input is Valid

            // Get Account
            Account acc = manager.getAccount(inputUsername);

            // Login Account
            manager.performLogin(acc);

            // Create Window
             if (acc instanceof Admin) {
                // Create Admin Window
                openWindow("FXML Files/AdminWindow.fxml", "Admin");
            } else {
                // Create User Window
                openWindow("FXML Files/UserWindow.fxml", "User");
            }

            // Close Current Window
            closeWindow(_event);

            // Clear details
            usernameField.setText("");
            passwordField.setText("");
        } else {
            // Input is Invalid
            createMessage("Login Error", errorString, AlertType.ERROR);
        }
    }

    @FXML
    void createProfile(ActionEvent _event) {
        // Open Profile Window
        openWindow("FXML Files/ProfileCreateWindow.fxml", "Create New Profile");
    }

    public void initialize() {
        // Read Files
        manager.readFile("accounts");
        manager.readFile("events");

        // Create Test Accounts
        manager.createAdmin();
        manager.createUser("mdr041", "password1", "Mitchell de Roo", 17, 4, 1999);
        manager.createUser("jg786", "abc123", "Joshua Grimshaw", 29, 10, 1996);
        manager.createUser("am123", "xyz890", "Alice Milton", 10, 8, 1997);

        // Write Test Accounts
        manager.writeFile("accounts");
    }

    private String validateInput(String username, String password) {
        // Check if all fields are populated
        if (username.isEmpty() || password.isEmpty()) {
            return "Please enter every field";
        } 

        // Check if the account exists
        Account acc = manager.getAccount(username);

        if (acc == null) {
            return "This account does not exist";
        }

        // Check if account is already logged in
        if (manager.getLoggedAccount() == acc) {
            return "This account is already logged in";
        }

        // Check if the correct password is given
        if (!acc.getPassword().equals(password)) {
            return "Invalid Password Given. Please try again.";
        }

        // Regular expression
        return "";
    }
}