import javafx.fxml.LoadException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import javafx.event.ActionEvent;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.Optional;

public class WindowController {
	// Create Event Manager
	EventManager manager;

	// Create a message
	public static int createMessage(String _title, String _text, AlertType _type) {
		// Create Alert Message
		Alert alert = new Alert(_type);
		alert.setTitle(_title);
		alert.setHeaderText(null);
		alert.setContentText(_text);

		// Check if confirmation type
		if (_type == AlertType.CONFIRMATION) {
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				// Return Confirm
				return 1;
			} else {
				// Return Not Confirm
				return 0;
			}
		} else {
			// Set default close
			alert.showAndWait();
		}
		// Return
		return -1;
	}

	// Create A New Window
	public void openWindow(String _filename, String _title) {
		// Create a new stage
		Stage stage = new Stage();

		// Attempt to create stage
		try {
			Parent root = FXMLLoader.load(getClass().getResource(_filename));
			Scene scene = new Scene(root);
			stage.setTitle(_title);
			stage.setScene(scene);
			stage.show();

			// Add Case-Specific Close Operations
			if (_title.equals("Login")) {
				stage.setOnCloseRequest(e -> {
					int option = WindowController.createMessage("Exit Program", "Exit the Program?", AlertType.CONFIRMATION);
					if (option == 1) {
						System.exit(0);
					} else {
						e.consume();
					}
				});
			}

		} catch (Exception e) {
			// Display Exception
			createMessage("Exception Found", "Exception: " + e.toString(), AlertType.ERROR);
		}
	}

	// Close a Window
	public void closeWindow(ActionEvent _event) {
		// Close Current Stage
		Node source = (Node) _event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
}