package application.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import application.model.Task;
import application.model.User;

public class DatabaseHandler extends Configs {
	Connection dbConnection;

	public Connection getDbConnection() throws ClassNotFoundException, SQLException {
		String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
		Class.forName("com.mysql.jdbc.Driver");
		dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
		return dbConnection;
	}

	public void signUpUser(User user) {
		String insert = "INSERT INTO " + Const.USERS_TABLE + "(" + Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME
				+ "," + Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + "," + Const.USERS_LOCATION + ","
				+ Const.USERS_GENDER + ")" + "VALUES(?,?,?,?,?,?)";
		try {
			PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getUserName());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getLocation());
			preparedStatement.setString(6, user.getGender());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertTask(Task task) {
		String insert = "INSERT INTO " + Const.TASKS_TABLE + "(" + Const.TASKS_USER_ID + "," + Const.TASKS_DATE + ","
				+ Const.TASKS_TASK + "," + Const.TASKS_DESCRIPTION + ")" + "VALUES(?,?,?,?)";
		try {
			PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
			preparedStatement.setInt(1, task.getUserid());
			preparedStatement.setTimestamp(2, task.getDateCreated());
			preparedStatement.setString(3, task.getTask());
			preparedStatement.setString(4, task.getDescription());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet isUser(User user) {
		ResultSet set = null;
		String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " + Const.USERS_USERNAME + "=? AND "
				+ Const.USERS_PASSWORD + "=?";
		try {
			PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassword());
			set = preparedStatement.executeQuery();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}

	public int getAllTasks(int userid) throws ClassNotFoundException, SQLException {
		String query = "SELECT COUNT(*) FROM " + Const.TASKS_TABLE + " WHERE " + Const.TASKS_USER_ID + "=?";
		PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
		preparedStatement.setInt(1, userid);
		ResultSet set = preparedStatement.executeQuery();
		if (set.next()) {
			return set.getInt(1);
		}
		return 0;
	}

	public ResultSet getAllTask(int n) throws ClassNotFoundException, SQLException {
		ResultSet set = null;
		String query = "SELECT * FROM " + Const.TASKS_TABLE + " WHERE " + Const.USERS_ID + "=?";
		PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
		try {
			preparedStatement.setInt(1, n);
			set = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}

	public void deleteTaskId(int userid, int taskid) throws ClassNotFoundException, SQLException {
		String query = "DELETE FROM " + Const.TASKS_TABLE + " WHERE " + Const.USERS_ID + "=?" + " AND " + Const.TASKS_ID
				+ "=?";
		PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
		preparedStatement.setInt(1, userid);
		preparedStatement.setInt(2, taskid);
		preparedStatement.execute();
		preparedStatement.close();
	}

	public void updateTask(Timestamp datecreated, String description, String task, int taskId)
			throws SQLException, ClassNotFoundException {

		String query = "UPDATE tasks SET datecreated=?, description=?, task=? WHERE taskid=?";

		PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
		preparedStatement.setTimestamp(1, datecreated);
		preparedStatement.setString(2, description);
		preparedStatement.setString(3, task);
		// preparedStatement.setInt(4, userId);
		preparedStatement.setInt(4, taskId);
		preparedStatement.executeUpdate();
		preparedStatement.close();

	}

}
