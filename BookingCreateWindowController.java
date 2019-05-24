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
    // Selected Event and Session
    Event currentEvent;
    Session currentSession;

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
        try {
            int attendees = Integer.parseInt(attendeeField.getText());
            double price = attendees * currentSession.getPrice();
            priceText.setText("$" + price);
        } catch (NumberFormatException e) {

        }
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
            
            // Create Booking
            boolean success = currentSession.makeBooking((User) manager.getLoggedAccount(), Integer.parseInt(inputAttendee));
            if (success) {
                // Display Message
                createMessage("Successful Booking", "You have successfully booked this event", AlertType.INFORMATION);

                // Close the Booking Creation window
                exitWindow(event);
            } else {
                // Booking has already been made
                createMessage("Booking already made", "You have already made a booking for this event. Go to 'Manage Profile' to change or remove your booking", AlertType.INFORMATION);
            }
        } else {
            // Input is invalid
            createMessage("Error", errorString, AlertType.ERROR);
        }

        // Clear data
        
    }
    private static String validateInput(String attendees, String code, String requirements) {
        // Check if attendees is empty
        if (attendees.isEmpty()) {
            return "Please enter the number of attendees required";
        }

        // Check if attendees is an integer
        try {
            int attendeeCount = Integer.parseInt(attendees);
        } catch(NumberFormatException e) {
            return "Please enter an integer number of attendees";
        }

        // No Error to Return
        return "";
    }
    public void initialize() {
        // Get Selected Event
        currentEvent = manager.getSelectedEvent();
        currentSession = manager.getSelectedSession();

        // Set Price
        priceText.setText("$0.00");
        eventInfoField.setText("Title: " + currentEvent.getTitle() + 
                                "\nLocation: " + currentEvent.getLocation() + 
                                "\nHost: " + currentEvent.getHost().getFullname() + 
                                "\nDate: " + currentSession.getDate() + 
                                "\nTime: " + currentSession.getTime() + 
                                "\nPrice: $" + currentSession.getPrice() + 
                                "\nCapacity: " + currentSession.displayCapacity());
    }
}