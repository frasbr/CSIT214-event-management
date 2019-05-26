import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.Alert.AlertType;

import java.time.LocalDate;

import java.util.ArrayList;

public class ProfileManageWindowController extends WindowController {

    @FXML
    private TextField fullnameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField dateofbirthField;

    @FXML
    private Button changeProfileButton;

    @FXML
    private Button deleteProfileButton;

    @FXML
    private TextField attendeesField;

    @FXML
    private Button editBookingButton;

    @FXML
    private Button cancelBookingButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ListView<String> bookingsList;

    @FXML
    void changeProfile(ActionEvent _event) {
        // Retrieve user input
        String inputPassword = String.valueOf(passwordField.getText());
        String inputFullname = fullnameField.getText();
        String inputDateOfBirth = dateofbirthField.getText();

        // Validate the input
        String errorString = validateInput(inputPassword, inputFullname, inputDateOfBirth);

        if (errorString.isEmpty()) {
            // Input is all good

            // Split date string into integers
            int[] dateParts = getDateParts(inputDateOfBirth);

            // Change the account
            User loggedUser = (User) manager.getLoggedAccount();
            loggedUser.setData(inputPassword, inputFullname, dateParts[0], dateParts[1], dateParts[2]);

            // Save the data
            manager.writeFile("users");

            // Party time
            createMessage("Account Changed", "Account was successfully changed", AlertType.CONFIRMATION);
        } else {
            // Input is invalid
            createMessage("Error", errorString, AlertType.ERROR);
        }
    }

    @FXML
    void deleteProfile(ActionEvent _event) {
        // Check if user actually wants to delete their profile :O
        int choice = createMessage("Delete Account", "Are you sure you want to delete this profile?\nDoing so will delete any bookings and events you have created and return you to the login screen.", AlertType.CONFIRMATION);
    
        if (choice == 1) {
            // Get Profile
            User loggedUser = (User) manager.getLoggedAccount();

            // Remove Profile
            manager.getTotalAccounts().remove(loggedUser.getUsername());

            // Close Window
            closeWindow(_event);

             // Save Data
            manager.writeFile("users");
            manager.writeFile("events");

            // Perform Logout
            manager.performLogout();

            // Open Login Window
            openWindow("FXML Files/LoginWindow.fxml", "Login");
        }
    }

    @FXML
    void editBooking(ActionEvent _event) {
        User user = (User) manager.getLoggedAccount();

        if (user.getBooking(manager.getSelectedEvent(), manager.getSelectedSession()) != null) {
            // Get Bookings
            Booking booking = user.getBooking(manager.getSelectedEvent(), manager.getSelectedSession());

            // Get input
            String inputAttendee = attendeesField.getText();
            if (!inputAttendee.isEmpty()) {
                try {
                    // Get Attendee Count
                    int attendees = Integer.parseInt(inputAttendee);

                    // Update Booking
                    booking.updateAttendees(attendees);

                    // Update Bookings
                    updateBookings();
                } catch (NumberFormatException e) {
                    createMessage("Input Error", "Attendees must be an integer", AlertType.ERROR);
                }
            } else {
                createMessage("Missing Data", "Please fill all text fields", AlertType.ERROR);
            }
        }
    } 

    @FXML
    void cancelBooking(ActionEvent _event) {
        User user = (User) manager.getLoggedAccount();

        if (user.getBooking(manager.getSelectedEvent(), manager.getSelectedSession()) != null) {
            int choice = createMessage("Cancel Booking", "Are you sure you want to cancel this booking?", AlertType.CONFIRMATION);
            
            if (choice == 1) {
                // Get Booking
                Booking booking = user.getBooking(manager.getSelectedEvent(), manager.getSelectedSession());

                // Remove user from event attendees
                manager.getSelectedSession().getAttendees().remove(user);
                manager.getSelectedSession().decreaseCapacity(booking.getAttendees());

                // Remove Booking
                user.removeBooking(booking);

                // Save Data
                manager.writeFile("users");
                manager.writeFile("events");

                // Update Bookings
                updateBookings();

                // Clear Attendees field
                attendeesField.setText("");

                // Display Message
                createMessage("Cancelled Booking", "Successfully cancelled the booking", AlertType.INFORMATION);
            }
        } else {
            createMessage("No Booking Selected", "No booking was selected", AlertType.CONFIRMATION);
        }
    }

    @FXML
    void exitWindow(ActionEvent _event) {
        closeWindow(_event);
    }

    public void updateBookings() {
        // Get Logged User
        User loggedUser = (User) manager.getLoggedAccount();

        // Get Bookings
        ArrayList<String> list = new ArrayList<String>();

        for (Booking booking: loggedUser.getTotalBookings()) {
            list.add("Event: " + booking.getEvent().getTitle() + 
                "\nSession: " + booking.getSession().getDate() + " " + booking.getSession().getTime() + 
                "\nAttendees: " + booking.getAttendees() + 
                "\nRequirements: " + booking.getRequirements());
        }

        // Add data to list
        ObservableList<String> data = FXCollections.observableArrayList(list);
        bookingsList.setItems(data);   
    }

    public void initialize() {
        // Get logged user and set data fields
        User loggedUser = (User) manager.getLoggedAccount();

        fullnameField.setText(loggedUser.getFullname());
        usernameField.setText(loggedUser.getUsername());
        passwordField.setText(loggedUser.getPassword());
        dateofbirthField.setText(loggedUser.getDob());

        // Update Bookings
        updateBookings();

        // Get Bookings Event Listener
        bookingsList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                    try {
                        // Get Date/Time Key from ListView
                        String [] ev = new_val.split("\nAttendees: ");
                        String eventKey = ev[0].split("Event: ")[1].split("\n")[0];
                        String sessionKey = ev[0].split("Event: ")[1].split("Session: ")[1];

                        manager.selectEvent(manager.getEvent(eventKey));
                        manager.selectSession(manager.getEvent(eventKey).getSession(sessionKey));

                        User user = (User) manager.getLoggedAccount();
                        if (user.getBooking(manager.getSelectedEvent(), manager.getSelectedSession()) != null) {
                            Booking booking = user.getBooking(manager.getSelectedEvent(), manager.getSelectedSession());
                            attendeesField.setText(String.valueOf(booking.getAttendees()));
                        }

                        System.out.println(eventKey);
                        System.out.println(sessionKey);
                    } catch (NullPointerException e) {

                    }
                }
        });
    }
    private static String validateInput(String password, String fullname, String dob) {
        // Check if all fields are populated
        if (password.isEmpty() || fullname.isEmpty() || dob.isEmpty()) {
            return "Please enter every field";
        }

        // Check if the date is valid
        try {
            // Parse date string
            int[] datePart = getDateParts(dob);

            // Attempt to create a date object
            LocalDate date = LocalDate.of(datePart[2], datePart[1], datePart[0]);
            
            // Check if its after current date
            if (date.isAfter(LocalDate.now())) {
                throw new Exception();
            }
        } catch (Exception e) {
            return "Please enter a valid date (dd/mm/yyyy)";
        }

        // No Error to Return
        return "";
    }

    private static int[] getDateParts(String dateString) {
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
}