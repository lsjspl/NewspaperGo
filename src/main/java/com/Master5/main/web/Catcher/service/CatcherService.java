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
import com.Master5.main.web.Catcher.entry.Catcher;
import com.Master5.main.web.Catcher.entry.UrlsInfo;

@Service
public class CatcherService {

	Pattern timePattern = Pattern.compile("%(\\S*)%");

	@Autowired
	CatcherDao catcherDao;

	@Autowired
	UrlsInfoDao urlsInfoDao;

	public List<UrlsInfo> queryUrlsInfo() {

		return urlsInfoDao.findAll();
	}

	public UrlsInfo queryOne(int id) {
		return urlsInfoDao.findOne(id);
	}

	public UrlsInfo saveUrlsInfo(UrlsInfo urlsInfo) {
		urlsInfo.setTitlePattern(StringEscapeUtils.escapeHtml(urlsInfo.getTitlePattern()));
		urlsInfo.setTimePattern(StringEscapeUtils.escapeHtml(urlsInfo.getTimePattern()));
		urlsInfo.setContentPattern(StringEscapeUtils.escapeHtml(urlsInfo.getContentPattern()));
		urlsInfo.setCareatTime(Calendar.getInstance().getTime());
		return urlsInfoDao.saveAndFlush(urlsInfo);
	}

	public List<Catcher> queryCatcher() {
		return catcherDao.findAll();
	}
	
	public void catcherWork(String[] urls, Date startDate ,Date endDate) {
		catcherDao.deleteAll();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);

		while (calendar.getTimeInMillis() < endDate.getTime()) {
			catcherWork(urls, calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}

	public void catcherWork(String[] urlsList, Date date) {


		Document htmlDoc;

		List<UrlsInfo> UrlsInfoList = urlsInfoDao.findByState(0);

		for (UrlsInfo urlsInfo : UrlsInfoList) {

			String urls = urlsInfo.getUrls();

			try {
				urls = saxUrlForDate(urls, date);
				htmlDoc = Jsoup.connect(urls)
						.userAgent(
								"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
						.get();
			} catch (Exception e1) {
				System.out.println("解析首页失败："+urls);
				continue;
			}

			String titlePattern = urlsInfo.getTitlePattern();
			String contentPattern = urlsInfo.getContentPattern();

			Elements areas = htmlDoc.select("area");
			
			if(areas.size()==0){
				System.out.println("没有找到相关的信息");
				return;
			}

			for (Element element : areas) {

				String childUrl = element.attr("href");

				if (!childUrl.startsWith("http://") && !childUrl.startsWith("https://")) {
					childUrl = urls.substring(0, urls.lastIndexOf("/") + 1) + childUrl;
				}

				try {
					Document childDoc = Jsoup.connect(childUrl)
							.userAgent(
									"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
							.get();
					Catcher catcher = new Catcher();
					catcher.setContent(childDoc.select(StringUtils.isEmpty(contentPattern) ? "body"
							: StringEscapeUtils.unescapeHtml(contentPattern)).text());
					catcher.setTitle(childDoc.select(StringUtils.isEmpty(contentPattern) ? "title"
							: StringEscapeUtils.unescapeHtml(titlePattern)).text());
					catcher.setCreatTime(Calendar.getInstance().getTime());
					catcher.setBaseInfo(StringEscapeUtils.escapeHtml(childDoc.html()));
					catcher.setUrlId(urlsInfo.getId());
					catcher.setTime(date);
					catcher.setUrl(childUrl);
					catcherDao.saveAndFlush(catcher);
					System.out.println("解析并保存成功："+childUrl);
				} catch (IOException e) {
					System.out.println("解析内页失败："+urls);
					continue;
				}

			}

		}
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

	public Catcher testCatcher(int id, Date date) {

		Document htmlDoc = null;

		UrlsInfo urlsInfo = urlsInfoDao.findOne(id);

		String urls = urlsInfo.getUrls();

		try {
			urls = saxUrlForDate(urls, date);
			htmlDoc = Jsoup.connect(urls)
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
					.get();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("失败了");
			return null;
		}

		String titlePattern = urlsInfo.getTitlePattern();
		String contentPattern = urlsInfo.getContentPattern();

		Elements areas = htmlDoc.select("area");

		for (Element element : areas) {

			String childUrl = element.attr("href");

			if (!childUrl.startsWith("http://") && !childUrl.startsWith("https://")) {
				childUrl = urls.substring(0, urls.lastIndexOf("/") + 1) + childUrl;
			}

			try {
				Document childDoc = Jsoup.connect(childUrl)
						.userAgent(
								"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
						.get();
				Catcher catcher = new Catcher();
				catcher.setContent(childDoc.select(
						StringUtils.isEmpty(contentPattern) ? "body" : StringEscapeUtils.unescapeHtml(contentPattern))
						.text());
				catcher.setTitle(childDoc.select(
						StringUtils.isEmpty(contentPattern) ? "title" : StringEscapeUtils.unescapeHtml(titlePattern))
						.text());
				catcher.setTime(date);
				catcher.setUrl(childUrl);

				return catcher;

			} catch (Exception e) {
				System.out.println("爬取失败"+childUrl+"");
				continue;
			}

		}

		return null;
	}

	public List<Map<String, Object>> total() {

		List<Object[]> total = catcherDao.queryTotal();

		List<Map<String, Object>> result = new ArrayList<>();

		Map<String, Map<String, Object>> totalCache = new HashMap<>();
		Map<String,Set<Long>> dayCache=new HashMap<>();
		// urlsinfo.keyWord,urlsinfo.name,,urlsinfo.type
		// catcher.url,catcher.title,catcher.content,catcher.time,catcher.state
		int index;
		for (Object[] data : total) {
			index = 0;
			int id=(Integer) data[index++];
			String keyWord = (String) data[index++];
			String name = (String) data[index++];
			int type = (Integer) data[index++];
			String url = (String) data[index++];
			String title = (String) data[index++];
			String content = (String) data[index++];
			Date time = (Date) data[index++];
			int state = (Integer) data[index++];
			String key = keyWord + "," + name;

			boolean isWeek = false;
			boolean isVip = type == 1;

			Calendar cal = Calendar.getInstance();
			cal.setTime(time);
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				isWeek = true;
			}
			
			if(!dayCache.containsKey(key)){
				dayCache.put(key, new HashSet<Long>());
			}
			dayCache.get(key).add(time.getTime());
			
			if (totalCache.containsKey(key)) {

				Map<String, Object> tmp = totalCache.get(key);
				tmp.put("name", name);
				tmp.put("keyWord", keyWord);
				((List<String>) tmp.get("url")).add(url);
				((List<Date>) tmp.get("time")).add(time);
				((List<Integer>) tmp.get("id")).add(id);
				tmp.put("count", (Integer) tmp.get("count") + 1);
				tmp.put("weekCount", (Integer) tmp.get("weekCount") + (isWeek ? 1 : 0));
				tmp.put("vipCount", (Integer) tmp.get("vipCount") + (isVip ? 1 : 0));
				tmp.put("day", dayCache.get(key).size());
			} else {
				Map<String, Object> tmp = new HashMap<>();
				tmp.put("name", name);
				tmp.put("keyWord", keyWord);
				tmp.put("url", new ArrayList<String>());
				tmp.put("time", new ArrayList<Date>());
				tmp.put("id", new ArrayList<Integer>());
				((List<String>) tmp.get("url")).add(url);
				((List<Date>) tmp.get("time")).add(time);
				((List<Integer>) tmp.get("id")).add(id);
				tmp.put("count", 1);
				tmp.put("day", dayCache.get(key).size());
				tmp.put("weekCount", isWeek ? 1 : 0);
				tmp.put("vipCount", isVip ? 1 : 0);
				result.add(tmp);
				totalCache.put(key, tmp);
			}

		}

		return result;
	}
	
	public void deleteCatcherById(int id){
		catcherDao.delete(id);
	}

}
