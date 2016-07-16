package com.Master5.main.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class encryptTools {

	static Random random = new Random();

	/**
	 * 把一个字符串转换成一个utf-8的字符串 字符串的格式流必须本来就是utf-8的
	 */
	public static String getUTF8Code(String code) {

		try {

			code = new String(code.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return code;
	}

	/**
	 * 获得指定位数的补0的字符串
	 * 
	 * @param length
	 *            返回字符串的位数
	 * @param number
	 *            需要补0的数字 long
	 */
	public static String getFormatInt(int length, long number) {

		if (length < 1)
			length = 1;
		return String.format("%0" + length + "d", number);
	}

	/**
	 * 产生随机字符串
	 */
	public static String getRandomString(int length) {

		char[] words = ("ABCDEF").toCharArray();

		char[] randBuffer = new char[length];

		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = words[random.nextInt(words.length)];
		}

		return new String(randBuffer);
	}

	/**
	 * 删除一个字符串所有的大写字母
	 */
	public static String delUpperCase(String str) {

		return str.replaceAll("[\\pL]", "");
	}

	public static String getEncodeStr(int[] num) {

		StringBuilder str = new StringBuilder();
		int length = 32;

		for (int i = 0; i < num.length; i++) {
			num[i] = num[i] << (i + 1);
			str.append(String.valueOf(num[i]).length()).append(num[i]);
		}
		String numRs = str.toString();

		char[] strArray = numRs.toCharArray();

		char[] ranArray = getRandomString(length).toCharArray();

		int strLength = strArray.length;
		int numLength = numRs.length();
		int ranSeed = 0;
		int ranNum = 0;
		int ranTmp = 0;

		for (int i = 0; i < strLength; i++) {
			ranSeed = (length - ranTmp) / (numLength - i);
			ranNum = random.nextInt(ranSeed);
			ranTmp += ranNum == 0 ? 1 : ranNum;
			ranArray[ranTmp] = strArray[i];
		}

		return String.valueOf(ranArray);
	}

	public static int[] getDecodeStr(String str) {

		str = delUpperCase(str);

		int seedLength = 1;

		if (str.length() > 15)
			seedLength = 2;
		int idLength = Integer.valueOf(str.substring(0, seedLength));
		int id = Integer.valueOf(str.substring(seedLength, idLength + seedLength));
		int day = Integer.valueOf(str.substring(idLength + seedLength + 1, str.length()));
		id = id >> 1;
		day = day >> 2;

		return new int[] { id, day };
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			String str = getEncodeStr(new int[] { 1000000000, 30001231 });

			int[] ii = getDecodeStr(str);
			if (ii[0] != 1000000000 || ii[1] != 30001231)
				System.err.println(ii[0] + " " + ii[1]);
		}

		System.out.println("============");

	}
}
