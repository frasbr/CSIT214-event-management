import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDate;
import java.util.Optional;

public class ProfileCreateWindowController extends WindowController {

    @FXML
    private TextField fullnameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField dateofbirthField;

    @FXML
    private Button createButton;

    @FXML
    private Button cancelButton;

    @FXML
    void exitWindow(ActionEvent _event) {
        // Close Window
        closeWindow(_event);
    }

    @FXML
    void createProfile(ActionEvent _event) {
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
            exitWindow(_event);
        } else {
            // Input is invalid
            createMessage("Error", errorString, AlertType.ERROR);
        }
    }
    private static String validateInput(String username, String password, String fullname, String dob) {
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