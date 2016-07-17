package com.Master5.main.web.Catcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Master5.main.web.Catcher.dao.CatcherDao;
import com.Master5.main.web.Catcher.entry.UrlsInfo;

@Service
public class CatcherService {
	
	@Autowired
	CatcherDao catcherDao;
	
	@Autowired
	UrlsInfo urlsInfo;

}
