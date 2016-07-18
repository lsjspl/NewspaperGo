package com.Master5.main.web.Catcher.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTML;

import org.apache.commons.lang3.StringEscapeUtils;
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
		urlsInfo.setCareatTime(Calendar.getInstance().getTime());
		return urlsInfoDao.saveAndFlush(urlsInfo);
	}
	
	String titlePatten="<\\s*?(title)\\s*?>[\\s\\S]*?</\\s*?(title)\\s*?>";

	public void catcher(String[] urls) {
		
		Pattern pattern=Pattern.compile(titlePatten);
	

		if (urls == null) {

			List<UrlsInfo> UrlsInfoList = urlsInfoDao.findAll();

			for (UrlsInfo urlsInfo : UrlsInfoList) {

				String result = null;
					result = StringEscapeUtils .unescapeHtml4(send(urlsInfo.getUrls()));
				
				Catcher catcher=new Catcher();
				Matcher matcher=pattern.matcher(result);
				if(matcher.find()){
					
					catcher.setTitle(matcher.group());
				}
				catcher.setCreatTime(Calendar.getInstance().getTime());
				catcher.setContent(result);
				catcher.setUrlId(urlsInfo.getId());
				catcherDao.saveAndFlush(catcher);

			}
		}
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
			Map<String, List<String>> map = connection.getHeaderFields();
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}

			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf8"));
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
