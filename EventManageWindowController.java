import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.control.Alert.AlertType;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventManageWindowController extends WindowController {

	// Manage Events 
    @FXML
    private ListView<String> eventsList;

    @FXML
    private TextField titleField;

    @FXML
    private TextField locationField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button editEventButton;

    @FXML
    private Button deleteEventButton;

    // Manage Sessions
    @FXML
    private ListView<String> eventsList1;

    @FXML
    private ListView<String> sessionsList;

    @FXML
    private TextField dateField;

    @FXML
    private TextField timeField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField capacityField;

    @FXML
    private Button editSessionButton;

    @FXML
    private Button deleteSessionButton;

    // Cancel Button
    @FXML
    private Button cancelButton;

    @FXML
    void editEvent(ActionEvent _event) {
        // Get Selected Event
        Event event = manager.getSelectedEvent();

        // Check if event was selected
        if (event != null) {
            // Get Input
            String inputTitle = titleField.getText();
            String inputLocation = locationField.getText();
            String inputDescription = descriptionArea.getText();

            // Get Choice
        	int choice = createMessage("Edit Event", "Are you sure you want to perform these edits to this event?", AlertType.CONFIRMATION);

            if (choice == 1) {
                // Edit Event
                event.editData(inputLocation, inputDescription);

                // Display Message
                createMessage("Edits successful", "Event has been successfully edited", AlertType.INFORMATION);

                // Update Events
                updateEvents();

                // Clear Data
                titleField.setText("");
                locationField.setText("");
                descriptionArea.setText("");
            }
        } else {
            createMessage("No Event Selected", "Please select an event before attempting to perform any edits", AlertType.ERROR);
        }
    }
    
    @FXML
    void deleteEvent(ActionEvent _event) {
        // Check if event was selected
        Event event = manager.getSelectedEvent();

        if (event != null) {
            // Get key
            String key = event.getTitle();

            // Get Choice
            int choice = createMessage("Delete Event", "Are you sure you want to delete this event?", AlertType.CONFIRMATION);
        
            if (choice == 1) {
                try {
                    // Delete Event and sessions
                    event.getTotalSessions().clear();
                    manager.getTotalEvents().remove(key);

                    // Save Data
                    manager.writeFile("events");
                    
                    // Reset Selected Event
                    manager.selectEvent(null);

                    // Update Events and Sessions
                    updateSessions(key);
                    updateEvents();

                    // Display Message
                    createMessage("Event Deleted", "Event has been successfully deleted", AlertType.INFORMATION);

                    // Clear Text
                    titleField.setText("");
                    locationField.setText("");
                    descriptionArea.setText("");

                } catch (NullPointerException e) {
                   
                }
            }
        } else {
            createMessage("No Event Selected", "Please select an event before attempting to delete it", AlertType.ERROR);
        }
    }

    @FXML
    void editSession(ActionEvent _event) {
        // Get Selected Event and session
        Event event = manager.getSelectedEvent();
        Session session = manager.getSelectedSession();

        if (session != null) {
            // Get Data
            String inputDate = dateField.getText();
            String inputTime = timeField.getText();
            String inputPrice = priceField.getText();
            String inputCapacity = capacityField.getText();

            String errorString = validateSession(inputDate, inputTime, inputPrice, inputCapacity);

            if (errorString.isEmpty()) {
                int choice = createMessage("Edit Session", "Are you sure you want to perform these edits to this session?", AlertType.CONFIRMATION);

                if (choice == 1) {
                     // Edit Session
                    //session.editData();

                    // Display Message
                    createMessage("Session deleted", "Session has been successfully deleted", AlertType.INFORMATION);

                    // Clear Text
                    dateField.setText("");
                    timeField.setText("");
                    priceField.setText("");
                    capacityField.setText("");

                    // Display Message
                    createMessage("Edits successful", "Session has been successfully edited", AlertType.INFORMATION);
                }
            } else {
                createMessage("Error", errorString, AlertType.ERROR);
            }
        } else {
            createMessage("No Sessions Selected", "Please select a session before attempting to perform edits to it", AlertType.ERROR);
        }
    }

    @FXML
    void deleteSession(ActionEvent _event) {
        // Get Selected Event and session
        Event event = manager.getSelectedEvent();
        Session session = manager.getSelectedSession();

        if (session != null) {
            int choice = createMessage("Delete Session", "Are you sure you want to delete this session?", AlertType.CONFIRMATION);

            if (choice == 1) {
                // Remove Session
                event.removeSession(session.getDate() + " " + session.getTime());

                // Display Message
                createMessage("Session deleted", "Session has been successfully deleted", AlertType.INFORMATION);

                // Clear Text
                dateField.setText("");
                timeField.setText("");
                priceField.setText("");
                capacityField.setText("");
            }
        } else {
            createMessage("No Sessions Selected", "Please select a session before attempting to delete it", AlertType.ERROR);
        }
    }

    @FXML
    void exitWindow(ActionEvent _event) {
        // Close Window
        closeWindow(_event);

        // Set Event and Session to Null
        manager.selectSession(null);
        manager.selectEvent(null);
    }

    // Update Events
    public void updateEvents() {
        // Create a list of events
        ArrayList<String> list = new ArrayList<String>();
        list.clear();

        User currentUser = (User) manager.getLoggedAccount();
        
        // Get all events based on search input
         for (Event event : manager.getTotalEvents().values()) {
            if (event.getHost().getFullname().equals(currentUser.getFullname())) {
                list.add("Title: " + event.getTitle() + "\nLocation: " + event.getLocation() + "\nHost: " + event.getHost().getFullname() + "\nLaunched: " + (event.getLaunched() == true ? "Yes" : "No"));
            }
        }
         // Set the ListView to the data collected
        ObservableList<String> data = FXCollections.observableArrayList(list);
        eventsList.setItems(data);
        eventsList1.setItems(data);
    }

    public void updateSessions(String _key) {
        // Initalise list
        ArrayList<String> list = new ArrayList<String>();
        list.clear();

        if (manager.getEvent(_key) != null) {
            // Add sessions to sessions list
            for (Session sess : manager.getEvent(_key).getTotalSessions().values()) {
                list.add("Date: " + sess.getDate() + "\nTime: " + sess.getTime() + "\nCapacity: " + sess.getCapacity() + "\nPrice: $" + sess.getPrice());
            }
            // Set the ListView to the data collected
            ObservableList<String> data = FXCollections.observableArrayList(list);
            sessionsList.setItems(data);
        }
    }

    // Initialise Events
    public void initialize() {
        // Update Events
        updateEvents();
        updateSessions("");

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

                            // Set Text
                            titleField.setText(manager.getSelectedEvent().getTitle());
                            locationField.setText(manager.getSelectedEvent().getLocation());
                            descriptionArea.setText(manager.getSelectedEvent().getDescription());
                        }
                    } catch (NullPointerException e) {

                    }
                }
        });

        // Add action listener for manage sessions event list
        eventsList1.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                    try {
                        // Get Event Text From ListView
                        String [] ev = new_val.split("\n");
                        String key = ev[0].split("Title: ")[1];

                        // Select Event and get sessions
                        updateSessions(key);

                        // Clear Data
                        dateField.setText("");
                        timeField.setText("");
                        priceField.setText("");
                        capacityField.setText("");
                    } catch (NullPointerException e) {
                        
                    }
                }
        });

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
                                manager.selectSession(session);
                            }

                            // Display Data
                            dateField.setText(manager.getSelectedSession().getDate());
                            timeField.setText(manager.getSelectedSession().getTime());
                            priceField.setText(String.valueOf(manager.getSelectedSession().getPrice()));
                            capacityField.setText(String.valueOf(manager.getSelectedSession().getCapacity()));
                        }
                    } catch (NullPointerException e) {
                        
                    }
                }
        });
    }

    public static String validateSession(String _date, String _time, String _capacity, String _price) {
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
        return "";
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
}