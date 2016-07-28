package com.Master5.main.web.Catcher.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Master5.main.utils.Tools;
import com.Master5.main.utils.constant.TaskStatus;
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

	@Autowired
	CatcherDao catcherDao;

	@Autowired
	UrlsInfoDao urlsInfoDao;

	@Autowired
	CatcherTaskDao catcherTaskDao;

	@Autowired
	TaskLogDao taskLogDao;

	public List<TaskLog> queryTaskLog(int type, int taskId) {

		return taskLogDao.findByTypeAndTaskIdOrderByIdDesc(type, taskId);
	}

	public List<UrlsInfo> queryUrlsInfo() {

		return urlsInfoDao.findAll();
	}

	public UrlsInfo queryUrlsInfoOne(int id) {
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

	public Catcher queryCatcherOne(int id) {
		return catcherDao.findOne(id);
	}
	
	public Catcher testCatcher(UrlsInfo urlsInfoTmp, Date date) {

		Document htmlDoc = null;

		UrlsInfo urlsInfo = urlsInfoDao.findOne(urlsInfoTmp.getId());

		String urls = urlsInfo.getUrls();

		try {
			System.out.println("首页 " + urls);
			urls = Tools.saxUrlForDate(urls, date);
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
				if (state == 3 || state == 0 && (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
						|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
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
		addTask2Queue(task);
	}

	@Transactional
	public void deleteTask(int id) {

		if(CatcherTaskService.getCatcherQueue().contains(id)){
			CatcherTaskService.getCatcherQueue().remove(id);
			
		}
		taskLogDao.deleteByTaskId(id);
		catcherDao.deleteByTaskId(id);
		catcherTaskDao.delete(id);
	}

	public List<CatcherTask> queryTask() {
		return catcherTaskDao.findAllByOrderByIdDesc();
	}

	public void updateCatcherState(int id, int state) {

		Catcher catcher = catcherDao.findOne(id);
		catcher.setState(state);
		catcher.setId(id);
		catcherDao.save(catcher);
	}

	public void redoTask(int taskId) {
		taskLogDao.deleteByTaskId(taskId);
		catcherDao.deleteByTaskId(taskId);
		addTask2Queue(catcherTaskDao.findOne(taskId));
	}

	void addTask2Queue(CatcherTask task) {
		task.setState(TaskStatus.WAIT);
		task = catcherTaskDao.save(task);
		CatcherTaskService.put(task.getId());
	}

}
