import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

public class ProfileManageWindowController extends WindowController {

    @FXML
    private TextField fullnameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField dateofbirthField;

    @FXML
    private Button changeProfileButton;

    @FXML
    private Button deleteProfileButton;

    @FXML
    private TextField attendeesField;

    @FXML
    private Button editBookingButton;

    @FXML
    private Button cancelBookingButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ListView<String> bookingsList;

    @FXML
    void changeProfile(ActionEvent event) {

    }

    @FXML
    void deleteProfile(ActionEvent event) {

    }

    @FXML
    void editBooking(ActionEvent event) {

    }

    @FXML
    void cancelBooking(ActionEvent event) {

    }

    @FXML
    void exitWindow(ActionEvent event) {
        closeWindow(event);
    }

    public void initialize() {
        // Get logged user and set data fields
        User loggedUser = (User) manager.getLoggedAccount();

        fullnameField.setText(loggedUser.getFullname());
        usernameField.setText(loggedUser.getUsername());
        passwordField.setText(loggedUser.getPassword());
        dateofbirthField.setText(loggedUser.getDob());
    }
}