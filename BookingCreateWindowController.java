import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class BookingCreateWindowController extends WindowController {

    EventManager manager;

    Event currentEvent;

    @FXML
    private TextField codeField;

    @FXML
    private Rectangle rectBackground;

    @FXML
    private Pane loginPane;

    @FXML
    private Button cancelButton;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField attendeeField;

    @FXML
    private TextArea requirementArea;

    @FXML
    private TextArea eventInfoField;

    @FXML
    private Button confirmButton;

    @FXML
    private Label priceText;

    @FXML
    void exitWindow(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    void updatePrice(KeyEvent event) {
        int attendees = Integer.parseInt(attendeeField.getText());
        double price = attendees * currentEvent.getSession(0).getPrice();
        priceText.setText("$" + price);
    }

    @FXML
    void confirmBooking(ActionEvent event) {
        // Retrieve user input
        String inputAttendee    = attendeeField.getText();
        String inputCode        = codeField.getText();
        String inputRequirement = requirementArea.getText();
        
        // Validate the input
        String errorString = validateInput(inputAttendee, inputCode, inputRequirement);

        if (errorString.isEmpty()) {
            // Input is all good

            // Close the Booking Creation window
            exitWindow(event);
        } else {
            // Input is invalid
            createMessage("Error", errorString, AlertType.ERROR);
        }

        // Clear data
        
    }
    private static String validateInput(String attendees, String code, String requirements) {
        /*
         * This method receives the four input strings specific to the signup form and
         * returns an error message if any of those strings are invalid. If there are no
         * errors an empty string is returned
         */

        if (attendees.isEmpty()) {
            return "Please enter the number of attendees required";
        }

        // No Error to Return
        return "";
    }
    public void initialize() {
        // Get Selected Event
        currentEvent = manager.getSelectedEvent();

        // Set Price
        priceText.setText("$0.00");
        eventInfoField.setText(currentEvent.getTitle() + "\n" + currentEvent.getHost().getFullname());
    }
}