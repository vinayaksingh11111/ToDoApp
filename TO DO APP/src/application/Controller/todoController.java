package application.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import application.Database.DatabaseHandler;
import application.model.Task;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class todoController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ImageView listRefreshButton;

	@FXML
	private JFXTextField taskField;

	@FXML
	private JFXTextArea descriptionField;

	@FXML
	private JFXButton saveTask;

	@FXML
	private JFXListView<Task> list;

	ObservableList<Task> tasks;
	private ObservableList<Task> refreshedTasks;

	@FXML
	void initialize() throws SQLException, ClassNotFoundException {
		tasks = FXCollections.observableArrayList();
		DatabaseHandler databaseHandler = new DatabaseHandler();
		ResultSet set = databaseHandler.getAllTask(addItemController.userid);
		while (set.next()) {
			Task myTask = new Task();
			myTask.setTaskId(set.getInt("taskid"));
			myTask.setTask(set.getString("task"));
			myTask.setDateCreated(set.getTimestamp("datecreated"));
			myTask.setDescription(set.getString("description"));
			tasks.addAll(myTask);
		}
		list.setItems(tasks);
		list.setCellFactory(CellController -> new CellController());
		saveTask.setOnAction(event -> {
			addNewTask();
		});

		listRefreshButton.setOnMouseClicked(event -> {
			try {
				refreshList();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});

	}

	private void addNewTask() {
		Task task = new Task();
		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());
		task.setUserid(addItemController.userid);
		task.setDateCreated(timestamp);
		task.setDescription(descriptionField.getText().trim());
		task.setTask(taskField.getText().trim());
		DatabaseHandler handler = new DatabaseHandler();
		handler.insertTask(task);
		try {
			initialize();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		taskField.setText("");
		descriptionField.setText("");
	}

	public void refreshList() throws SQLException, ClassNotFoundException {

		System.out.println("refreshList in ListCont called");
		refreshedTasks = FXCollections.observableArrayList();
		DatabaseHandler databaseHandler = new DatabaseHandler();
		ResultSet resultSet = databaseHandler.getAllTask(addItemController.userid);
		while (resultSet.next()) {
			Task task = new Task();
			task.setTaskId(resultSet.getInt("taskid"));
			task.setTask(resultSet.getString("task"));
			task.setDateCreated(resultSet.getTimestamp("datecreated"));
			task.setDescription(resultSet.getString("description"));
			refreshedTasks.addAll(task);
		}
		list.setItems(refreshedTasks);
		list.setCellFactory(CellController -> new CellController());
	}

}
