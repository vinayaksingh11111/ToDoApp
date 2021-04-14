package application.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import application.Database.DatabaseHandler;
import application.model.Task;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class addItemController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane root;

	@FXML
	private JFXTextField taskField;

	@FXML
	private JFXTextArea descriptionField;

	@FXML
	private JFXButton saveTaskButton;

	@FXML
	private JFXButton myTask;

	@FXML
	private Label done;

	public static int userid = loginController.userId;

	DatabaseHandler handler = new DatabaseHandler();

	@FXML
	void initialize() {
		Task task = new Task();
		saveTaskButton.setOnAction(event -> {
			done.setVisible(false);
			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());
			task.setUserid(addItemController.userid);
			task.setDateCreated(timestamp);
			task.setDescription(descriptionField.getText().trim());
			task.setTask(taskField.getText().trim());
			taskField.setText("");
			descriptionField.setText("");
			handler.insertTask(task);
			done.setVisible(true);
			int taskno = 0;
			try {
				taskno = handler.getAllTasks(userid);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			myTask.setText("MY TASKS-> " + taskno);
			myTask.setVisible(true);
		});
		myTask.setOnAction(event -> {
			AnchorPane pane;
			try {
				pane = FXMLLoader.load(getClass().getResource("/application/view/todo.fxml"));
				FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), pane);
				fadeTransition.setFromValue(0f);
				fadeTransition.setToValue(1f);
				fadeTransition.setCycleCount(1);
				fadeTransition.setAutoReverse(false);
				fadeTransition.play();
				root.getChildren().setAll(pane);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}
