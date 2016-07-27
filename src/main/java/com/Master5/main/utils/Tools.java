
package com.Master5.main.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tools {

	private static final Logger logger = LoggerFactory.getLogger(Tools.class);


	static Pattern timePattern = Pattern.compile("%(\\S*)%");
	
	/**
	 * 功能与描述：根据数组拼出用,分割的字符串
	 * 
	 * @param ids
	 *            值数组
	 * @param defaultStr
	 *            如果为空的话 返回的值
	 * @param isRepeat
	 *            是否保留重复的值
	 * @return ，分割的字符串
	 */
	public static String arrayToString(int[] ids, String defaultStr, boolean isRepeat) {

		if (null == ids || ids.length == 0) {
			return defaultStr;
		}

		Collection<Object> collection = isRepeat ? new ArrayList<Object>() : new HashSet<Object>();

		for (Object i : ids) {
			collection.add(i);
		}

		StringBuilder stringBuilder = new StringBuilder();

		for (Object i : collection) {
			stringBuilder.append(",").append(i).toString();
		}

		return stringBuilder.substring(1);

	}

	/**
	 * 功能与描述：根据数组拼出用,分割的字符串
	 * 
	 * @param objs
	 *            值数组
	 * @param defaultStr
	 *            如果为空的话 返回的值
	 * @param isRepeat
	 *            是否保留重复的值
	 * @return ，分割的字符串
	 */
	public static String arrayToString(Object[] objs, String defaultStr, boolean isRepeat) {

		if (null == objs || objs.length == 0) {
			return defaultStr;
		}

		Collection<Object> collection = isRepeat ? new ArrayList<Object>() : new HashSet<Object>();

		for (Object i : objs) {
			collection.add(i);
		}

		StringBuilder stringBuilder = new StringBuilder();

		for (Object i : collection) {
			stringBuilder.append(",").append(i).toString();
		}

		return stringBuilder.substring(1);

	}

	/**
	 * 功能与描述：判断参数是不是空的
	 * 
	 * @param parameters
	 *            参数
	 * @return 参数是不是空的 空的：true 不空的：false
	 */
	public static boolean stringIsNullOrEmpty(String... parameters) {

		if (parameters.length == 0) {
			return true;
		}

		for (String str : parameters) {
			if (null == str || str.trim().equals("")) {
				return true;
			}
		}

		return false;
	}

	public static String sendToURL(String ip, String json) {

		BufferedReader reader = null;

		try {

			byte[] b = json.getBytes("UTF-8");

			URL url = new URL(ip);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

			conn.setRequestProperty("Content-type", "json");

			conn.setDoOutput(true);

			conn.setRequestMethod("POST");

			conn.connect();

			conn.getOutputStream().write(b, 0, b.length);

			conn.getOutputStream().flush();

			conn.getOutputStream().close();

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String curLine = "";

			if ((curLine = reader.readLine()) != null) {

				return curLine;

			}

		} catch (UnsupportedEncodingException e) {

			logger.error("字符编码", e);

		} catch (MalformedURLException e) {

			logger.error("URL异常", e);

		} catch (ConnectException e) {

			logger.error("连接异常", e);

		} catch (Exception e) {

			logger.error("中转数据失败", e);

		} finally {

			try {

				reader.close();

			} catch (IOException e) {

				logger.error("关闭流失败", e);

			}
		}

		return null;
	}

	public static String saxUrlForDate(String urls, Date date) throws Exception {

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

}
