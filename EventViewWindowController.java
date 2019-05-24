import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.shape.Rectangle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

public class EventViewWindowController extends WindowController {

    String selectedEvent;

    // For Event List
    ArrayList<String> list = new ArrayList<String>();
    ObservableList<String> data;

    @FXML
    private Rectangle rectBackground;

    @FXML
    private Button cancelButton;

    @FXML
    private Label titleLabel;

    @FXML
    private ListView<String> eventsList;

    @FXML
    private ListView<String> sessionsList;

    @FXML
    void exitWindow(ActionEvent event) {
        closeWindow(event);
    }

    public void updateSessions() {
        // Initalise list
        ArrayList<String> list = new ArrayList<String>();

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

    public void initialize() {
        // Get Events
        for (Event event : manager.getTotalEvents().values()) {
            if (event.getLaunched() == true) {
                list.add("Title: " + event.getTitle() + "\nLocation: " + event.getLocation() + "\nHost: " + event.getHost().getFullname());
            }
        }

        // Add data to list
        data = FXCollections.observableArrayList(list);
        eventsList.setItems(data);

        // Add listener to list
        eventsList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                        // Get Event Title
                        String [] ev = new_val.split("\n");
                        selectedEvent = ev[0].split("Title: ")[1];

                        // Get Event and Update Sessions List
                        if (manager.getEvent(selectedEvent) != null) {
                            updateSessions();
                        } else {
                            System.out.println("Event Error");
                        }
            }
        });
    }
}