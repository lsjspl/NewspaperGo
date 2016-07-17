package com.Master5.main.web.Catcher.entry;

import java.util.Date;

public class Catcher {

	private int id;

	private String content;

	private String title;

	private Date time;

	private Date creatTime;

	private byte[] baseInfo;

	private int urlId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public byte[] getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(byte[] baseInfo) {
		this.baseInfo = baseInfo;
	}

	public int getUrlId() {
		return urlId;
	}

	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}

}
