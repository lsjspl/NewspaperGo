package com.Master5.main.web.Catcher.entry;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "catcher")
public class Catcher {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	@Column(name = "content", nullable = true)
	private String content;

	private String title;

	private Date time;

	private Date creatTime;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	@Column(name = "baseInfo", nullable = true)
	private String baseInfo;

	private int urlId;

	private int state;
	
	private int taskId;
	
	private String url;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(String baseInfo) {
		this.baseInfo = baseInfo;
	}

	public int getUrlId() {
		return urlId;
	}

	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}

	@Override
	public String toString() {
		return "Catcher [id=" + id + ", content=" + content + ", title=" + title + ", time=" + time + ", creatTime="
				+ creatTime + ", baseInfo=" + baseInfo + ", urlId=" + urlId + ", state=" + state + ", url=" + url + "]";
	}

}
