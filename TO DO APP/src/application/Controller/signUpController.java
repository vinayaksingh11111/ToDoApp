package application.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.Database.DatabaseHandler;
import application.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class signUpController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXTextField firstName;

	@FXML
	private JFXTextField lastName;

	@FXML
	private JFXTextField userName;

	@FXML
	private JFXTextField locationField;

	@FXML
	private JFXPasswordField password;

	@FXML
	private JFXCheckBox maleBox;

	@FXML
	private JFXCheckBox femaleBox;

	@FXML
	private JFXButton signUpButton;

	@FXML
	void initialize() {
		signUpButton.setOnAction(event -> {
			String firstname = firstName.getText().trim();
			String lastname = lastName.getText().trim();
			String username = userName.getText().trim();
			String pass = password.getText().trim();
			String loc = locationField.getText().trim();
			String gender = "";
			if (femaleBox.isSelected()) {
				gender = "Female";
			} else {
				gender = "Male";
			}
			User user = new User(firstname, lastname, username, pass, loc, gender);
			DatabaseHandler handler = new DatabaseHandler();
			handler.signUpUser(user);
			signUpButton.getScene().getWindow().hide();
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/application/view/LOGIN.fxml"));
			try {
				fxmlLoader.setRoot(fxmlLoader.getRoot());
				fxmlLoader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Parent root = fxmlLoader.getRoot();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.showAndWait();

		});
	}
}
