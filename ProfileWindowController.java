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

import java.time.LocalDate;
import java.util.Optional;

public class ProfileWindowController extends WindowController {

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
        // Close Window
        closeWindow(event);
    }

    @FXML
    void createProfile(ActionEvent event) {
         // Retrieve user input
        String inputUsername = usernameField.getText();
        String inputPassword = String.valueOf(passwordField.getText());
        String inputFullname = fullnameField.getText();
        String inputDateOfBirth = dateofbirthField.getText();

        // Validate the input
        String errorString = validateInput(inputUsername, inputPassword, inputFullname, inputDateOfBirth);

        if (errorString.isEmpty()) {
            // Input is all good

            // Split date string into integers
            int[] dateParts = getDateParts(inputDateOfBirth);

            // Create the account
            EventManager.createUser(inputUsername, inputPassword, inputFullname, dateParts[0], dateParts[1], dateParts[2]);

            // Party time
            createMessage("Account Created", "Account was successfully created", AlertType.CONFIRMATION);

            // close the account creation window
            exitWindow(event);
        } else {
            // Input is invalid
            createMessage("Error", errorString, AlertType.ERROR);
        }
    }
    private static String validateInput(String username, String password, String fullname, String dob) {
        /*
         * This method receives the four input strings specific to the signup form and
         * returns an error message if any of those strings are invalid. If there are no
         * errors an empty string is returned
         */

        // Check if all fields are populated
        if (username.isEmpty() || password.isEmpty() || fullname.isEmpty() || dob.isEmpty()) {
            return "Please enter every field";
        }

        // Check if the date is valid
        try {
            // Parse date string
            int[] datePart = getDateParts(dob);

            // Attempt to create a date object
            LocalDate date = LocalDate.of(datePart[2], datePart[1], datePart[0]);
            
            // Check if its after current date
            if (date.isAfter(LocalDate.now())) {
                throw new Exception();
            }
        } catch (Exception e) {
            return "Please enter a valid date (dd/mm/yyyy)";
        }

        // Check if the username already exists
        if (EventManager.getAccount(username) != null) {
            return "That username is already taken";
        }

        // No Error to Return
        return "";
    }

    private static int[] getDateParts(String dateString) {
        /*
         * This method takes a string representing a date and returns an integer array
         * of length 3 representing the day, month and year
         */
        String[] dateParts = dateString.split("/");
        try {
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            return new int[] { day, month, year };
        } catch (Exception e) {
            throw e;
        }
    }
}