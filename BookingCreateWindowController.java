import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class BookingCreateWindowController extends WindowController {

    @FXML
    private Rectangle rectBackground;

    @FXML
    private Pane loginPane;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField fullnameField;

    @FXML
    private Label titleLabel;

    @FXML
    private Button createButton;

    @FXML
    private TextField fullnameField1;

    @FXML
    void exitWindow(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    void createProfile(ActionEvent event) {

    }
}