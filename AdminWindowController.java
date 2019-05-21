import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.time.LocalDate;

public class AdminWindowController extends WindowController {
    @FXML
    private Rectangle rectBackground;

    @FXML
    private Pane loginPane;

    @FXML
    private Button exitButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Button viewButton;

    @FXML
    private TextField dateField;

    @FXML
    private Button approveButton;

    @FXML
    private Button statsButton;

    @FXML
    void approveEvents(ActionEvent event) {

    }

    @FXML
    void viewEvents(ActionEvent event) {

    }

    @FXML
    void viewStatistics(ActionEvent event) {

    }

    @FXML
    void exitWindow(ActionEvent event) {
        // Close Current Window
        closeWindow(event);

        // Perform Logout
        manager.performLogout();

        // Open Login Window
        openWindow("FXML Files/LoginWindow.fxml", "Login");
    }

    public void initialize() {
        // Set Date of DateField
        dateField.setText(LocalDate.now().toString()); 
    }
}