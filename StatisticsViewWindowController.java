import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.shape.Rectangle;

import javafx.event.ActionEvent;

public class StatisticsViewWindowController {

    @FXML
    private Rectangle rectBackground;

    @FXML
    private RadioButton userMonthRadio;

    @FXML
    private Rectangle rectBackground1;

    @FXML
    private Label titleLabel;

    @FXML
    private ComboBox<String> userStatsCombo;

    @FXML
    private Button cancelButton1;

    @FXML
    private RadioButton userYearRadio;

    @FXML
    private RadioButton eventMonthRadio;

    @FXML
    private RadioButton userAllRadio;

    @FXML
    private RadioButton eventDayRadio;

    @FXML
    private ListView<?> userStatsList;

    @FXML
    private RadioButton userWeekRadio;

    @FXML
    private RadioButton eventWeekRadio;

    @FXML
    private Button cancelButton;

    @FXML
    private ListView<String> eventsStatsList;

    @FXML
    private ComboBox<String> eventsStatsCombo;

    @FXML
    private Label titleLabel1;

    @FXML
    private RadioButton eventYearRadio;

    @FXML
    private RadioButton userDayRadio;

    @FXML
    private RadioButton eventAllRadio;

    @FXML
    void exitWindow(ActionEvent event) {

    }
}