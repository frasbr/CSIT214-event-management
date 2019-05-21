import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Rectangle;
// import java.util.Optional;

import java.time.LocalDate;

public class UserWindowController extends WindowController {
    @FXML
    private Rectangle rectBackground;

    @FXML
    private Pane loginPane;

    @FXML
    private Button exitButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Button bookButton;

    @FXML
    private Button createButton;

    @FXML
    private TextField dateField;

    @FXML
    private Button manageButton;

    @FXML
    private Button profileButton;

    @FXML
    void createEvent(ActionEvent event) {
    	// Open window to create events
        openWindow("FXML Files/EventCreateWindow.fxml", "Create Events and Sessions");
    }

    @FXML
    void manageEvents(ActionEvent event) {
    	// Open window to manage events
    	// openWindow("FXML Files/EventManageWindow.fxml", "Manage Events");
    }

    @FXML
    void bookEvent(ActionEvent event) {
    	// Open window to book events
    	openWindow("FXML Files/EventSearchWindow.fxml", "Search Events");
    }

    @FXML
    void manageProfile(ActionEvent event) {
    	// Open window to manage profile
    	// openWindow("FXML Files/ManageProfileWindow.fxml, "Manage Profile");
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