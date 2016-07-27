package com.Master5.main.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Repository;

import com.Master5.main.utils.constant.TaskStatus;
import com.Master5.main.web.Catcher.dao.CatcherDao;
import com.Master5.main.web.Catcher.dao.CatcherTaskDao;
import com.Master5.main.web.Catcher.dao.TaskLogDao;
import com.Master5.main.web.Catcher.dao.UrlsInfoDao;
import com.Master5.main.web.Catcher.entry.CatcherTask;
import com.Master5.main.web.Catcher.service.CatcherTaskService;

@SuppressWarnings("rawtypes")
@Repository
public class initApp implements ApplicationListener {

	private static boolean isStart = false;

	@Autowired
	CatcherTaskDao catcherTaskDao;

	@Autowired
	CatcherDao catcherDao;

	@Autowired
	UrlsInfoDao urlsInfoDao;

	@Autowired
	TaskLogDao taskLogDao;

	@Autowired
	CatcherTaskService catcherTaskService;

	public void onApplicationEvent(ApplicationEvent arg0) {
		if (!isStart) {// 这个可以解决项目启动加载两次的问题
			isStart = true;

			new Thread(new Runnable() {
				public void run() {
					catcherTaskService.start();
				}
			}).start();

		}
	}

}