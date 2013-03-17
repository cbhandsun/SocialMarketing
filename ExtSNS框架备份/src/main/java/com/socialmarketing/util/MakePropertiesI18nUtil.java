package com.socialmarketing.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class MakePropertiesI18nUtil {

	/**
	 * 功能:生成国际化properties文件 使用说明:一,把excel文件copy过来三列数据的内容保存到txt文件; 二,调用此方法
	 * 
	 * @param sourceFile
	 *            txt文件路径
	 * @throws Exception
	 */
	public static void make(String sourceFile) throws Exception {
		HashMap<String, Integer> keyMap = new HashMap<String, Integer>();
		HashMap<String, Integer> valueMap = new HashMap<String, Integer>();
		String preFile = sourceFile.substring(0, sourceFile.lastIndexOf("."));
		String cnFile = preFile + "_zh_CN.properties";
		String enFile = preFile + "_en.properties";

		String cnVFile = preFile + "_value.properties";
		FileOutputStream foscnv = new FileOutputStream(cnVFile);

		FileOutputStream foscn = new FileOutputStream(cnFile);
		FileOutputStream fosen = new FileOutputStream(enFile);
		RandomAccessFile raf = new RandomAccessFile(sourceFile, "r");
		String lineStr = raf.readLine();
		int lineNo = 1;
		while (lineStr != null) {
			lineStr = new String(lineStr.trim().getBytes("ISO8859-1"));
			String[] str = lineStr.split("\t");
			if (lineStr.trim().length() == 0) {
				lineStr = raf.readLine();
				lineNo++;
				continue;
			}

			if (str.length != 3) {
				System.out.println("注意1：\"" + lineStr + "\" 第" + lineNo
						+ "行数据不完整,不进行处理!");
				lineStr = raf.readLine();
				lineNo++;
				continue;
			}
			if (str[0].trim().length() == 0 || str[1].trim().length() == 0
					|| str[2].trim().length() == 0) {
				System.out.println("注意1：\"" + lineStr + "\" 第" + lineNo
						+ "行数据不完整,不进行处理!");
				lineStr = raf.readLine();
				lineNo++;
				continue;
			}
			String cn = str[2].trim() + " = " + str[0].trim() + "\n";
			String en = str[2].trim() + " = " + str[1].trim() + "\n";

			if (valueMap.get(str[0].trim()) != null
					&& valueMap.get(str[1].trim()) != null)// 过滤相同的名称中文、英文
			{
				System.out.println("注意2：\"" + lineStr + "\" 第" + lineNo
						+ "行中文、英文名称已经存在,不进行处理!");
				lineStr = raf.readLine();
				lineNo++;
				continue;
			} else {
				valueMap.put(str[0].trim(), lineNo);
				valueMap.put(str[1].trim(), lineNo);
				String v = str[0].trim() + " = " + str[2] + "\n";
				foscnv.write(v.getBytes());
			}

			if (null != keyMap.get(str[2].trim()))// 已经存在
			{
				System.out.println("Key=" + str[2].trim() + "与第"
						+ keyMap.get(str[2].trim()) + "行有冲突！");
			} else// 不存在
			{
				keyMap.put(str[2].trim(), lineNo);
			}
			foscn.write(stringToUTF8(cn).getBytes());
			fosen.write(en.getBytes());
			lineStr = raf.readLine();
			lineNo++;
		}
		foscnv.close();
		fosen.close();
		foscn.close();
		raf.close();
		System.out.println("分解成功！生成properties文件到：\n\t" + cnFile + "\n\t"
				+ enFile + "\n生成value text到：\n\t" + cnVFile);
	}

	public static String stringToUTF8(String str) {
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (c > 255) {
				sb.append("\\u");
				j = (c >>> 8);
				tmp = Integer.toHexString(j);
				if (tmp.length() == 1)
					sb.append("0");
				sb.append(tmp);
				j = (c & 0xFF);
				tmp = Integer.toHexString(j);
				if (tmp.length() == 1)
					sb.append("0");
				sb.append(tmp);
			} else {
				sb.append(c);
			}
		}
		return (new String(sb));
	}

	public static String UTF8toString(String s) {
		if (s == null) {
			return null;
		}
		StringBuffer result = new StringBuffer();
		int savedI, i, ch;
		for (i = 0; i < s.length(); i++) {
			if ((ch = s.charAt(i)) == '\\') {
				if (s.length() > i + 1 && s.charAt(i + 1) == 'u') {
					savedI = i;
					i += 2;
					while (s.length() > i && s.charAt(i) == 'u') {
						i++;
					}
					if (s.length() >= i + 4) {
						ch = Integer.parseInt(s.substring(i, i + 4), 16);
						i += 3;
					} else {
						i = savedI;
					}
				}
			}
			result.append((char) ch);
		}
		return result.toString();
	}

	public static void r() throws Exception {
		System.out.println("开始.....");
		File file = new File("d:\\r.txt");

		InputStreamReader isr = null;
		BufferedReader raf = null;

		isr = new InputStreamReader(new FileInputStream(file.getPath()), "GBK");
		raf = new BufferedReader(isr);
		String lineStr = raf.readLine();
		while (lineStr != null)// 取出java文件内容
		{
			String[] s = lineStr.split("=");
			String key = ReplaceJavaI18nUtil.md5By16(s[1]);
			key = key.substring(0, 4) + "_" + key.substring(4, 8) + "_"
					+ key.substring(8, 12) + "_" + key.substring(12);
			System.out.println("#" + key + "=" + s[1]);
			System.out.println(key + "=xxx\n");
			// System.out.println(md5By16(s[1])+"="+s[1]);
			lineStr = raf.readLine();
		}
		raf.close();
		isr.close();

		System.out.println("完成!");
	}

	public static void main(String[] args) throws Exception {
		// String sourceFile="d:\\BIResources.txt";
		// make(sourceFile);
		r();
	}
}
