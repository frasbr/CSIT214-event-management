import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginWindowController extends WindowController {
	EventManager manager;

    @FXML
    private TextField usernameField;

    @FXML
    private Rectangle rectBackground;

    @FXML
    private Pane loginPane;

    @FXML
    private ImageView imageLogo;

    @FXML
    private Label titleLabel;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button profileButton;

    @FXML
    void performLogin(ActionEvent event) {
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
    		closeWindow(event);

    		// Clear details
    		usernameField.setText("");
    		passwordField.setText("");
    	} else {
    		// Input is Invalid
    		createMessage("Login Error", errorString, AlertType.ERROR);
    	}
    }

    @FXML
    void createProfile(ActionEvent event) {
    	// Open Profile Window
    	openWindow("FXML Files/ProfileWindow.fxml", "Create New Profile");
    }

    public void initialize() {
        // Read Files
        manager.readFile("accounts");
        manager.readFile("events");

        // Create Test Accounts
        manager.createAdmin();
        manager.createUser("mdr041", "password1", "Mitchell de Roo", 17, 4, 1999);

        // Write Test Accounts
        manager.writeFile("accounts");

        // Create Test Events
        manager.createEvent("Mitchell's 21st Birthday Party", "University of Wollongong Unibar", (User) manager.getAccount("mdr041"));
        manager.createEvent("Computer Science Workshop", "Building 3-G123", (User) manager.getAccount("mdr041"));
    
        // Write Test Events
        manager.writeFile("events");
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