package com.xyz.tiger.base.util;

import java.security.MessageDigest;

/**
 * @Description: MD5加密工具类
 * @author: Hanht
 * @since: jdk1.8
 * @time: 2017年6月29日
 */
public class MD5Util {

	/***
	 * MD5加密 生成32位md5码
	 * 
	 * @param inStr
	 *            待加密字符串
	 * @return 返回32位md5码
	 */
	public static String md5Encode(String inStr) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] byteArray = inStr.getBytes("UTF-8");
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuilder hexValue = new StringBuilder();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

}
