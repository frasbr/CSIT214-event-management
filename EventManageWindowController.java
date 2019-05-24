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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;


public class EventManageWindowController extends WindowController {

    String selectedEvent;
    String selectedSession;

    User currentUser;

    Session session;

    @FXML
    private TextField locationField;

    @FXML
    private Rectangle rectBackground1;

    @FXML
    private Label titleLabel;

    @FXML
    private ListView<String> eventsList;

    @FXML
    private TextField titleField;

    @FXML
    private Button deleteEventButton;

    @FXML
    private TextField timeField;

    @FXML
    private TextField capacityField;

    @FXML
    private TextField dateField;

    @FXML
    private Button editEventButton;

    @FXML
    private Button deleteSessionButton;

    @FXML
    private Button editSessionButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField priceField;

    @FXML
    private Label titleLabel1;

    @FXML
    private ListView<String> sessionsList;

    @FXML
    private ListView<String> eventsList1;

    @FXML
    private Rectangle rectBackground11;

    @FXML
    void editEvent(ActionEvent event) {
        // Get Input
        String inputTitle = titleField.getText();
        String inputLocation = locationField.getText();

        if (!selectedEvent.equals("")) {
            // Get Choice
            int choice = createMessage("Edit Event", "Are you sure you want to perform these edits to this event?", AlertType.CONFIRMATION);
        
            if (choice == 1) {
                // // Delete Session
                // Event ev = manager.getEvent(selectedEvent);
                // ev.removeSession(session);

                // // Update Sessions
                // updateSessions();

                // Display Message
                createMessage("Edits successful", "Event has been successfully edited", AlertType.INFORMATION);

                // Save Data
                //manager.writeFile("events");
                
                // Set Data
                dateField.setText("");
                timeField.setText("");
                priceField.setText("");
                capacityField.setText("");
            }
        } else {
            createMessage("No Event Selected", "Please select an event before attempting to perform any edits", AlertType.ERROR);
        }   
    }

    @FXML
    void deleteEvent(ActionEvent event) {
        // Get Input
        String inputTitle = titleField.getText();
        String inputLocation = locationField.getText();

        if (!selectedEvent.equals("")) {
            // Get Choice
            int choice = createMessage("Delete Event", "Are you sure you want to delete this event?", AlertType.CONFIRMATION);
        
            if (choice == 1) {
                // // Delete Session
                // Event ev = manager.getEvent(selectedEvent);
                // ev.removeSession(session);

                // // Update Sessions
                // updateSessions();

                // Display Message
                createMessage("Event Deleted", "Event has been successfully deleted", AlertType.INFORMATION);

                // Save Data
                //manager.writeFile("events");
                
                // Set Data
                dateField.setText("");
                timeField.setText("");
                priceField.setText("");
                capacityField.setText("");
            }
        } else {
            createMessage("No Event Selected", "Please select an event before attempting to delete it", AlertType.ERROR);
        }   
    }   

    @FXML
    void editSession(ActionEvent event) {
        if (!selectedEvent.equals("") && !selectedSession.equals("")) {
            // Get Choice
            int choice = createMessage("Edit Session", "Are you sure you want to perform these edits to this session?", AlertType.CONFIRMATION);
        
            if (choice == 1) {
                // // Delete Session
                // Event ev = manager.getEvent(selectedEvent);
                // ev.removeSession(session);

                // // Update Sessions
                // updateSessions();

                // Display Message
                createMessage("Edits successful", "Session has been successfully edited", AlertType.INFORMATION);

                // Save Data
                //manager.writeFile("events");
                
                // Set Data
                dateField.setText("");
                timeField.setText("");
                priceField.setText("");
                capacityField.setText("");
            }
        } else {
            createMessage("No Session Selected", "Please select a session before attempting to perform any edits", AlertType.ERROR);
        }   
    }

    @FXML
    void deleteSession(ActionEvent event) {
        // Get Choice
        int choice = createMessage("Delete Session", "Are you sure you want to delete this session?", AlertType.CONFIRMATION);
    
        if (choice == 1) {
            // Delete Session
            // Event ev = manager.getEvent(selectedEvent);
            // ev.removeSession(session);

            // Update Sessions
            // updateSessions();

            // Display Message
            createMessage("Session Deleted", "Session has been successfully deleted", AlertType.INFORMATION);
                
            // Save Data
            manager.writeFile("events");

            // Set Data
            dateField.setText("");
            timeField.setText("");
            priceField.setText("");
            capacityField.setText("");
        }
    }


    @FXML
    void exitWindow(ActionEvent event) {
        closeWindow(event);
    }

    public void updateEvents() {
        // Create a list of events
        ArrayList<String> list = new ArrayList<String>();
        
        // Get all events based on search input
         for (Event ev : manager.getTotalEvents().values()) {
            if (ev.getHost().getFullname().equals(currentUser.getFullname())) {
                //System.out.println("Adding Event " + ev.getTitle());
                list.add("Title: " + ev.getTitle() + "\nLocation: " + ev.getLocation() + "\nHost: " + ev.getHost().getFullname());
            }
        }

        // Set the ListView to the data collected
        ObservableList<String> data = FXCollections.observableArrayList(list);
        eventsList.setItems(data);
        eventsList1.setItems(data);
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
        // Get Current Account
        currentUser = (User) manager.getLoggedAccount();

        // Update Events
        updateEvents();

        // Add action listener for the manage events list
        eventsList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                    try {
                        // Get Event Text From ListView
                        String [] ev = new_val.split("\n");
                        selectedEvent = ev[0].split("Title: ")[1];

                        // Select Event
                        if (manager.getEvent(selectedEvent) != null) {
                            Event event = manager.getEvent(selectedEvent);
                            titleField.setText(event.getTitle());
                            locationField.setText(event.getLocation());
                        }
                    } catch (NullPointerException e) {

                    }
                }
        });

        // Add action listener for manage sessions - events list
        eventsList1.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                    try {
                        // Get Event Text From ListView
                        String [] ev = new_val.split("\n");
                        selectedEvent = ev[0].split("Title: ")[1];

                        // Select Event and get sessions
                        updateSessions();

                    } catch (NullPointerException e) {

                    }
                }
        });

        // Add action listener for manage sessions - events list
        sessionsList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                    try {
                        // Get Event Text From ListView
                        String [] ev = new_val.split("\n");
                        selectedSession = ev[0].split("Date: ")[1];

                        System.out.println(selectedSession);

                        // Select Event
                        ArrayList<String> list = new ArrayList<String>();

                        // Select Session
                        if (manager.getEvent(selectedEvent) != null) {

                            for (Session sess : manager.getEvent(selectedEvent).getTotalSessions().values()) {
                                if (selectedSession.equals(sess.getDate())) {
                                    session = sess;
                                }
                            }

                            // Set Data
                            dateField.setText(session.getDate());
                            timeField.setText(session.getTime());
                            priceField.setText(Double.toString(session.getPrice()));
                            capacityField.setText(Integer.toString(session.getCapacity()));
                        }
                    } catch (NullPointerException e) {

                    }
                }
        });

    }
}