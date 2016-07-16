package com.Master5.main.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @version 1.0
 * @author lixunhui@qq.com
 * @since JDK 1.6
 **/
public class ValidateTools {

	/** 邮件 */
	private static final String EMAIL = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";

	/** url */
	private static final String URL = "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$";

	/** 仅中文 */
	private static final String CHINESE = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";

	/** 手机 */
	private static final String MOBILE = "^(13|15)[0-9]{9}$";

	/** 图片 */
	private static final String PICTURE = "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$";

	/** 用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串 */
	private static final String USERNAME = "^[a-zA-Z]\\w+$";

	/** 身份证 */
	private static final String V_IDCARD = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";

	/** 验证密码(数字和英文同时存在) */
	private static final String PASSWORD_REG = "^[-_\\.\\w]+$";

	/** 验证密码长度(6-18位) */
	private static final String PASSWORD_LENGTH = "^\\d{5,18}$";

	private static final String NICKNAME = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2Da-zA-Z0-9_]+$";

	/**
	 * 验证是不是中文
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean Chinese(String value) {
		return match(CHINESE, value);
	}

	/**
	 * 验证是不是邮箱地址
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean Email(String value) {
		return match(EMAIL, value);
	}

	/**
	 * 验证是不是正确的身份证号码
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean IDcard(String value) {
		return match(V_IDCARD, value);
	}

	/**
	 * 验证是不是手机号码
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean Mobile(String value) {
		return match(MOBILE, value);
	}

	/**
	 * 验证密码的长度(6~18位)
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean length(String value) {
		return match(PASSWORD_LENGTH, value);
	}

	/**
	 * 验证密码(数字和英文同时存在)
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean password_reg(String value) {
		return match(PASSWORD_REG, value);
	}

	/**
	 * 验证图片
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean Picture(String value) {
		return match(PICTURE, value);
	}

	/**
	 * 验证URL
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean Url(String value) {
		return match(URL, value);
	}

	/**
	 * 验证用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean userName(String value) {
		return match(USERNAME, value);
	}

	/**
	 * 验证是不是手机号码
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean nickName(String value) {
		return match(NICKNAME, value);
	}

	/**
	 * @param regex
	 *            正则表达式字符串
	 * @param str
	 *            要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
}