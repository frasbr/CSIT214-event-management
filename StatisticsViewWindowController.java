import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class StatisticsViewWindowController extends WindowController {
    // Selected Options
    String userOption;
    String eventOption;

    // Date
    LocalDate searchDate;

    // Toggle Groups
    @FXML
    private ToggleGroup eventStatsToggle;

    @FXML
    private ToggleGroup userStatsToggle;

    // List Views
    @FXML
    private ListView<String> eventStatsList;

    @FXML
    private ListView<String> userStatsList;

    // Choice Boxes
    @FXML
    private ChoiceBox<String> userStatsChoice;

    @FXML
    private ChoiceBox<String> eventStatsChoice;

    // Event Radios
    @FXML
    private RadioButton userDayRadio;

    @FXML
    private RadioButton userWeekRadio;

    @FXML
    private RadioButton userMonthRadio;

    @FXML
    private RadioButton userYearRadio;

    @FXML
    private RadioButton userAllRadio;

    // User Radios
    @FXML
    private RadioButton eventDayRadio;

    @FXML
    private RadioButton eventWeekRadio;

    @FXML
    private RadioButton eventMonthRadio;

    @FXML
    private RadioButton eventYearRadio;

    @FXML
    private RadioButton eventAllRadio;

    // Search Buttons
    @FXML
    private Button eventSearchButton;

    @FXML
    private Button userSearchButton;

    @FXML
    private Button cancelButton;

    public void initialize() {
        // Add User Stats Choices
        userStatsChoice.getItems().add("Most Events Created");
        userStatsChoice.getItems().add("Least Events Created");
        userStatsChoice.getItems().add("Most Events Attended");
        userStatsChoice.getItems().add("Least Events Attended");
        userStatsChoice.valueProperty().addListener((obs, oldVal, newVal) -> userOption = newVal);

        // Add Events Stats Choices
        eventStatsChoice.getItems().add("Most Used Location");
        eventStatsChoice.getItems().add("Least Used Location");
        eventStatsChoice.getItems().add("Most Attended Event");
        eventStatsChoice.getItems().add("Least Attended Event");
        eventStatsChoice.getItems().add("Event with Most Sessions");
        eventStatsChoice.getItems().add("Event with Least Sessions");
        eventStatsChoice.valueProperty().addListener((obs, oldVal, newVal) -> eventOption = newVal);
    }

    @FXML
    void getUserStats(ActionEvent event) {
        if (userStatsToggle.getSelectedToggle() != null) {
            if (userOption != null) {
                // Get Time Period
                getSearchDate(userStatsToggle);

                // Get Search Option
                getUserOption(userOption);

                // Display Options
                System.out.println(searchDate);
                System.out.println(userOption);
            } else {
                 createMessage("Search Error", "Please select a statistic option", AlertType.ERROR);
            }
        } else {
            createMessage("Search Error", "Please select a time period", AlertType.ERROR);
        }
    }

    @FXML
    void getEventStats(ActionEvent event) {
        if (eventStatsToggle.getSelectedToggle() != null) {
            if (eventOption != null) {
                // Get Time Period
                getSearchDate(eventStatsToggle);

                // Get Search Option
                getEventOption(eventOption);

                // Display Options
                System.out.println(searchDate);
                System.out.println(eventOption);
            } else {
                createMessage("Search Error", "Please select a statistics option", AlertType.ERROR);
            }
        } else {
            createMessage("Search Error", "Please select a time period", AlertType.ERROR);
        }
    }

    private void getSearchDate(ToggleGroup _group) {
        // Get Button Option
        RadioButton button = (RadioButton) _group.getSelectedToggle();

        // If button is selected, get Time Period
        if (button != null) {
            String time = button.getText();
            switch(time) {
                case "All Time":    searchDate = LocalDate.ofEpochDay(0);         break;
                case "Last Year":   searchDate = LocalDate.now().minusYears(1);   break;
                case "Last Month":  searchDate = LocalDate.now().minusMonths(1);  break;
                case "Last Week":   searchDate = LocalDate.now().minusWeeks(1);   break;
                case "Last Day":    searchDate = LocalDate.now().minusDays(1);    break;
                default: break;
            }
        }
    }

    private void getEventOption(String _option) {
        // If option is selected, get user option
        if (_option != null) {
            switch(_option) {
                case "Most Used Location":          mostUsedLocation(true);     break;
                case "Least Used Location":         mostUsedLocation(false);     break;
                case "Most Attended Event":         mostAttendedEvent(true);    break;
                case "Least Attended Event":        mostAttendedEvent(false);   break;
                case "Event with Most Sessions":    mostSessions(true);         break;
                case "Event with Least Sessions":   mostSessions(false);        break;
            }
        }
    }

    private void getUserOption(String _option) {
        // If option is selected, get user option
        if (_option != null) {
            switch(_option) {
                case "Most Events Created":     mostEventsCreated(true);    break;
                case "Least Events Created":    mostEventsCreated(false);   break;
                case "Most Events Attended":    mostEventsAttended(true);   break;
                case "Least Events Attended":   mostEventsAttended(false);  break;
            }
        }
    }

    private void mostEventsCreated(boolean _option) {
        // Set up results array
        ArrayList<String> list = new ArrayList<String>();

        // Get Results
        for (Account acc : manager.getTotalAccounts().values()) {
            if (!acc.getUsername().equals("admin")) {
                User user = (User) acc;
                int count = 0;
                for (Event event : manager.getTotalEvents().values()) {
                    if (event.getHost().getFullname().equals(user.getFullname())) {
                        count++;
                    }
                }
                list.add(user.getFullname() + ": " + count);
            }
        }

        // Sort in ascending or descending order
        if (!_option) {
            Collections.sort(list);
        } else {
            Collections.sort(list, Collections.reverseOrder());
        }

        // Add results to TextArea
        ObservableList<String> data = FXCollections.observableArrayList(list);
        userStatsList.setItems(data);
    }

    private void mostEventsAttended(boolean _option) {
        // Set up results array
        ArrayList<String> list = new ArrayList<String>();

        // Get Results
        for (Account acc : manager.getTotalAccounts().values()) {
            if (!acc.getUsername().equals("admin")) {
                User user = (User) acc;
                list.add(user.getFullname() + ": " + user.getTotalBookings().size());
            }
        }

        // Sort in ascending or descending order
        if (!_option) {
            Collections.sort(list);
        } else {
            Collections.sort(list, Collections.reverseOrder());
        }

        // // Add data to list
        ObservableList<String> data = FXCollections.observableArrayList(list);
        userStatsList.setItems(data);    
    }

    public void mostUsedLocation(boolean _option) {
        // Set up results array
        ArrayList<String> list = new ArrayList<String>();

        // Get Initial Locations
        for (Event event : manager.getTotalEvents().values()) {
            if (!list.contains(event.getLocation())) {
                list.add(event.getLocation());
            }
        } 

        // Get Total Results
        for (String str : list) {
            int count = 0;
            for (Event event : manager.getTotalEvents().values()) {
                if (str.equals(event.getLocation())) {
                    count++;
                }
            }
            int index = list.indexOf(str);
            list.set(index, list.get(index) + ": " + count);
        }

        // Sort in ascending or descending order
        if (_option) {
            Collections.sort(list);
        } else {
            Collections.sort(list, Collections.reverseOrder());
        }

        // // Add data to list
        ObservableList<String> data = FXCollections.observableArrayList(list);
        eventStatsList.setItems(data);
    }

    public void mostAttendedEvent(boolean _option) {
        // Set up results array
        ArrayList<String> list = new ArrayList<String>();

        // Get Results
        for (Event event : manager.getTotalEvents().values()) {
            int count = 0;

            for (Session session : event.getTotalSessions().values()) {
                count += session.getCurCapacity();
            }

            list.add(event.getTitle() + ": " + count);
        }

        // Sort in ascending or descending order
        if (_option) {
            Collections.sort(list);
        } else {
            Collections.sort(list, Collections.reverseOrder());
        }

        // // Add data to list
        ObservableList<String> data = FXCollections.observableArrayList(list);
        eventStatsList.setItems(data);
    }

    public void mostSessions(boolean _option) {
        // Set up results array
        ArrayList<String> list = new ArrayList<String>();

        // Get Results
        for (Event event : manager.getTotalEvents().values()) {
                list.add(event.getTitle() + ": " + event.getTotalSessions().size());
            }

        // Sort in ascending or descending order
        if (_option) {
            Collections.sort(list);
        } else {
            Collections.sort(list, Collections.reverseOrder());
        }

        // // Add data to list
        ObservableList<String> data = FXCollections.observableArrayList(list);
        eventStatsList.setItems(data);    
    }

    @FXML
    void exitWindow(ActionEvent event) {
        // Close Current Window
        closeWindow(event);
    }
}