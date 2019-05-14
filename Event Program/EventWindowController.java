import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.fxml.LoadException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class EventWindowController {

    EventManager manager;

    @FXML
    private TextField codeField;

    @FXML
    private TextField locationField;

    @FXML
    private Rectangle rectBackground;

    @FXML
    private Rectangle rectBackground1;

    @FXML
    private Label titleLabel;

    @FXML
    private ListView<String> eventsList = new ListView<String>();

    @FXML
    private TextField titleField;

    @FXML
    private TextField timeField;

    @FXML
    private Button createSessionButton;

    @FXML
    private TextField capacityField;

    @FXML
    private TextField dateField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField priceField;

    @FXML
    private Label titleLabel1;

    @FXML
    private Button createEventButton;

    @FXML
    void createEvent(ActionEvent event) {
         // Set up Error Handler
        Alert alert;

        // Get Data
        String inputTitle = titleField.getText();
        String inputLocation = locationField.getText();
        String inputDesc = descriptionArea.getText();
        
        // Check fields are filled
        if (inputTitle.equals("") || inputLocation.equals("") || inputDesc.equals("") ) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All Relevant Fields");

            alert.showAndWait();
        } else {
            try {
                // Add Event
                if (manager.getEvent(inputTitle) != null) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Event Creation Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Event with this title already exists.");

                    alert.showAndWait();
                } else {
                    User host = (User) manager.getLoggedAccount();
                    manager.createEvent(inputTitle, inputLocation, host);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Event Creation Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Event was successfully created");

                    alert.showAndWait();

                    titleField.setText("");
                    locationField.setText("");
                    descriptionArea.setText("");
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    @FXML
    void createSession(ActionEvent event) {
        // String inputDate = dateField.getText();
        // String inputCapacity = capacityField.getText();
        // String inputPrice = priceField.getText();

        // if (inputDate.equals("") || inputCapacity.equals("") || inputPrice.equals("")))

        /*
        // Get Price and Capacity
        double price = Double.parseDouble(inputPrice);
        int capacity = Integer.parseInt(inputCapacity);

        // Get Date
        String[] dateComponents = inputDate.split("/");
        int day = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]);
        int year = Integer.parseInt(dateComponents[2]);

        dateField.setText("");
        priceField.setText("");
        capacityField.setText("");

        // Check Date
            int count = inputDate.length() - inputDate.replace("/", "").length();
            if (count != 2) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Date given is incorrectly formatted. Please do the following: dd/mm/yyyy");
                    
                alert.showAndWait();
            } else {

        // Event newEvent = manager.getEvent(inputTitle);
        // newEvent.addSession(day, month, year, 10, 10, price, capacity);
        */
    }

    @FXML
    void exitWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    public void initialize() {
       ArrayList<String> list = new ArrayList<String>();

       User currentUser = (User) manager.getLoggedAccount();

       for (Event event : manager.getTotalEvents().values()) {
            if (event.getHost().getFullname().equals(currentUser.getFullname())) {
                list.add(event.getTitle() + " - " + event.getLocation());
            }
       }

        ObservableList<String> data = FXCollections.observableArrayList(list);

        eventsList.setItems(data);
    }
}