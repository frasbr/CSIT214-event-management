import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import javafx.scene.control.Alert.AlertType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class EventSearchWindowController extends WindowController {
    // Event Manager
	EventManager manager;

    // Selected Event
    String selectedEvent;

    // List
    ArrayList<String> list;

    @FXML
    private Rectangle rectBackground;

    @FXML
    private Pane loginPane;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private Label titleLabel;

    @FXML
    private ListView<String> sessionsList;

    @FXML
    private ListView<String> eventsList;

    @FXML
    private Button quitButton;

    @FXML
    private Button bookingButton;

    @FXML
    void performBooking(ActionEvent event) {
    	// Create booking window
        if (manager.getSelectedEvent() != null && manager.getSelectedSession() != null) {
            openWindow("FXML Files/BookingCreateWindow.fxml", "Create Booking");
        } else {
            createMessage("Error", "Please select an event and a session before performing a booking", AlertType.ERROR);
        }
    }

    @FXML
    void searchEvents(ActionEvent event) {
    	// Get User Input
    	String searchInput = searchField.getText();

    	// Validate Input
    	if (searchInput.equals("")) {
    		createMessage("Error", "Enter a search term into the search field", AlertType.ERROR);
    	} else {
    		// Update Events
            updateEvents(searchInput);

            // Display total number of entries found
            createMessage("Results Found", list.size() + " events found", AlertType.INFORMATION);
    	}
    }

    void updateEvents(String input) {
        // Create a list of events
        list = new ArrayList<String>();
        
        // Get all events based on search input
        String results;

        System.out.println("Events found: ");

        for (Event ev : manager.getTotalEvents().values()) {
            results = ev.toString();

            // Get Dates/Times from Session
            for (Session sess : ev.getTotalSessions().values()) {
                if (!results.contains(sess.getDate() + " " + sess.getTime())) {
                    results += " " + sess.getDate() + " " + sess.getTime() + " ";
                }
            }

            // Check if results match input
            if (results.contains(input) && ev.getLaunched() == true) {
                list.add("Title: " + ev.getTitle() + "\nLocation: " + ev.getLocation() + "\nHost: " + ev.getHost().getFullname());
            }
        }

        // Set the ListView to the data collected
        ObservableList<String> data = FXCollections.observableArrayList(list);
        eventsList.setItems(data);
    }

    void updateSessions() {
        // Initalise list
        list = new ArrayList<String>();

        if (manager.getEvent(selectedEvent) != null) {
            // Add sessions to sessions list
            for (Session sess : manager.getEvent(selectedEvent).getTotalSessions().values()) {
                list.add("Date: " + sess.getDate() + "\nTime: " + sess.getTime() + "\nCapacity: " + sess.getCapacity() + "\nPrice: $" + sess.getPrice());
            }
            // Set the ListView to the data collected
            ObservableList<String> data = FXCollections.observableArrayList(list);
            sessionsList.setItems(data);
        }
    }

    @FXML
    void exitProgram(ActionEvent event) {
    	// Close Stage
    	closeWindow(event);
    }

    public void initialize() {
        // Display all events
        for (Event event : manager.getTotalEvents().values()) {
            if (event.getLaunched() == true) {
                System.out.println(event);
            }
        }

        // Add action listener for events
        eventsList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                    try {
                        // Get Event Text From ListView
                        String [] ev = new_val.split("\n");
                        selectedEvent = ev[0].split("Title: ")[1];

                        // Select Event
                        if (manager.getEvent(selectedEvent) != null) {
                            manager.selectEvent(manager.getEvent(selectedEvent));

                            updateSessions();
                        }
                    } catch (NullPointerException e) {

                    }
                }
        });

        // Add action listener for events
        sessionsList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                    try {
                        // Get Date/Time Key from ListView
                        String [] ev = new_val.split("\nCapacity: ");
                        String date = ev[0].split("Date: ")[1].split("\n")[0];
                        String time = ev[0].split("Date: ")[1].split("Time: ")[1];

                        // Get Session
                        if (manager.getSelectedEvent() != null) {
                            String key = date + " " + time;
                            Session session = manager.getSelectedEvent().getSession(key);

                            if (session != null) {
                                System.out.println(session);
                                manager.selectSession(session);
                            } else {
                                System.out.println(key + " does not exist!");
                            }
                        }
                    } catch (NullPointerException e) {

                    }
                }
        });
    }
}