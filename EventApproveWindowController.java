import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.scene.control.Alert.AlertType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class EventApproveWindowController extends WindowController {

    String selectedEvent;

    @FXML
    private Button cancelButton;

    @FXML
    private Button approveEventButton;

    @FXML
    private ListView<String> eventsList;

    @FXML
    void approveEvent(ActionEvent event) {
        // Get Event
        Event ev = manager.getEvent(selectedEvent);

        if (ev != null) {
            if (ev.getTotalSessions().size() > 0) {
                int choice = createMessage("Approve Event", "Are you sure you want to approve this event?", AlertType.CONFIRMATION);

                if (choice == 1) {
                    // Launch Event
                    ev.launchEvent(true);

                    // Update Events
                    updateEvents();

                    // Reset Selected Event
                    selectedEvent = null;

                    // Display Message
                    createMessage("Event Approved", "This Event has been approved for launch", AlertType.INFORMATION);
                }
            } else {
                createMessage("Event Error", "Events without sessions cannot be launched", AlertType.ERROR);
            }
        } else {
            createMessage("Event Error", "No event was found", AlertType.ERROR);
        }
    }

    @FXML
    void exitWindow(ActionEvent event) {
        // Close Window
        closeWindow(event);

        // Save data
        manager.writeFile("events");
    }

    public void updateEvents() {
        ArrayList<String> list = new ArrayList<String>();
        
        for (Event ev : manager.getTotalEvents().values()) {
            if (ev.getLaunched() == false) {
                    list.add("Title: " + ev.getTitle() + 
                            "\nLocation: " + ev.getLocation() + 
                            "\nDescription: " + ev.getDescription() + 
                            "\nHost: " + ev.getHost().getFullname() + 
                            "\nSessions: " + ev.getTotalSessions().size());
            }
        }

        // Set the ListView to the data collected
        ObservableList<String> data = FXCollections.observableArrayList(list);
        eventsList.setItems(data);
    }   

    public void initialize() {
        // Get Events and add them to list
        updateEvents();

        // Add action listener
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
                        }
                    } catch (NullPointerException e) {

                    }
                }
        });
    }
}