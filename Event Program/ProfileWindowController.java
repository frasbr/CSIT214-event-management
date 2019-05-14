import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;

import javafx.fxml.LoadException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProfileWindowController {

    EventManager manager;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField dateofbirthField;

    @FXML
    private Rectangle rectBackground;

    @FXML
    private Pane loginPane;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField fullnameField;

    @FXML
    private Label titleLabel;

    @FXML
    private Button createButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    void exitWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void createProfile(ActionEvent event) {
        // Set up Error Handler
        Alert alert;

        // Get Data
        String inputUsername = usernameField.getText();
        String inputPassword = String.valueOf(passwordField.getText());
        String inputFullname = fullnameField.getText();
        String inputDateOfBirth = dateofbirthField.getText();

        if (inputUsername.equals("") || inputPassword.equals("") || inputFullname.equals("") || inputDateOfBirth.equals("")) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All Relevant Fields");
        
            alert.showAndWait();
        } else {
             if (manager.getAccount(inputUsername) != null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(null);
                alert.setContentText("This Account already exists");
                
                alert.showAndWait();
            } else {
                // Check Date
                int count = inputDateOfBirth.length() - inputDateOfBirth.replace("/", "").length();
                if (count != 2) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Date given is incorrectly formatted. Please do the following: dd/mm/yyyy");
                    
                    alert.showAndWait();
                } else {
                    // Get Date
                    String[] dateComponents = inputDateOfBirth.split("/");
                    int day = Integer.parseInt(dateComponents[0]);
                    int month = Integer.parseInt(dateComponents[1]);
                    int year = Integer.parseInt(dateComponents[2]);

                    // Create Account
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Account Creation Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Account was successfully created");

                    alert.showAndWait();

                    manager.createUser(inputUsername, inputPassword, inputFullname, day, month, year);
                    
                    // Clear details
                    usernameField.setText("");
                    passwordField.setText("");
                    fullnameField.setText("");
                    dateofbirthField.setText("");
                }
            }
        }
    }
}