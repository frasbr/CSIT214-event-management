import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.Rectangle;

import javafx.scene.control.Alert.AlertType;

import java.util.HashMap;

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
        System.out.println("Got User Stats!");

        // Get Time Period
        getSearchDate(userStatsToggle);

        // Get Search Option
        getUserOption(userOption);

        // Display Options
        System.out.println(searchDate);
        System.out.println(userOption);
    }

    @FXML
    void getEventStats(ActionEvent event) {
        // Get Time Period
        getSearchDate(eventStatsToggle);

        // Get Search Option
        getEventOption(eventOption);

        // Display Options
        System.out.println(searchDate);
        System.out.println(eventOption);
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
        } else {
            createMessage("Search Error", "Please select a time period", AlertType.ERROR);
        }
    }

    private void getUserOption(String _option) {
        // If option is selected, get user option
        if (_option != null) {
            switch(_option) {
                case "Most Events Created":  mostEventsCreated(true); break;
                case "Least Events Created":        break;
                case "Most Events Attended":        break;
                case "Least Events Attended":       break;
            }
        } else {
            createMessage("Search Error", "Please select a statistic option", AlertType.ERROR);
        }
    }

    private void mostEventsCreated(boolean _option) {
        // Set up results hashmap
        // HashMap<String, Integer> results = new HashMap<String, Integer>();

        // // Get Results
        // for (Event event : manager.getTotalEvents().values()) {
        //     if (results.containsKey(event.getHost().getFullname())) {
        //         results.put(event.getHost().getFullname(), results.get(event.getHost().getFullname()) + 1);
        //     } else {
        //         results.put(event.getHost().getFullname(), 1);
        //     }
        // }

        // // Add results to TextArea
        // // ObservableList<String> data = FXCollections.observableArrayList(results.getValues());
        // // sessionsList.setItems(data);
    }

    private void getEventOption(String _option) {
        // If option is selected, get user option
        if (_option != null) {
            switch(_option) {
                case "Most Used Location":          break;    
                case "Least Used Location":         break;
                case "Most Attended Event":         break;
                case "Least Attended Event":        break;
                case "Event with Most Sessions":    break;
                case "Event with Least Sessions":   break;
            }
        } else {
            createMessage("Search Error", "Please select a statistic option", AlertType.ERROR);
        }
    }

    @FXML
    void exitWindow(ActionEvent event) {
        // Close Current Window
        closeWindow(event);
    }
}
