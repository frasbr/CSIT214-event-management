import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class EventSearchController {

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

    }

    @FXML
    void createProfile(ActionEvent event) {

    }

    @FXML
    void exitProgram(ActionEvent event) {

    }
}