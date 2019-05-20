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
    private ListView<String> eventsList;

    @FXML
    private Button quitButton;

    @FXML
    private Button bookingButton;

    @FXML
    void performBooking(ActionEvent event) {
    	// Create booking window
    	openWindow("FXML Files/BookingCreateWindow.fxml", "Create Booking");
    }

    @FXML
    void searchEvents(ActionEvent event) {
    	// Get User Input
    	String searchInput = searchField.getText();

    	// Validate Input
    	if (searchInput.equals("")) {
    		createMessage("Error", "Enter a search term into the search field", AlertType.ERROR);
    	} else {
    		// Create a list of events
    		ArrayList<String> list = new ArrayList<String>();
    	
    		// Get all events based on search input
    		for (Event ev : manager.getTotalEvents().values()) {
    			if (ev.toString().contains(searchInput)) {
    				list.add(ev.getTitle() + " - " + ev.getLocation() + "\nHost: " + ev.getHost().getFullname());
    			}
    		}

    		// Set the ListView to the data collected
    		ObservableList<String> data = FXCollections.observableArrayList(list);
            eventsList.setItems(data);

            // Display total number of entries found
            createMessage("Results Found", list.size() + " events found", AlertType.INFORMATION);
    	}
    }

    @FXML
    void exitProgram(ActionEvent event) {
    	// Close Stage
    	closeWindow(event);
    }

    public void initialize() {
        // Add action listener
        eventsList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                        String [] ev = new_val.split(" -");
                        selectedEvent = ev[0];
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