import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Rectangle;
import java.util.Optional;

import javafx.fxml.LoadException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class LoginWindowController {

    public EventManager manager;

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
    private Button quitButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button profileButton;

    @FXML
    void performLogin(ActionEvent event) {
        // Set up Error Handler
        Alert alert;

        // Get Data
         String inputUsername = usernameField.getText();
         String inputPassword = String.valueOf(passwordField.getText());

         // Check Fields are filled
         if (inputUsername.equals("") || inputPassword.equals("")) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please Fill Username and Password Fields");

            alert.showAndWait();
         } else {
            // Check if account exists
            if (manager.getAccount(inputUsername) == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(null);
                alert.setContentText("This Account does not exist");

                alert.showAndWait();
            } else {
               if (!manager.getAccount(inputUsername).getPassword().equals(inputPassword)) {
                  alert = new Alert(AlertType.ERROR);
                  alert.setTitle("Login Error");
                  alert.setHeaderText(null);
                  alert.setContentText("Invalid Password Given. Please try Again");

                  alert.showAndWait();
               } else {
                  // Perform Login
                  // manager.getAccount(inputUsername).login();
                    createWindow("User");

                  // System.out.println(manager.getAccount(inputUsername).toString());

                  // Clear details
                  usernameField.setText("");
                  passwordField.setText("");
               }
            }  
         }
    }
    public void createWindow(String type) {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource(type + "Window.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle(type);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    @FXML
    void exitProgram(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit Program");
        alert.setHeaderText(null);
        alert.setContentText("Exit the Program?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    @FXML
    void createProfile(ActionEvent event) {

    }
    public void initialize() {
        manager.createUser("mdr041", "password1", "Mitchell de Roo", 17, 04, 1999);
    }
}