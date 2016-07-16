/*
 * 刘三军 最后修改于 2014、3、22
 */

package com.Master5.main.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

public class ConfigTools {

	File configFile;

	Properties prop = new Properties();

	public ConfigTools() {

		try {
			configFile = new File(
					ConfigTools.class.getClassLoader().getResource("").toURI().getPath() + "config/user.properties");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		new ConfigTools().loadConfig();

	}

	// 初始化配置文件
	public void loadConfig() {

		InputStream in = null;

		try {
			in = new FileInputStream(configFile);
			prop.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 修改文件中的值
	 * 
	 * @param Key
	 *            要修改的 key 值
	 * @param value
	 *            要修改的 key 值对应的 value 值
	 */
	public void set(String Key, Object value) {

		try {

			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(configFile));
			prop.load(bis);
			prop.setProperty(Key, String.valueOf(value));
			FileOutputStream fos = new FileOutputStream(configFile);
			prop.store(fos, null);
			fos.close();
			bis.close();
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

}
