package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListCell;

import application.Database.DatabaseHandler;
import application.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CellController extends JFXListCell<Task> {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private Label task;

	@FXML
	private Label description;

	@FXML
	private ImageView delete;

	@FXML
	private ImageView update;

	@FXML
	private Label date;

	private FXMLLoader fxmlLoader;

	DatabaseHandler databasehandler = new DatabaseHandler();

	@FXML
	void initialize() {

	}

	@Override
	protected void updateItem(Task myTask, boolean empty) {
		super.updateItem(myTask, empty);
		if (empty || myTask == null) {
			setText(null);
			setGraphic(null);
		} else {
			if (fxmlLoader == null) {
				fxmlLoader = new FXMLLoader(getClass().getResource("/application/view/cell.fxml"));
				fxmlLoader.setController(this);
				try {
					fxmlLoader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			task.setText(myTask.getTask());
			description.setText(myTask.getDescription());
			date.setText(myTask.getDateCreated().toString());

			update.setOnMouseClicked(event -> {

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/application/view/updateTask.fxml"));

				try {
					loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Parent root = loader.getRoot();
				Stage stage = new Stage();
				stage.setScene(new Scene(root));

				UpdateTaskController updateTaskController = loader.getController();
				updateTaskController.setTaskField(myTask.getTask());
				updateTaskController.setUpdateDescriptionField(myTask.getDescription());

				updateTaskController.updateTaskButton.setOnAction(event1 -> {

					Calendar calendar = Calendar.getInstance();

					java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());

					try {

						System.out.println("taskid " + myTask.getTaskId());

						databasehandler.updateTask(timestamp, updateTaskController.getDescription(),
								updateTaskController.getTask(), myTask.getTaskId());

					} catch (SQLException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

				});

				stage.show();

			});

			delete.setOnMouseClicked(event -> {
				getListView().getItems().remove(getItem());
				DatabaseHandler databaseHandler = new DatabaseHandler();
				try {
					databaseHandler.deleteTaskId(addItemController.userid, myTask.getTaskId());
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			});
			setText(null);
			setGraphic(rootPane);
		}
	}
}
