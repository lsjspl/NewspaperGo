package com.Master5.main.web.Catcher.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Master5.main.web.Catcher.dao.CatcherDao;
import com.Master5.main.web.Catcher.dao.UrlsInfoDao;
import com.Master5.main.web.Catcher.entry.UrlsInfo;

@Service
public class CatcherService {
	
	@Autowired
	CatcherDao catcherDao;
	
	@Autowired
	UrlsInfoDao urlsInfoDao;
	
	public List<UrlsInfo> queryUrlsInfo(){
		return urlsInfoDao.findAll();
	}

	public UrlsInfo queryOne(int id){
		return urlsInfoDao.findOne(id);
	}
	
	public UrlsInfo saveUrlsInfo(UrlsInfo urlsInfo) {
		urlsInfo.setCareatTime(Calendar.getInstance().getTime());
		return urlsInfoDao.saveAndFlush(urlsInfo);
	}
	
	
	
}
