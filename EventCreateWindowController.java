import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

import javafx.scene.control.Alert.AlertType;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class EventCreateWindowController extends WindowController {
	// Current User
	User currentUser;

	// For Event List
	ArrayList<String> list = new ArrayList<String>();
	ObservableList<String> data;

	// Selected Event
	String selectedEvent;

	@FXML
    private TextField codeField;

    @FXML
    private TextField locationField;

    @FXML
    private Rectangle rectBackground;

    @FXML
    private Rectangle rectBackground1;

    @FXML
    private Label titleLabel;

    @FXML
    private ListView<String> eventsList = new ListView<String>();

    @FXML
    private TextField titleField;

    @FXML
    private TextField timeField;

    @FXML
    private Button createSessionButton;

    @FXML
    private TextField capacityField;

    @FXML
    private TextField dateField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField priceField;

    @FXML
    private Label titleLabel1;

    @FXML
    private Button createEventButton;

    @FXML
    void createEvent(ActionEvent event) {
    	// Retrieve User Input
    	String inputTitle = titleField.getText();
        String inputLocation = locationField.getText();
        String inputDesc = descriptionArea.getText();

        // Validate the Input
        String errorString = validateEvent(inputTitle, inputLocation, inputDesc);
    	
    	if (errorString.isEmpty()) {
    		// Input is all good

    		// Create Event 
    		EventManager.createEvent(inputTitle, inputLocation, currentUser);

    		// Display Message
    		createMessage("Event Created", "Event was successfully created", AlertType.INFORMATION);
        		
    		// Update Events
    		updateEvents();

    		// Clear Fields
    		titleField.setText("");
            locationField.setText("");
            descriptionArea.setText("");
    	} else {	
    		// Input is invalid
    		createMessage("Error", errorString, AlertType.ERROR);
    	}
    }

    @FXML
    void createSession(ActionEvent event) {
    	// Retrieve User Input
    	String inputDate = dateField.getText();
    	String inputTime = timeField.getText();
    	String inputCapacity = capacityField.getText();
    	String inputPrice = priceField.getText();

    	// Validate the Input
    	String errorString = validateSession(inputDate, inputTime, inputCapacity, inputPrice, selectedEvent);

    	if (errorString.isEmpty()) {
    		// Input is all good

    		// Split date string into integers
    		int[] dateParts = getDateParts(inputDate);

    		// Split time string into integers
    		int[] timeParts = getTimeParts(inputTime);

    		// Get Event
    		try {
    			// Get Event
    			Event newEvent = EventManager.getEvent(selectedEvent);

    			if (newEvent == null) System.out.println("Event Does not exist");

    			// Add Session
    			newEvent.addSession(dateParts[0], dateParts[1], dateParts[2], timeParts[0], timeParts[1], Double.parseDouble(inputPrice), Integer.parseInt(inputCapacity));
    		
                // Display Message
            createMessage("Session Created", "Session was successfully created", AlertType.INFORMATION);

            } catch (Exception e) {
    			createMessage("Error", e.toString(), AlertType.ERROR);
    		}
    		
    		// Clear Fields
    		dateField.setText("");
    		timeField.setText("");
    		capacityField.setText("");
    		priceField.setText("");
    	} else {
    		// Input is invalid
    		createMessage("Error", errorString, AlertType.ERROR);
    	}
    }

     @FXML
    void exitWindow(ActionEvent event) {
        closeWindow(event);
    }

    private static String validateEvent(String title, String location, String description) {
    	// Check if fields are populated
    	if (title.isEmpty() || location.isEmpty() || description.isEmpty()) {
    		return "Please enter every field";
    	}

    	// Check if event with this title exists
    	if (EventManager.getEvent(title) != null) {
    		return "An event with this title already exists";
    	}

    	return "";
    }

    public static String validateSession(String date, String time, String capacity, String price, String event) {
    	// Check if all fields are populated
    	if (date.isEmpty() || time.isEmpty() || capacity.isEmpty() || price.isEmpty()) {
    		return "Please enter every field";
    	}

    	// Check if the date is valid
    	try {
            // Parse date string
            int[] datePart = getDateParts(date);

            // Attempt to create a date object
            LocalDate newDate = LocalDate.of(datePart[2], datePart[1], datePart[0]);
            
            // Check if its before current date
            if (!newDate.isBefore(LocalDate.now())) {
                throw new Exception();
            }
        } catch (Exception e) {
            return "Please enter a valid date (dd/mm/yyyy)";
        }

        // Check if time is valid
        try {
        	// Parse time string
        	int[] timePart = getTimeParts(time);

        	// Attempt to create a time object
        	LocalTime newTime = LocalTime.of(timePart[1], timePart[0]);

        	// Check if its before current time
        	if (!newTime.isBefore(LocalTime.now())) {
        		throw new Exception();
        	}
        } catch (Exception e) {
        	return "Please enter a valid time (hh:mm)";
        }

        // Check if price is a double
        try {
        	double thisPrice = Double.parseDouble(price);
        } catch (Exception e) {
        	return "Price must be a number";
        }

        // Check if capacity is an integer
        try {
        	int thisCapacity = Integer.parseInt(capacity);
        } catch (Exception e) {
        	return "Capacity must be an integer";
        }

        // Check if an event has been selected
        if (event.isEmpty()) {
    		return "No event has been selected";
    	}

       	return "";
    }

    public void updateEvents() {
    	// Get Events from Current Account
        for (Event event : manager.getTotalEvents().values()) {
            if (event.getHost().getFullname().equals(currentUser.getFullname())) {
                list.add("Title: " + event.getTitle() + "\nLocation: " + event.getLocation() + "\nHost: " + event.getHost().getFullname());
            }
        }

        // Add data to list
        data = FXCollections.observableArrayList(list);
        eventsList.setItems(data);
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

    private static int[] getTimeParts(String timeString) {
    	String[] timeParts = timeString.split(":");
    	try {
    		int hour = Integer.parseInt(timeParts[0]);
    		int minute = Integer.parseInt(timeParts[1]);
    		return new int[] { hour, minute };
    	} catch (Exception e) {
    		throw e;
    	}
    }

    public void initialize() {
     	// Get Current Account
       	currentUser = (User) manager.getLoggedAccount();

     	// Update Events List
        updateEvents();

        // Add action listener
        eventsList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                		String [] ev = new_val.split("\n");
                        selectedEvent = ev[0].split("Title: ")[1];
                        System.out.println(selectedEvent);

                        if (manager.getEvent(selectedEvent) != null) {
                            System.out.println(manager.getEvent(selectedEvent));
                        } else {
                            System.out.println("Event Error");
                        }
            }
        });
    }
}