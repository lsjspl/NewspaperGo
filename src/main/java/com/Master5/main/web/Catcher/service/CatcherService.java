package com.Master5.main.web.Catcher.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

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

		System.out.println(urlsInfo);
		urlsInfo.setTitlePattern(StringEscapeUtils.escapeHtml(urlsInfo.getTitlePattern()));
		urlsInfo.setTimePattern(StringEscapeUtils.escapeHtml(urlsInfo.getTimePattern()));
		urlsInfo.setContentPattern(StringEscapeUtils.escapeHtml(urlsInfo.getContentPattern()));
		urlsInfo.setCareatTime(Calendar.getInstance().getTime());
		return urlsInfoDao.saveAndFlush(urlsInfo);
	}

	public List<Catcher> queryCatcher() {
		return catcherDao.findAll();
	}

	Pattern titlePatten = Pattern.compile("<\\s*?title\\s*?>([\\s\\S]*?)</\\s*?title\\s*?>");
	Pattern bodyPatten = Pattern.compile("<\\s*?body\\s*?>([\\s\\S]*?)</\\s*?body\\s*?>");

	public void catcherWork(String[] urlsList, Date date) {

		catcherDao.deleteAll();

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
				e1.printStackTrace();
				continue;
			}

			String titlePattern = urlsInfo.getTitlePattern();
			String contentPattern = urlsInfo.getContentPattern();

			Elements areas = htmlDoc.select("area");

			for (Element element : areas) {

				String childUrl = element.attr("href");

				if (!childUrl.startsWith("http://") && !childUrl.startsWith("https://")) {
					childUrl = urls.substring(0, urls.lastIndexOf("/") + 1) + childUrl;
				}

				System.out.println(childUrl);

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

				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}

			}

		}
	}

	Pattern timePattern = Pattern.compile("%(\\S*)%");

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
					.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
					.get();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("失败了");
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
				e.printStackTrace();
				continue;
			}

		}

		return null;
	}

}
