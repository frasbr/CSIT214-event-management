import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

public class EventCreateWindowController extends WindowController {

    @FXML
    private TextField locationField;

    @FXML
    private Rectangle rectBackground1;

    @FXML
    private Label titleLabel;

    @FXML
    private ListView<?> eventsList;

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
    private ListView<?> sessionsList;

    @FXML
    private ListView<?> eventsList1;

    @FXML
    private Rectangle rectBackground11;

    @FXML
    void editEvent(ActionEvent event) {

    }

    @FXML
    void deleteEvent(ActionEvent event) {

    }

    @FXML
    void editSession(ActionEvent event) {

    }

    @FXML
    void deleteSession(ActionEvent event) {

    }


    @FXML
    void exitWindow(ActionEvent event) {

    }
}
