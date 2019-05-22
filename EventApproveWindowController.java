import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.shape.Rectangle;

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
    private Button rejectEventButton;

    @FXML
    private Rectangle rectBackground1;

    @FXML
    private ListView<String> eventsList;

    @FXML
    private Label titleLabel;

    @FXML
    void approveEvent(ActionEvent event) {
        // Display Approval Message
        int choice = createMessage("Approve Event", "Are you sure you want to approve this event?", AlertType.CONFIRMATION);

        if (choice == 1) {
            try {
                // Launch Event
                Event ev = manager.getEvent(selectedEvent);
                ev.launch(true);

                // Update Event
                updateEvents();

                // Display Message
                createMessage("Event Approved", "This Event has been approved for launch", AlertType.INFORMATION);
            } catch (Exception e) {
                createMessage("Error", "No Event was Found", AlertType.ERROR);
            }
        }
    }

    @FXML
    void rejectEvent(ActionEvent event) {
        // Display Approval Message
        int choice = createMessage("Reject Event", "Are you sure you want to reject this event?", AlertType.CONFIRMATION);
    }

    @FXML
    void exitWindow(ActionEvent event) {
        closeWindow(event);
    }

    public void updateEvents() {
        ArrayList<String> list = new ArrayList<String>();
        
        for (Event ev : manager.getTotalEvents().values()) {
            if (ev.getLaunched() == false) {
                    list.add("Title: " + ev.getTitle() + "\nLocation: " + ev.getLocation() + "\nHost: " + ev.getHost().getFullname());
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