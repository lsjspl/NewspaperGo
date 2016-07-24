package com.Master5.main.web.Catcher.entry;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringEscapeUtils;

@Entity
@Table(name = "urlsInfo")
public class UrlsInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String urls;

	private String name;
	
	private String keyWord;

	private Date creatTime;

	private int type;
	
	private int state;
	
	private String titlePattern;
	
	private String areaPattern;
	
	private String urlPattern;
	
	private String otherPattern;
	
	private String contentPattern;
	

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getTitlePattern() {
		return titlePattern;
	}

	public void setTitlePattern(String titlePattern) {
		this.titlePattern = titlePattern;
	}

	public String getAreaPattern() {
		return areaPattern;
	}

	public void setAreaPattern(String areaPattern) {
		this.areaPattern = areaPattern;
	}

	public String getUrlPattern() {
		return urlPattern;
	}

	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

	public String getOtherPattern() {
		return otherPattern;
	}

	public void setOtherPattern(String otherPattern) {
		this.otherPattern = otherPattern;
	}

	public String getContentPattern() {
		return contentPattern;
	}

	public void setContentPattern(String contentPattern) {
		this.contentPattern = contentPattern;
	}

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


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}


}
