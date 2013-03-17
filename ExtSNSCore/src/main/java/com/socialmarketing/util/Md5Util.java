/**
 * 
 */
package com.socialmarketing.util;

import java.security.MessageDigest;

/**
 * Md5工具类
 * @author Jony
 * @version V1.0.0
 */
public class Md5Util {
	
	public static String getMd5(String password, Object salt) {
		try {
			MessageDigest md5 = MD5.getInstance();
			md5.update(password.getBytes());
			md5.update(salt.toString().getBytes());
			return MD5.byteArrayToHexString(md5.digest());
		} catch (Exception e) {
			return "";
		}
	}

}
