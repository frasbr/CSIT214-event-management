import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class BookingCreateWindowController extends WindowController {
	Event currentEvent;
    Session currentSession;

	@FXML
    private TextField codeField;

    @FXML
    private Button cancelButton;

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
        // Close current window
        closeWindow(event);

        // Open Search Window
        openWindow("FXML Files/EventSearchWindow.fxml", "Search Events");
    }

    @FXML
    void updatePrice(KeyEvent event) {
        try {
        	if (attendeeField.getText().equals("")) {
        		priceText.setText("Price: $0.0");
        	} else {
        		int attendees = Integer.parseInt(attendeeField.getText());
            	double price = attendees * currentSession.getPrice();
            	priceText.setText("Price: $" + price);
        	}
        } catch (NumberFormatException e) {

        }
    }

    @FXML
    void confirmBooking(ActionEvent event) {
    	// Retrieve User Input
    	String inputAttendee = attendeeField.getText();
    	String inputCode = codeField.getText();
    	String inputRequirements = requirementArea.getText();

    	// Validate the inputs
    	String errorString = validateInput(inputAttendee, inputCode, inputRequirements);

    	if (errorString.isEmpty()) {
    		// Input is all good
    		User user = (User) manager.getLoggedAccount();

    		boolean bookingExists = (user.bookingExists(currentEvent, currentSession));
    		System.out.println(bookingExists);

    		if (bookingExists == false) {
    			int choice = createMessage("Confirm Booking", "Are you sure you want to create a booking for this event?", AlertType.CONFIRMATION);

                if (choice == 1) {
    				// Create New Booking
    				currentSession.makeBooking(currentEvent, currentSession, user, Integer.parseInt(inputAttendee), inputRequirements);
    		
    				// Display Message
    				createMessage("Booking Successful", "Booking has been successfully created", AlertType.INFORMATION);
    			}	
    		} else {
    			// Booking already exists
    			createMessage("Booking Exists", "You have already made a booking for this event.\nUse the Manage Profile option to modify or cancel a booking", AlertType.INFORMATION);
    		}
    	} else {
    		// Invalid Input
    		createMessage("Error", errorString, AlertType.ERROR);
    	}
    }

     private String validateInput(String attendees, String code, String requirements) {
        // Check if attendees is empty
        if (attendees.isEmpty()) {
            return "Please enter the number of attendees required";
        }

        // Check if attendees is an integer
        try {
            int attendeeCount = Integer.parseInt(attendees);

            if ((currentSession.getCurCapacity() + attendeeCount) > currentSession.getCapacity()) {
                return "The number of attendees entered is larger than the session's capacity";
            }

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