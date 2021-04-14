package application.model;

import java.sql.Timestamp;

public class Task {
	public Task() {

	}

	private Timestamp dateCreated;
	private String task;
	private String description;
	private int userid;
	private int taskId;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Task(Timestamp dateCreated, String task, String description) {
		this.dateCreated = dateCreated;
		this.task = task;
		this.description = description;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
