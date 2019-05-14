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
import javafx.scene.Node;

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
    
        // Check fields are filled
        if (inputUsername.equals("") || inputPassword.equals("")) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please Fill Username and Password Fields");

            alert.showAndWait();
        } else {
            // Get Account
            Account acc = manager.getAccount(inputUsername);

            // Check if account exists
            if (acc == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(null);
                alert.setContentText("This Account does not exist");

                alert.showAndWait();
            } else {
                // Check if account is already logged in
                if (manager.getLoggedAccount() == acc) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Login Error");
                    alert.setHeaderText(null);
                    alert.setContentText("This account is already logged in");

                    alert.showAndWait();
                } else {
                    // Check Password
                    if (!acc.getPassword().equals(inputPassword)) {
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Login Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid Password Given. Please try Again");

                        alert.showAndWait();
                    } else {
                        // Perform Login
                          manager.performLogin(acc);

                          // Create User window
                          createWindow("User");

                          // Close Stage
                          Node source = (Node) event.getSource();
                          Stage stage = (Stage) source.getScene().getWindow();
                          stage.close();

                          // Clear details
                          usernameField.setText("");
                          passwordField.setText("");
                    }
                }
            }
        }       
    }
    public void createWindow(String type) {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML Files/" + type + "Window.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle(type);
            stage.setScene(scene);

            stage.setOnCloseRequest(e -> {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Exit Program");
                alert.setHeaderText(null);
                alert.setContentText("Exit the Program?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    System.exit(0);
                } else {
                    e.consume();
                }
            });

            stage.show();
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    @FXML
    void createProfile(ActionEvent event) {
        createWindow("Profile");
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
}