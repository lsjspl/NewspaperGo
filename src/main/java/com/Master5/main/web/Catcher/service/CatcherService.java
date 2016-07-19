package com.Master5.main.web.Catcher.service;

import java.io.BufferedReader;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.Master5.main.web.Catcher.dao.CatcherDao;
import com.Master5.main.web.Catcher.dao.UrlsInfoDao;
import com.Master5.main.web.Catcher.entry.Catcher;
import com.Master5.main.web.Catcher.entry.UrlsInfo;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javafx.scene.web.HTMLEditor;

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
	
	public List<Catcher> queryCatcher(){
		return catcherDao.findAll();
	}
	

	Pattern titlePatten = Pattern.compile("<\\s*?title\\s*?>([\\s\\S]*?)</\\s*?title\\s*?>");
	Pattern bodyPatten = Pattern.compile("<\\s*?body\\s*?>([\\s\\S]*?)</\\s*?body\\s*?>");

	public void catcher(String[] urlsList, Date date) {

		if (urlsList == null) {

			List<UrlsInfo> UrlsInfoList = urlsInfoDao.findAll();

			for (UrlsInfo urlsInfo : UrlsInfoList) {

				String urls = urlsInfo.getUrls();

				Pattern timePattern = Pattern.compile("%(\\S*)%");

				Matcher timeMatcher = timePattern.matcher(urls);

				if (timeMatcher.find(1)) {
					String tmp = timeMatcher.group(1);
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(tmp);
					urls = urls.replaceAll("%(\\S*)%", simpleDateFormat.format(date));
				} else {
					System.out.println("匹配时间错误");
					continue;
				}

				String result = send(urls);

				if (StringUtils.isEmpty(result)) {
					continue;
				}

				Pattern mapPattern = Pattern
						.compile("<\\s*?[mM][aA][pP][\\s\\S]*?>([\\s\\S]*?)</\\s*?[mM][aA][pP]\\s*?>");
				Pattern pattern = Pattern.compile("<\\s*?[aA][rR][eE][aA]\\s[\\s\\S]*?href=\"([\\s\\S]*?)\">?");
				Matcher mapMatcher = mapPattern.matcher(result);
				if (mapMatcher.find()) {
					String map = mapMatcher.group(1);
					Matcher areaMatcher = pattern.matcher(map);
					while (areaMatcher.find()) {

						try {

							String childUrl = areaMatcher.group(1);

							if (!childUrl.startsWith("http://") && !childUrl.startsWith("https://")) {

								childUrl = urls.substring(0, urls.lastIndexOf("/") + 1) + childUrl;
							}

							System.out.println(childUrl);
							result = send(childUrl);
							String titleRule = urlsInfo.getTimePattern();
							String bodyRule = urlsInfo.getContentPattern();

							String unescapeHtml = StringEscapeUtils.unescapeHtml(result);
							Catcher catcher = new Catcher();
							Matcher matcher = (!StringUtils.isEmpty(titleRule) ? Pattern.compile(titleRule)
									: titlePatten).matcher(unescapeHtml);
							if (matcher.find(1)) {
								catcher.setTitle(matcher.group(1));
							}
							matcher = (!StringUtils.isEmpty(bodyRule) ? Pattern.compile(bodyRule) : bodyPatten)
									.matcher(unescapeHtml);
							if (matcher.find(1)) {
								catcher.setContent(html2Text(matcher.group(1)));
							}
							catcher.setCreatTime(Calendar.getInstance().getTime());
							catcher.setBaseInfo(StringEscapeUtils.escapeHtml(result));
							catcher.setUrlId(urlsInfo.getId());
							catcher.setTime(date);
							catcher.setUrl(childUrl);
							catcherDao.saveAndFlush(catcher);
						} catch (Exception e) {
							System.out.println("报错了");
							continue;
						}

					}
				}

			}
		}
	}

	String html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script>]*?>[\s\S]*?<\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style>]*?>[\s\S]*?<\/style>
			// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;
	}

	String send(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();
			// Map<String, List<String>> map = connection.getHeaderFields();
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }

			in = new BufferedReader(new InputStreamReader(connection.getInputStream(),
					url.indexOf("anhuinews") == -1 ? "utf8" : "gb2312"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result;
	}

}
