package com.Master5.main.web.Catcher.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Master5.main.web.Catcher.dao.CatcherDao;
import com.Master5.main.web.Catcher.dao.UrlsInfoDao;
import com.Master5.main.web.Catcher.dao.CatcherTaskDao;
import com.Master5.main.web.Catcher.dao.TaskLogDao;
import com.Master5.main.web.Catcher.entry.Catcher;
import com.Master5.main.web.Catcher.entry.CatcherTask;
import com.Master5.main.web.Catcher.entry.TaskLog;
import com.Master5.main.web.Catcher.entry.UrlsInfo;

@Service
public class CatcherService {

	Pattern timePattern = Pattern.compile("%(\\S*)%");

	@Autowired
	CatcherDao catcherDao;

	@Autowired
	UrlsInfoDao urlsInfoDao;

	@Autowired
	CatcherTaskDao catcherTaskDao;

	@Autowired
	TaskLogDao taskLogDao;

	private static final int TASK_START = 0;
	private static final int TASK_FINISH = 1;
	private static final int TASK_SUCCESS_MAIN = 2;
	private static final int TASK_FAILED_MAIN = 3;

	void addTaskLog(int type, int catcherId, int taskId, int urlsId, String url, String remarks) {
		TaskLog taskLog = new TaskLog();
		taskLog.setCatcherId(catcherId);
		taskLog.setState(0);
		taskLog.setTaskId(taskId);
		taskLog.setUrl(url);
		taskLog.setUrlsId(urlsId);
		taskLog.setType(type);
		taskLog.setRemarks(remarks);
		;
		taskLogDao.save(taskLog);
	}

	public List<TaskLog> queryTaskLog(int type, int taskId) {

		return taskLogDao.findByTypeAndTaskId(type, taskId);
	}

	public List<UrlsInfo> queryUrlsInfo() {

		return urlsInfoDao.findAll();
	}

	public UrlsInfo queryOne(int id) {
		return urlsInfoDao.findOne(id);
	}

	public UrlsInfo saveUrlsInfo(UrlsInfo urlsInfo) {
		urlsInfo.setTitlePattern(StringEscapeUtils.escapeHtml(urlsInfo.getTitlePattern()));
		urlsInfo.setAreaPattern(StringEscapeUtils.escapeHtml(urlsInfo.getAreaPattern()));
		urlsInfo.setContentPattern(StringEscapeUtils.escapeHtml(urlsInfo.getContentPattern()));
		urlsInfo.setCreatTime(Calendar.getInstance().getTime());
		return urlsInfoDao.save(urlsInfo);
	}

	public List<Catcher> queryCatcher() {
		return catcherDao.findAll();
	}

	public void catcherWork(String[] urls, Date startDate, Date endDate, CatcherTask task) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);

		while (calendar.getTimeInMillis() <= endDate.getTime()) {
			catcherWork(urls, calendar.getTime(), task);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}

	public void catcherWork(String[] urlsList, Date date, CatcherTask task) {

		Document htmlDoc;

		List<UrlsInfo> UrlsInfoList = urlsInfoDao.findByState(0);

		Catcher catcher = new Catcher();

		addTaskLog(TASK_START, 0, task.getId(), 0, "", "任务开启：" + task.getName());

		for (UrlsInfo urlsInfo : UrlsInfoList) {

			String urls = urlsInfo.getUrls();

			try {
				urls = saxUrlForDate(urls, date);
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

		addTaskLog(TASK_FINISH, 0, task.getId(), 0, "", "任务完成：" + task.getName());
		task.setState(1);
		catcherTaskDao.save(task);
	}

	private String saxUrlForDate(String urls, Date date) throws Exception {

		Matcher timeMatcher = timePattern.matcher(urls);

		if (timeMatcher.find(1)) {
			String tmp = timeMatcher.group(1);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(tmp);
			urls = urls.replaceAll("%(\\S*)%", simpleDateFormat.format(date));
		} else {
			throw new Exception("匹配时间错误:" + urls);
		}

		return urls;
	}

	public Catcher testCatcher(UrlsInfo urlsInfoTmp, Date date) {

		Document htmlDoc = null;

		UrlsInfo urlsInfo = urlsInfoDao.findOne(urlsInfoTmp.getId());

		String urls = urlsInfo.getUrls();

		try {
			System.out.println("首页 " + urls);
			urls = saxUrlForDate(urls, date);
			htmlDoc = Jsoup.connect(urls)
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
					.timeout(50000).get();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("失败了" + urls);
			return null;
		}

		String titlePattern = urlsInfoTmp.getTitlePattern();
		String contentPattern = urlsInfoTmp.getContentPattern();
		String areaPattern = urlsInfo.getAreaPattern();

		Elements areas = htmlDoc.select(StringUtils.isEmpty(areaPattern) ? "area" : areaPattern);

		for (Element element : areas) {

			String childUrl;

			if (element.hasAttr("url")) {
				childUrl = element.attr("url");
			} else if (element.hasAttr("href")) {
				childUrl = element.attr("href");
			} else {
				System.out.println("内页没有相关的属性");
				continue;
			}

			if (childUrl.startsWith("/")) {
				childUrl = urls.substring(0, urls.indexOf("/", urls.indexOf("://") + 3)) + childUrl;
			} else if (childUrl.startsWith("http://") || childUrl.startsWith("https://")) {
			} else {
				childUrl = urls.substring(0, urls.lastIndexOf("/") + 1) + childUrl;
			}
			System.out.println("内页 " + childUrl);
			try {
				Document childDoc = Jsoup.connect(childUrl)
						.userAgent(
								"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
						.timeout(50000).get();
				Catcher catcher = new Catcher();
				catcher.setContent(childDoc.select(
						StringUtils.isEmpty(contentPattern) ? "body" : StringEscapeUtils.unescapeHtml(contentPattern))
						.text());

				catcher.setTitle(childDoc.select(
						StringUtils.isEmpty(titlePattern) ? "title" : StringEscapeUtils.unescapeHtml(titlePattern))
						.text());
				catcher.setTime(date);
				catcher.setUrl(childUrl);

				return catcher;

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("爬取失败" + childUrl + "");
				continue;
			}

		}

		return null;
	}

	public List<Map<String, Object>> total(int taskId) {

		List<Object[]> total = catcherDao.queryTotal(taskId);

		List<Map<String, Object>> result = new ArrayList<>();

		Map<String, Map<String, Object>> totalCache = new HashMap<>();
		Map<String, Set<Long>> dayCache = new HashMap<>();
		// urlsinfo.keyWord,urlsinfo.name,,urlsinfo.type
		// catcher.url,catcher.title,catcher.content,catcher.time,catcher.state
		int index;
		for (Object[] data : total) {
			index = 0;
			int id = (Integer) data[index++];
			String[] keyWords = ((String) data[index++]).split("\\|");
			String name = (String) data[index++];
			int type = (Integer) data[index++];
			String url = (String) data[index++];
			String title = (String) data[index++];
			String content = (String) data[index++];
			Date time = (Date) data[index++];
			int state = (Integer) data[index++];

			for (String keyWord : keyWords) {

				if (!content.contains(keyWord)) {
					continue;
				}

				String key = keyWord + "," + name;

				boolean isWeek = false;
				boolean isVip = type == 1;

				Calendar cal = Calendar.getInstance();
				cal.setTime(time);
				if (state==3 || state == 0 && (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
						|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ) {
					isWeek = true;
				}

				if (!dayCache.containsKey(key)) {
					dayCache.put(key, new HashSet<Long>());
				}
				dayCache.get(key).add(time.getTime());

				if (totalCache.containsKey(key)) {

					Map<String, Object> tmp = totalCache.get(key);

					if (((List<String>) tmp.get("url")).contains(url)
							|| ((List<String>) tmp.get("title")).contains(title)) {
						continue;
					}
					tmp.put("name", name);
					tmp.put("keyWord", keyWord);
					((List<String>) tmp.get("url")).add(url);
					((List<Date>) tmp.get("time")).add(time);
					((List<Integer>) tmp.get("id")).add(id);
					((List<String>) tmp.get("title")).add(title);
					((List<Integer>) tmp.get("state")).add(state);
					if (state != 1) {
						tmp.put("count", (Integer) tmp.get("count") + 1);
						tmp.put("vipCount", (Integer) tmp.get("vipCount") + (isVip ? 1 : 0));
						tmp.put("day", (Integer) tmp.get("day") + 1);
						tmp.put("weekCount", (Integer) tmp.get("weekCount") + (isWeek ? 1 : 0));
					}
				} else {
					Map<String, Object> tmp = new HashMap<>();
					tmp.put("name", name);
					tmp.put("keyWord", keyWord);
					tmp.put("url", new ArrayList<String>());
					tmp.put("time", new ArrayList<Date>());
					tmp.put("id", new ArrayList<Integer>());
					tmp.put("title", new ArrayList<String>());
					tmp.put("state", new ArrayList<Integer>());
					((List<String>) tmp.get("url")).add(url);
					((List<Date>) tmp.get("time")).add(time);
					((List<Integer>) tmp.get("id")).add(id);
					((List<String>) tmp.get("title")).add(title);
					((List<Integer>) tmp.get("state")).add(state);
					if (state != 1) {
						tmp.put("count", 1);
						tmp.put("day", 1);
						tmp.put("vipCount", isVip ? 1 : 0);
						tmp.put("weekCount", isWeek ? 1 : 0);
					} else {
						tmp.put("count", 0);
						tmp.put("day", 0);
						tmp.put("weekCount", 0);
						tmp.put("vipCount", 0);
					}
					result.add(tmp);
					totalCache.put(key, tmp);
				}
			}
		}

		return result;
	}

	public void deleteCatcherById(int id) {
		catcherDao.delete(id);
	}

	public void saveCatcherTask(CatcherTask task) {

		task.setCreatTime(Calendar.getInstance().getTime());
		task.setState(0);
		task = catcherTaskDao.save(task);
		try {
			ProducerConsumer.getInstance().put(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public List<CatcherTask> queryTask() {
		return catcherTaskDao.findAll();
	}

	public void updateCatcherState(int id, int state) {

		Catcher catcher = catcherDao.findOne(id);
		catcher.setState(state);
		catcher.setId(id);
		catcherDao.save(catcher);
	}

}
