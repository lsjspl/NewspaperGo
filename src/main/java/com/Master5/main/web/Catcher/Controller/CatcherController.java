package com.Master5.main.web.Catcher.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @since 2016.07.14
 * @author liusanjun
 *
 */
public class CatcherController {

	private static String outPutDataPath = "d:/";

	private static String outPutFileName = "catcher.txt";

	private static String confFilePath = "d://conf.txt";

	/**
	 * 
	 * @return
	 */
	public static String getOutPutPath() {
		return outPutDataPath + outPutFileName;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {

		//
		List<String> hha = readConf();

		for (String str : hha) {

			List<String> castsResults = castUrls(str);

			for (String urls : castsResults) {

				String result = new String(sendGet(urls));

				outPutFile(getOutPutPath(), result);
			}

		}

	}

	private static void outPutFile(String outPutPath, String result) {
		File file = new File(outPutPath);
		FileWriter fw = null;
		BufferedWriter writer = null;
		try {
			fw = new FileWriter(file);
			writer = new BufferedWriter(fw);
			writer.write(result);
			writer.newLine();
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static List<String> castUrls(String str) {

		String[] strs = str.split(",");

		String url = strs[1];

		List<String> list = new ArrayList<>();
		list.add(url);
		return list;
	}

	private static void catcherTask() {

	}

	public static List<String> readConf() {
		return readFileByLines(confFilePath);

	}

	public static List<String> readFileByLines(String fileName) {
		List<String> fileResult = new ArrayList<>();

		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				fileResult.add(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

		return fileResult;
	}

	public static String sendGet(String url) {
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
			System.out.println("����GET��������쳣��" + e);
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

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("���� POST ��������쳣��" + e);
			e.printStackTrace();
		}
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

}
