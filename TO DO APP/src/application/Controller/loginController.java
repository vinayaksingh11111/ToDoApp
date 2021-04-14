package application.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.Animations.Shaker;
import application.Database.DatabaseHandler;
import application.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class loginController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXButton signUpButton;

	@FXML
	private JFXTextField username;

	@FXML
	private JFXPasswordField password;

	@FXML
	private JFXButton loginButton;

	@FXML
	private Label incorrectLabel;

	DatabaseHandler handler = new DatabaseHandler();

	public static int userId;

	@FXML
	void initialize() {
		loginButton.setOnAction(event -> {
			String User = username.getText().trim();
			String pass = password.getText().trim();
			if (!User.equals("") && !pass.equals("")) {
				int count = 0;
				User user = new User();
				user.setUserName(User);
				user.setPassword(pass);
				ResultSet set = handler.isUser(user);
				try {
					while (set.next()) {
						count++;
						loginController.userId = set.getInt("userid");
					}
					if (count == 1) {
						loginButton.getScene().getWindow().hide();
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(getClass().getResource("/application/view/addItem.fxml"));
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
					} else {
						incorrectLabel.setText("INCORRECT CREDENTIALS");
						Shaker shaker = new Shaker(username);
						Shaker shake = new Shaker(password);
						shaker.Shake();
						shake.Shake();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				incorrectLabel.setText("!!Empty Username or Password Field!!");
			}
		});
		signUpButton.setOnAction(event -> {
			signUpButton.getScene().getWindow().hide();
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/application/view/signUp.fxml"));
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
