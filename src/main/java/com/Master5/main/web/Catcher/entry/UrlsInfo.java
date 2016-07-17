package com.Master5.main.web.Catcher.entry;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "urlsInfo")
public class UrlsInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String urls;

	private String name;

	private Date careatTime;

	private int type;
	
	private int state;
	

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCareatTime() {
		return careatTime;
	}

	public void setCareatTime(Date careatTime) {
		this.careatTime = careatTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	

}
