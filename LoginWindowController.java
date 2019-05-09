import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Rectangle;
import java.util.Optional;

public class LoginWindowController {

    @FXML
    private TextField usernameField;

    @FXML
    private Rectangle rectBackground;

    @FXML
    private Pane loginPane;

    @FXML
    private ImageView imageLogo;

    @FXML
    private Label titleLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button quitButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button profileButton;

    @FXML
    void performLogin(ActionEvent event) {
        // Alert alert = new Alert(AlertType.INFORMATION);
        // alert.setTitle("Information Dialog");
        // alert.setHeaderText(null);
        // alert.setContentText("I have a great message for you!");

        // alert.showAndWait();
    }

    @FXML
    void exitProgram(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit Program");
        alert.setHeaderText(null);
        alert.setContentText("Exit the Program?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    @FXML
    void createProfile(ActionEvent event) {

    }
}