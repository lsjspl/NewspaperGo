package com.Master5.main.utils.encrypt;

import java.security.MessageDigest;

public class MD5 {

	/**
	 * 获得加密后的字符串
	 * 
	 * @param pass
	 * @return
	 */
	public final static String getMD5Pass(String pass) {

		return getMD5(pass + "olca").toUpperCase();
	}

	public static void main(String[] args) {

		System.out.println(getMD5("admin"));

	}

	public final static String getMD5(String pass) {

		String result = "";

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		try {

			byte[] strTemp = pass.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			result = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
