package com.Master5.main.web.Catcher.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Master5.main.utils.Tools;
import com.Master5.main.utils.constant.TaskStatus;
import com.Master5.main.web.Catcher.dao.CatcherDao;
import com.Master5.main.web.Catcher.dao.CatcherTaskDao;
import com.Master5.main.web.Catcher.dao.TaskLogDao;
import com.Master5.main.web.Catcher.dao.UrlsInfoDao;
import com.Master5.main.web.Catcher.entry.Catcher;
import com.Master5.main.web.Catcher.entry.CatcherTask;
import com.Master5.main.web.Catcher.entry.TaskLog;
import com.Master5.main.web.Catcher.entry.UrlsInfo;

@Service
public class CatcherTaskService {

	@Autowired
	CatcherTaskDao catcherTaskDao;

	@Autowired
	CatcherDao catcherDao;

	@Autowired
	UrlsInfoDao urlsInfoDao;

	@Autowired
	TaskLogDao taskLogDao;

	// 建立一个阻塞队列
	private static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(10);

	public static LinkedBlockingQueue<Integer> getCatcherQueue() {
		return queue;
	}

	public static void put(Integer taskId) {
		try {
			queue.put(taskId);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class Consumer extends Thread {
		public void run() {
			CatcherTask task = null;
			while (true) {
				try {
					task = catcherTaskDao.findOne((Integer) queue.take());

					// 如果任务不是正在准备状态 清空爬取的信息
					if (task.getState() != TaskStatus.WAIT) {
						catcherDao.deleteByTaskId(task.getId());
					}
					task.setState(TaskStatus.WORKING);
					catcherTaskDao.save(task);

					addTaskLog(TASK_START, 0, task.getId(), 0, "", "任务开启：" + task.getName());

					catcherWork(null, task.getStartDate(), task.getEndDate(), task);

					addTaskLog(TASK_FINISH, 0, task.getId(), 0, "", "任务完成：" + task.getName());
					task.setState(TaskStatus.FINISH);
					catcherTaskDao.save(task);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("任务失败：" + task.getName());
					if (task != null) {
						// 标记爬取失败
						task.setState(TaskStatus.FAILED);
						task.setRemarks(e.getMessage());
						catcherTaskDao.save(task);
					}
					continue;
				}
			}
		}
	}

	public void catcherWork(String[] urls, Date startDate, Date endDate, CatcherTask task) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);

		while (calendar.getTimeInMillis() <= endDate.getTime()) {
			catcherWork(urls, calendar.getTime(), task);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}

	private static final int TASK_START = 0;
	private static final int TASK_FINISH = 1;
	private static final int TASK_SUCCESS_MAIN = 2;
	private static final int TASK_FAILED_MAIN = 3;

	public void catcherWork(String[] urlsList, Date date, CatcherTask task) {

		Document htmlDoc;

		String urlsInfoIds = task.getUrlsInfoIds();
		
		List<UrlsInfo> urlsInfoList;
		
		if (urlsInfoIds != null && !urlsInfoIds.equals("")) {
			
			List<Integer> urlsIdsList=new ArrayList<>();
			String[] tmps=urlsInfoIds.split(",");
			for(String tmp:tmps){
				urlsIdsList.add(Integer.parseInt(tmp));
			}
			
			//别问我为什么这么拼第二个参数 过度封装的东西真让人恶心
			urlsInfoList = urlsInfoDao.findByStateAndIdIn(0, urlsIdsList);
		} else {
			urlsInfoList = urlsInfoDao.findByState(0);
		}

		Catcher catcher = new Catcher();

		for (UrlsInfo urlsInfo : urlsInfoList) {

			String urls = urlsInfo.getUrls();

			try {
				urls = Tools.saxUrlForDate(urls, date);
				htmlDoc = Jsoup.connect(urls)
						.userAgent(
								"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
						.timeout(50000).get();
			} catch (Exception e1) {
				addTaskLog(TASK_FAILED_MAIN, 0, task.getId(), urlsInfo.getId(), urls, "首页");
				System.out.println("解析首页失败：" + urls);
				continue;
			}

			String titlePattern = urlsInfo.getTitlePattern();
			String contentPattern = urlsInfo.getContentPattern();
			String areaPattern = urlsInfo.getAreaPattern();

			Elements areas = htmlDoc.select(StringUtils.isEmpty(areaPattern) ? "area" : areaPattern);

			if (areas.size() == 0) {
				addTaskLog(TASK_FAILED_MAIN, 0, task.getId(), urlsInfo.getId(), urls, "首页");
				System.out.println("没有找到相关的信息");
				continue;
			}

			addTaskLog(TASK_SUCCESS_MAIN, 0, task.getId(), urlsInfo.getId(), urls, "首页");

			for (Element element : areas) {

				String childUrl;
				if (element.hasAttr("url")) {
					childUrl = element.attr("url");
				} else if (element.hasAttr("href")) {
					childUrl = element.attr("href");
				} else {
					addTaskLog(TASK_FAILED_MAIN, 0, task.getId(), urlsInfo.getId(), urls, "内页没有相关的属性");
					System.out.println("内页没有相关的属性");
					continue;
				}

				if (childUrl.startsWith("/")) {
					childUrl = urls.substring(0, urls.indexOf("/", urls.indexOf("://") + 3)) + childUrl;
				} else if (childUrl.startsWith("http://") || childUrl.startsWith("https://")) {
				} else {
					childUrl = urls.substring(0, urls.lastIndexOf("/") + 1) + childUrl;
				}

				try {
					Document childDoc = Jsoup.connect(childUrl)
							.userAgent(
									"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
							.timeout(50000).get();
					catcher.setId(null);
					catcher.setContent(childDoc.select(StringUtils.isEmpty(contentPattern) ? "body"
							: StringEscapeUtils.unescapeHtml(contentPattern)).text());
					catcher.setTitle(childDoc.select(
							StringUtils.isEmpty(titlePattern) ? "title" : StringEscapeUtils.unescapeHtml(titlePattern))
							.text());
					catcher.setCreatTime(Calendar.getInstance().getTime());
					catcher.setBaseInfo(StringEscapeUtils.escapeHtml(childDoc.html()));
					catcher.setUrlId(urlsInfo.getId());
					catcher.setTime(date);
					catcher.setUrl(childUrl);
					catcher.setTaskId(task.getId());
					catcher = catcherDao.save(catcher);

					addTaskLog(TASK_SUCCESS_MAIN, catcher.getId(), task.getId(), urlsInfo.getId(), childUrl, "内页");

					System.out.println("解析并保存成功：" + childUrl);
				} catch (IOException e) {
					addTaskLog(TASK_FAILED_MAIN, 0, task.getId(), urlsInfo.getId(), childUrl, "内页");
					System.out.println("解析内页失败：" + urls);
					continue;
				}

			}

		}
	}

	void addTaskLog(int type, int catcherId, int taskId, int urlsId, String url, String remarks) {
		TaskLog taskLog = new TaskLog();
		taskLog.setCatcherId(catcherId);
		taskLog.setState(0);
		taskLog.setTaskId(taskId);
		taskLog.setUrl(url);
		taskLog.setUrlsId(urlsId);
		taskLog.setType(type);
		taskLog.setRemarks(remarks);
		taskLogDao.save(taskLog);
	}

	public void start() {
		try {
			List<Integer> taskStatus = new ArrayList<Integer>();
			taskStatus.add(TaskStatus.WAIT);
			taskStatus.add(TaskStatus.WORKING);
			List<CatcherTask> list = catcherTaskDao.findByStateIn(taskStatus);
			for (CatcherTask task : list) {

				int id = task.getId();
				taskLogDao.deleteByTaskId(id);
				catcherDao.deleteByTaskId(id);
				task.setState(TaskStatus.WAIT);
				catcherTaskDao.save(task);
				try {
					CatcherTaskService.getCatcherQueue().put(id);
				} catch (InterruptedException e) {
					continue;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("初始化任务数据失败");
			CatcherTaskService.getCatcherQueue().clear();
		}

		new Consumer().start();
	}
}
