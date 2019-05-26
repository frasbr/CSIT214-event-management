import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.control.Alert.AlertType;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class EventCreateWindowController extends WindowController {
	// Create Event
    @FXML
    private TextField titleField;

    @FXML
    private TextField locationField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button createEventButton;

    // Create Session
    @FXML
    private TextField dateField;

    @FXML
    private TextField timeField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField codeField;

    @FXML
    private TextField capacityField;

    @FXML
    private Button createSessionButton;

    // Cancel Button
    @FXML
    private Button cancelButton;

    @FXML
    private ListView<String> eventsList = new ListView<String>();

    @FXML
    void createEvent(ActionEvent _event) {
    	// Retrieve User Input
    	String inputTitle = titleField.getText();
        String inputLocation = locationField.getText();
        String inputDesc = descriptionArea.getText();

        // Validate the Input
        String errorString = validateEvent(inputTitle, inputLocation, inputDesc);
    	
    	if (errorString.isEmpty()) {
    		// Input is all good

            User currentUser = (User) manager.getLoggedAccount();

    		// Create Event 
    		manager.createEvent(inputTitle, inputLocation, inputDesc, currentUser);

    		// Display Message
    		createMessage("Event Created", "Event was successfully created", AlertType.INFORMATION);
        		
    		// Update Events
    		updateEvents();

    		// Clear Fields
    		titleField.setText("");
            locationField.setText("");
            descriptionArea.setText("");
        }
    }

    @FXML
    void createSession(ActionEvent _event) {
    	// Retrieve User Input
    	String inputDate = dateField.getText();
    	String inputTime = timeField.getText();
    	String inputCapacity = capacityField.getText();
    	String inputPrice = priceField.getText();

    	// Validate the Input
    	String errorString = validateSession(inputDate, inputTime, inputCapacity, inputPrice, manager.getSelectedEvent());

    	if (errorString.isEmpty()) {
    		// Input is all good

    		// Split date string into integers
    		int[] dateParts = getDateParts(inputDate);

    		// Split time string into integers
    		int[] timeParts = getTimeParts(inputTime);

    		// Get Event
    		try {
    			if (manager.getSelectedEvent() == null) {
    				createMessage("Event Error", "No Event was found", AlertType.ERROR);
    			} else {
    				// Add Session
    				manager.getSelectedEvent().addSession(dateParts[0], dateParts[1], dateParts[2], timeParts[0], timeParts[1], Double.parseDouble(inputPrice), Integer.parseInt(inputCapacity));
    		
                	// Display Message
            		createMessage("Session Created", "Session was successfully created", AlertType.INFORMATION);
    			}
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
    void exitWindow(ActionEvent _event) {
        closeWindow(_event);
    }

    private static String validateEvent(String _title, String _location, String _description) {
    	// Check if fields are populated
    	if (_title.isEmpty() || _location.isEmpty() || _description.isEmpty()) {
    		return "Please enter every field";
    	}

    	// Check if event with this title exists
        Event event = EventManager.getEvent(_title);
    	if (event != null) {
    		return "An event with this title already exists";
    	}

    	return "";
    }

    public static String validateSession(String _date, String _time, String _capacity, String _price, Event _event) {
    	// Check if all fields are populated
    	if (_date.isEmpty() || _time.isEmpty() || _capacity.isEmpty() || _price.isEmpty()) {
    		return "Please enter every field";
    	}

    	// Check if the date is valid
    	try {
            // Parse date string
            int[] datePart = getDateParts(_date);

            // Attempt to create a date object
            LocalDate newDate = LocalDate.of(datePart[2], datePart[1], datePart[0]);
            
            // Check if its before current date
            if (!newDate.isAfter(LocalDate.now())) {
                throw new Exception();
            }
        } catch (Exception e) {
            return "Please enter a valid date (dd/mm/yyyy)";
        }

        // Check if time is valid
        try {
        	// Parse time string
        	int[] timePart = getTimeParts(_time);

        	// Attempt to create a time object
        	LocalTime newTime = LocalTime.of(timePart[1], timePart[0]);

        } catch (Exception e) {
        	return "Please enter a valid time (hh:mm)";
        }

        // Check if price is a double
        try {
        	double thisPrice = Double.parseDouble(_price);
        } catch (Exception e) {
        	return "Price must be a number";
        }

        // Check if capacity is an integer
        try {
        	int thisCapacity = Integer.parseInt(_capacity);
        } catch (Exception e) {
        	return "Capacity must be an integer";
        }

        // Check if an event has been selected
        if (_event == null) {
    		return "No event has been selected";
    	}

       	return "";
    }

    public void updateEvents() {
    	// Get Events from Current Account
        ArrayList<String> list = new ArrayList<String>();

        User loggedUser = (User) manager.getLoggedAccount();

        for (Event event : manager.getTotalEvents().values()) {
            if (event.getHost().getFullname().equals(loggedUser.getFullname())) {
                list.add("Title: " + event.getTitle() + "\nLocation: " + event.getLocation() + "\nHost: " + event.getHost().getFullname());
            }
        }

        // Add data to list
        ObservableList<String> data = FXCollections.observableArrayList(list);
        eventsList.setItems(data);   
    }

    private static int[] getDateParts(String _dateString) {
        /*
         * This method takes a string representing a date and returns an integer array
         * of length 3 representing the day, month and year
         */
        String[] dateParts = _dateString.split("/");
        try {
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            return new int[] { day, month, year };
        } catch (Exception e) {
            throw e;
        }
    }

    private static int[] getTimeParts(String _timeString) {
    	String[] timeParts = _timeString.split(":");
    	try {
    		int hour = Integer.parseInt(timeParts[0]);
    		int minute = Integer.parseInt(timeParts[1]);
    		return new int[] { hour, minute };
    	} catch (Exception e) {
    		throw e;
    	}
    }

    public void initialize() {
    	// Update Events List
        updateEvents();

        // Add action listener for manage eventss event list
        eventsList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                    try {
                        // Get Event Key From ListView
                        String[] ev = new_val.split("\nLocation: ");
                        String key = ev[0].split("Title: ")[1];

                        if (key != null) {
                            // Select Event
                            manager.selectEvent(manager.getEvent(key));
                        }
                    } catch (NullPointerException e) {

                    }
                }
        });
    }
}