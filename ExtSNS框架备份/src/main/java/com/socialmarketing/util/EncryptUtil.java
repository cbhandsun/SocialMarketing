/**********************************************************************
 * FILE : EncryptUtils.java
 * CREATE DATE : 2008-07-01
 * DESCRIPTION :
 *		
 *		在IMES系统中，EncryptUtils提供对用户密码SHA-1算法签名，以及验证的函数。
 *		提供用户登录后Token的生成，以及Token的提交验证。
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2008-07-01 |  Liu Yan Lu  |    创建草稿版本
 *---------------------------------------------------------------------              
 **********************************************************************
 */
package com.socialmarketing.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * 加密密钥的工具类
 * 
 * @author LiuYanLu
 */
public class EncryptUtil {

	/** 系统生成令牌的过期时间 */
	public static final int TOKEN_EXPIRED_DATE = 3; // days

	/**
	 * 令牌分隔符
	 */
	public static final String TOKEN_SEPARATOR = ",";

	/**
	 * 令牌前缀
	 */
	public static final String TOKEN_PREFIX = "#TOKEN";

	/**
	 * 令牌的密钥
	 */
	private static final String TOKEN_KEY = "solution.auto.imes.tokenKey";

	/**
	 * 令牌中的日期表示形式
	 */
	private final static String FORMAT_DATE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将明文的密码以SHA-1算法加密
	 * 
	 * @param passwd
	 *            明文密码
	 * @return String SHA-1算法加密后的密码签名
	 */
	public static String shaEncryptText(String passwd) {
		MessageDigest md = null;
		byte[] bt = passwd.getBytes();
		String strDes = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid algorithm.");
			return null;
		}

		return strDes;
	}

	/**
	 * 验证用户输入的密码是否与数据库中SHA-1算法加密后的密码相同
	 * 
	 * @param passwd
	 *            明文密码
	 * @param sha1_passwd
	 *            SHA-1加密算法后的密码签名
	 * @return true 相同 false 不相同
	 */
	public static boolean validatePassword(String passwd, String sha1_passwd) {

		if (null == passwd || null == sha1_passwd)
			return false;

		if (sha1_passwd.equals(shaEncryptText(passwd))) {
			return true;
		}

		return false;
	}

	/**
	 *  用户登录验证成功后，系统自动为用户生成TOKEN : 通过可逆加密算法，将username + login
	 * timestamp转换为字符串，作为TOKEN返回给客户端。
	 * 
	 * @param username
	 *            用户登录名
	 * 
	 * @return 令牌
	 */
	public static String generateToken(String username) {
		String currentDate = getCurrentDateAsString(FORMAT_DATE_YYYY_MM_DD_HH_MM_SS);

		String before_token_str = currentDate + TOKEN_SEPARATOR + username;

		return addTokenPrefix(desEncryptText(before_token_str));
	}

	/**
	 *  验证用户的TOKEN是否正确 : 通过可逆加密算法，将Token拆分为username和login timestamp。
	 * 判断拆分出来的username是否与输入的用户名相同，login时间是否没有超过指定的有效期
	 * 
	 * @param username
	 *            用户登录用ID
	 * @param token
	 *            用户的Token
	 * 
	 * @return true 验证通过 false 验证失败
	 */
	public final static String[] validateToken(String username, String token)
			throws Exception {
		String err_str = "Invalid token!";
		Calendar current_time = Calendar.getInstance();
		String encryptToken = removeTokenPrefix(token);
		// remove prefix
		encryptToken = desDecryptText(encryptToken);
		if (null == encryptToken || encryptToken.equals(""))
			throw new Exception(err_str);

		String[] sep_index = encryptToken.split(TOKEN_SEPARATOR);
		if (sep_index == null || sep_index.length <= 0)
			throw new Exception(err_str);

		String strtoken_time = sep_index[0];
		String strtoken_username = sep_index[1];

		if (!username.equalsIgnoreCase(strtoken_username))
			throw new Exception(err_str);

		// Date token_date = parseDate(strtoken_time,
		// FORMAT_DATE_YYYY_MM_DD_HH_MM_SS);
		//
		// Calendar expiredDate = Calendar.getInstance();
		// expiredDate.setTime(token_date);
		// expiredDate.add(Calendar.DAY_OF_MONTH, TOKEN_EXPIRED_DATE);
		// if (expiredDate.before(current_time))
		// throw new Exception(err_str);
		return sep_index;
	}

	/**
	 * Byte数组到字符串转换
	 * 
	 * @param bts
	 *            Byte数组
	 * @return 转换后的字符串
	 */
	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	/**
	 * 字符串到Byte数组的转换
	 * 
	 * @param srcStr
	 *            字符串
	 * @return Byte数组
	 */
	private static byte[] hex2Bytes(String srcStr) {
		int byte_len = srcStr.length() / 2;
		byte[] result = new byte[byte_len];
		for (int i = 0; i < byte_len; i++) {
			String bytestr = srcStr.substring(i * 2, i * 2 + 2);
			result[i] = Integer.valueOf(bytestr, 16).byteValue();
		}
		return result;
	}

	/**
	 * 加密生成令牌
	 * 
	 * @param inputstr
	 *            加密的信息
	 * @return 生成的令牌信息
	 */
	public static String desEncryptText(String inputstr) {

		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();

			// 从原始密匙数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(TOKEN_KEY.getBytes());

			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

			SecretKey securekey = keyFactory.generateSecret(dks);

			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");

			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

			// 现在，获取数据并加密
			// 正式执行加密操作
			return bytes2Hex(cipher.doFinal(inputstr.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 解密令牌信息
	 * 
	 * @param inputStr
	 *            令牌信息
	 * @return 解密后的信息
	 */
	public static String desDecryptText(String inputStr) {
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();

			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(TOKEN_KEY.getBytes());

			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);

			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance("DES");

			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

			// 现在，获取数据并解密
			// 正式执行解密操作
			return new String(cipher.doFinal(hex2Bytes(inputStr)));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 以PBE算法生成加密令牌的信息
	 * 
	 * @param inputstr
	 *            输入的信息
	 * @return 生成的令牌信息
	 */
	private static String encodePBEToken(String inputstr) {

		try {
			PBEKeySpec pbeKeySpec = new PBEKeySpec(TOKEN_KEY.toCharArray());
			SecretKeyFactory keyFac = SecretKeyFactory
					.getInstance("PBEWithMD5AndDES");
			SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

			byte[] salt = { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,
					(byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };

			int count = 2;

			// 生成pbe算法所需的参数对象，两个参数详见 RSA的 PKCS #5 标准
			PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, count);

			// 生成一个加密器
			Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

			// 初始化加密器
			pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

			// 明文
			byte[] cleartext = inputstr.getBytes();

			// 加密
			byte[] ciphertext = pbeCipher.doFinal(cleartext);

			// 返回密文
			return bytes2Hex(ciphertext);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * 解密PBE令牌的信息
	 * 
	 * @param inputStr
	 *            加密过的令牌信息
	 * @return 解密后的信息
	 */
	private static String decodePBEToken(String inputStr) {
		try {
			PBEKeySpec pbeKeySpec = new PBEKeySpec(TOKEN_KEY.toCharArray());
			SecretKeyFactory keyFac = SecretKeyFactory
					.getInstance("PBEWithMD5AndDES");
			SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

			byte[] salt = { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,
					(byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };

			int count = 2;

			// 生成pbe算法所需的参数对象，两个参数详见 RSA的 PKCS #5 标准
			PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, count);

			// 生成一个加密器
			Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

			// 初始化加密器
			pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);

			// 密文
			byte[] ciphertext = hex2Bytes(inputStr);

			// 解密
			byte[] cleartext = pbeCipher.doFinal(ciphertext);

			// 返回明文
			return new String(cleartext);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "";
	}

	/**
	 * Main运行测试函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String strSrc = "Pass1234";
		System.out.println("Source String:" + strSrc);
		System.out.println("Encrypted String:");
		System.out.println("Use Def:" + EncryptUtil.shaEncryptText(strSrc));

		Long id = new Long(1);
		String username = "admin";
		System.out.println("Username String:" + username);
		System.out
				.println(DateUtil
						.getCurrentDateAsString(DateUtil.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS));
		String token = EncryptUtil.generateToken(username);
		System.out
				.println(DateUtil
						.getCurrentDateAsString(DateUtil.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS));
		System.out.println("Token String:" + token);

		try {
			System.out.println("Validate Token String:"
					+ EncryptUtil.validateToken(username, token));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out
				.println(DateUtil
						.getCurrentDateAsString(DateUtil.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS));

		try {
			System.out.println("Validate Token String:"
					+ EncryptUtil.validateToken(username, token));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 以指定格式过去当前日期信息
	 * 
	 * @param formatPattern
	 *            指定日期格式
	 * @return 当前日期
	 */
	private final static String getCurrentDateAsString(String formatPattern) {
		Date date = new Date();
		return new SimpleDateFormat(formatPattern).format(date);
	}

	/**
	 * 按照指定格式解析日期信息字符串
	 * 
	 * @param stringValue
	 *            日期信息字符串
	 * @param formatPattern
	 *            指定格式
	 * @return 日期
	 */
	private final static Date parseDate(String stringValue, String formatPattern) {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		try {
			return format.parse(stringValue);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断该信息是否令牌加密
	 * 
	 * @param str
	 *            信息
	 * @return 是或否
	 */
	public final static boolean isEncryptToken(String str) {
		return str.startsWith(TOKEN_PREFIX);
	}

	/**
	 * 去除加密令牌信息中的前缀
	 * 
	 * @param str
	 *            令牌信息
	 * @return 去除前缀后的令牌信息
	 */
	private final static String removeTokenPrefix(String str) {
		if (str.startsWith(TOKEN_PREFIX)) {
			str = str.substring(TOKEN_PREFIX.length());
		}
		return str;
	}

	/**
	 * 加入令牌前缀
	 * 
	 * @param str
	 *            输入字符串
	 * @return 追加令牌前缀后的字符串
	 */
	private final static String addTokenPrefix(String str) {
		return TOKEN_PREFIX + str;
	}
}
