import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Rectangle;
import java.util.Optional;

import javafx.fxml.LoadException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.time.LocalDate;

import java.io.IOException;

public class UserWindowController {

    EventManager manager;

    @FXML
    private Rectangle rectBackground;

    @FXML
    private Pane loginPane;

    @FXML
    private Button exitButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Button bookButton;

    @FXML
    private Button createButton;

    @FXML
    private TextField dateField;

    @FXML
    private Button manageButton;

    @FXML
    private Button profileButton;
    
    @FXML
    void createEvent(ActionEvent event) {
        createWindow("EventCreate");
    }

    @FXML
    void manageEvents(ActionEvent event) {
        //createWindow("ManageEvent");
    }

    @FXML
    void bookEvent(ActionEvent event) {
        createWindow("EventSearch");
    }

    @FXML
    void manageProfile(ActionEvent event) {
        //createWindow("ManageProfile")
    }

    @FXML
    void exitWindow(ActionEvent event) {
        // Close current stage
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        // Perform logout
        manager.performLogout();

        // Create Login Window
        createWindow("Login");
    }

    public void createWindow(String type) {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML Files/" + type + "Window.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle(type);
            stage.setScene(scene);
            stage.show();

            if (type.equals("Login")) {
                stage.setOnCloseRequest(e -> {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Exit Program");
                alert.setHeaderText(null);
                alert.setContentText("Exit the Program?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    System.exit(0);
                } else {
                    e.consume();
                }
            });
            }         
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    public void initialize() {
        // Set Date of DateField
        dateField.setText(LocalDate.now().toString()); 
    }
}