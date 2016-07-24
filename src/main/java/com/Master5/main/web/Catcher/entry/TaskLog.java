package com.Master5.main.web.Catcher.entry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "taskLog")
public class TaskLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int state;

	private int type;

	private String url;
	
	private int urlsId;
	
	private int taskId;
	
	private int catcherId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getUrlsId() {
		return urlsId;
	}

	public void setUrlsId(int urlsId) {
		this.urlsId = urlsId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getCatcherId() {
		return catcherId;
	}

	public void setCatcherId(int catcherId) {
		this.catcherId = catcherId;
	}
	
	

}
