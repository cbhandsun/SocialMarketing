package com.socialmarketing.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceTools {
	private String findPrefix;/* 查找的替换前辍 */
	private String findSuffix;/* 查找的替换后辍 */

	/**
	 * 判断findStr中是否存在regex正则表达式
	 * 
	 * @param regex
	 * @param findStr
	 * @return 存在返回true否则false
	 */
	private boolean isExistWord(String regex, String findStr) {
		Pattern pattern1 = Pattern.compile(regex);
		return pattern1.matcher(findStr).find();
	}

	/**
	 * 去除expStr字串前后的\n、\r、\t和空格
	 * 
	 * @param expStr
	 * @return 存在返回去除完成的后的内容，否则不做处理
	 */
	private String trimWhiteSpace(String expStr) {
		while (true) {
			if (expStr.indexOf("\n") == 0 || expStr.indexOf("\r") == 0
					|| expStr.indexOf("\t") == 0 || expStr.indexOf(" ") == 0) {
				expStr = expStr.substring(1);
				continue;
			}
			break;
		}
		while (true) {
			String whiteSpace = expStr.substring(expStr.length() - 1);
			if (whiteSpace.indexOf("\n") == 0 || whiteSpace.indexOf("\r") == 0
					|| whiteSpace.indexOf("\t") == 0
					|| expStr.indexOf(" ") == 0) {
				expStr = expStr.substring(0, expStr.length() - 1);
				continue;
			}
			break;
		}
		return expStr;
	}

	/**
	 * 1. 按照 + 进行拆分，如果没有 + 则直接返回
	 * 
	 * @param inputStr
	 * @return
	 */
	private String[] checkMate(String inputStr) {
		StringBuffer resultBuffer = new StringBuffer();
		StringBuffer paramBuffer = new StringBuffer(" new String[]{");

		if (null == inputStr || inputStr.equals("")) {
			return new String[] { inputStr, "" };
		}
		String[] tokens = inputStr.split("\\+");
		if (tokens.length < 2) {
			return new String[] { inputStr, "" };
		}
		int i = 0;
		for (String token : tokens) {
			token = trimWhiteSpace(token.trim());
			if (token.startsWith("\"")) {
				resultBuffer.append(token);
			} else {
				if (i > 0) {
					paramBuffer.append("," + token + "+\"\"");
				} else {
					paramBuffer.append(token + "+\"\"");
				}
				resultBuffer.append("{" + i + "}");
				i++;
			}
		}
		paramBuffer.append("}");
		return new String[] { resultBuffer.toString(), paramBuffer.toString() };
	}

	/**
	 * 查找替换文件内容(文件必须为UTF-8编码)
	 * 
	 * @param sourceDir
	 *            开始查找java的目录
	 * @param gkey
	 *            生成key的对象
	 * @param propertiesFile
	 *            生成properties路径文件名
	 * @throws Exception
	 */
	public void startReplaceForJava(String sourceDir, IGenoratorKey gkey,
			String propertiesFile) throws Exception {
		System.out.println("开始.....");
		File dir = new File(sourceDir);
		if (dir.isFile()) {
			System.out.println("\"" + sourceDir + "\"不是文件夹!");
			return;
		}
		Stack<File> dirStack = new Stack<File>();
		dirStack.push(dir);
		HashMap<String, String> processedFileList = new HashMap<String, String>();// 保存处理过的文件
		InputStreamReader isr = null;
		BufferedReader raf = null;
		ArrayList<String> msgList = new ArrayList<String>();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(propertiesFile), "UTF-8"));// 写国际化文件
		gkey.resetIncrement();
		while (!dirStack.empty()) {
			dir = dirStack.pop();
			File[] files = dir.listFiles();
			for (File file : files) {
				if (null != processedFileList.get(file.getPath())) {
					continue;
				} else if (file.isDirectory()) {
					dirStack.push(file);
					continue;
				}
				if (!file.getName().toLowerCase().endsWith(".java")) {
					continue;
				}
				String lineStr = null;
				isr = new InputStreamReader(
						new FileInputStream(file.getPath()), "UTF-8");
				raf = new BufferedReader(isr);
				lineStr = raf.readLine();
				StringBuilder java = new StringBuilder();
				int lineNo = 1;
				int beginIdx = -1;
				int endIdx = -1;// 查找后辍
				while (lineStr != null)// 取出java文件内容
				{
					lineStr = lineStr.replaceAll(
							"[\\s]+ApplicationException[\\s]*\\([\\s]*",
							findPrefix);
					lineStr = lineStr
							.replaceAll("[\\s]*\\)[\\s]*;", findSuffix);
					java.append(lineStr + "\n");
					lineStr = raf.readLine();
					lineNo++;
				}
				raf.close();
				isr.close();
				beginIdx = java.indexOf(findPrefix);
				endIdx = java.indexOf(findSuffix, beginIdx + 1);
				while (beginIdx > -1 && endIdx > -1)// 处理ApplicationException("xxx")这种形式的
				{
					String argMsg = trimWhiteSpace(java.substring(beginIdx
							+ findPrefix.length(), endIdx));// 取得"xxx"内容
					argMsg = argMsg.replaceAll("[\\t\\n\\f\\r]+", "");// 处理空格换行符等

					if ("\"".equals(trimWhiteSpace(argMsg))) {
						beginIdx = java.indexOf(findPrefix, endIdx
								+ findSuffix.length());// 继续找下一个
						endIdx = java.indexOf(findSuffix, beginIdx + 1);
						continue;
					}
					if (checkLineComment(java, beginIdx)) {
						msgList.add("该行属于注释行不做处理：" + argMsg + "("
								+ file.getName() + ")");
						beginIdx = java.indexOf(findPrefix, endIdx
								+ findSuffix.length());// 继续找下一个
						endIdx = java.indexOf(findSuffix, beginIdx + 1);
						continue;
					}
					if (isExistWord("\"[a-zA-Z]{1,4}[0-9]{4}\"", argMsg)
							|| isExistWord(
									"^\"[a-zA-Z]{1,20}_[a-zA-Z0-9]{1,20}_{0,1}",
									argMsg))// 符合处理过的的不做处理
					{
						msgList.add("该字串符合处理过的Key规则不做处理：" + argMsg + "("
								+ file.getName() + ")");
						beginIdx = java.indexOf(findPrefix, endIdx
								+ findSuffix.length());// 继续找下一个
						endIdx = java.indexOf(findSuffix, beginIdx + 1);
						continue;
					}
					// 判断"("后面是否为"""和")"前面为"""
					String key = gkey.genoratorKey(file);// keyMap.get(argMsg);//已经存在则取现有的
					boolean notAddComment = false;
					if (getFirstParam(argMsg).indexOf("+") < 0
							&& getFirstParam(argMsg).indexOf("\"") == 0
							&& getFirstParam(argMsg).length() == argMsg
									.length()) {
						/* 判断()中只有一个参数并且不是变量 */
						bw.write("#" + key + " = "
								+ argMsg.substring(1, argMsg.length() - 1)
								+ "(" + file.getName() + ")\n");// 注释所在的文件
						bw.write(key
								+ " = "
								+ MakePropertiesI18nUtil.stringToUTF8(argMsg
										.substring(1, argMsg.length() - 1))
								+ "\n\n");// 去掉＂＂
						java = java.replace(beginIdx, endIdx, findPrefix + "\""
								+ key + "\"");
						msgList.add("成功替换ApplicationException()并在尾部加上注释："
								+ argMsg + "(" + file.getName() + ")");
					} else if (getFirstParam(argMsg).indexOf("+") > 0
							&& getFirstParam(argMsg).indexOf("\"") > -1
							&& getFirstParam(argMsg).length() == argMsg
									.length()) {
						/* 判断()中第一个参数内容是否有是用"+"拼出来的字串 */
						bw.write("#" + key + " = " + argMsg + "("
								+ file.getName() + ")\n");// 注释所在的文件

						String[] result = checkMate(getFirstParam(argMsg));
						String tmp = result[0].replaceAll("\"", "");
						bw.write(key
								+ " = "
								+ MakePropertiesI18nUtil
										.stringToUTF8(trimWhiteSpace(tmp))
								+ "\n\n");// 去掉＂＂
						java = java.replace(beginIdx, endIdx, findPrefix + "\""
								+ key + "\"," + result[1]);
						msgList.add("成功替换ApplicationException()并在尾部加上注释："
								+ argMsg + "(" + file.getName() + ")");

					} else if (getFirstParam(argMsg).indexOf("\"") < 0) {
						((GenoratorCustomKeyImpl) gkey)
								.setStartIdx(((GenoratorCustomKeyImpl) gkey)
										.getStartIdx() - 1);
						/* 参数是变量 */
						msgList.add("ApplicationException()中的参数是变量不做处理!("
								+ file.getName() + ")");
						notAddComment = true;
					} else if (getFirstParam(argMsg).indexOf("\"") > 0
							&& getFirstParam(argMsg).indexOf(".") > 0) {
						((GenoratorCustomKeyImpl) gkey)
								.setStartIdx(((GenoratorCustomKeyImpl) gkey)
										.getStartIdx() - 1);
						/* 参数可能已经是调用资源处理了 */
						msgList.add("ApplicationException()中参数可能已经是调用资源处理了：("
								+ file.getName() + ")");
						notAddComment = true;
					} else {
						bw.write("#" + argMsg + " (" + file.getName() + ")\n");// 注释所在的文件
						bw.write(key + " = 请输入内容 \n\n");// 去掉＂＂
						msgList.add("ApplicationException()语法不规范：("
								+ file.getName() + ")");
					}
					endIdx = java.indexOf(findSuffix, beginIdx + 1);
					if (endIdx > -1 && !notAddComment) {
						java = java.replace(endIdx, endIdx
								+ findSuffix.length(), findSuffix + "// " + key
								+ " = " + argMsg + "  \n");
					}
					beginIdx = java.indexOf(findPrefix, endIdx);// 下一个
					endIdx = java.indexOf(findSuffix, beginIdx + 1);
				}

				BufferedWriter bwJava = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(file),
								"UTF-8"));// 写国际化文件
				bwJava.write(java.toString());
				bwJava.close();
			}
		}
		bw.close();
		Collections.sort(msgList);
		for (String string : msgList) {
			System.out.println(string);
		}
		System.out.println("完成!");
	}

	/**
	 * 
	 * 检查单行是否使用单行"//"注释
	 * 
	 * @param contents
	 *            java代码
	 * @param endIdx
	 *            　检查点的最后一个下标
	 * @return 存在返回true
	 */
	private boolean checkLineComment(StringBuilder contents, int endIdx) {
		boolean result = false;
		while (endIdx > -1) {
			String chkStr = contents.substring(endIdx - 1, endIdx);
			if (chkStr.equals("\n") || chkStr.equals("\r")) {
				result = false;
				break;
			} else if (chkStr.equals("/")) {
				result = true;
				break;
			}
			endIdx--;
		}
		return result;
	}

	/**
	 * 获取()中的第一个参数
	 * 
	 * @param findStr
	 *            ()中的内容
	 * @return
	 */
	private String getFirstParam(String findStr) {
		String replaceStr = "~";
		int beginIndexQ = -1;
		int endIndexQ = -1;
		String qutoStr = "\"";
		do {
			beginIndexQ = findStr.indexOf(qutoStr, endIndexQ + 1);
			if (beginIndexQ < 0) {
				break;
			}
			endIndexQ = findStr.indexOf(qutoStr, beginIndexQ + 1);
			String tmp = findStr.substring(beginIndexQ, endIndexQ + 1);
			if (tmp.indexOf(",") > -1) {
				findStr = findStr.replace(tmp, tmp.replace(",", replaceStr));
			}
		} while (beginIndexQ > -1);
		String[] str = findStr.split(",");
		return str[0].replace(replaceStr, ",");
	}

	public interface IGenoratorKey {
		public String genoratorKey(File javaFile);

		public void resetIncrement();
	}

	public class GenoratorFilePathKeyImpl implements IGenoratorKey {
		private int keyLayer;// 生成properties Key名称的层级数如：xxx1_xxx2_xx3
		private int startIdx = 1;// 默认从１开始

		/**
		 * 
		 * @param keyLayer
		 *            生成properties Key名称的层级数如：xxx1_xxx2_xx3
		 */
		public GenoratorFilePathKeyImpl(int keyLayer) {
			this.keyLayer = keyLayer;
		}

		/**
		 * 当前替换的java文件
		 * 
		 * @param javaFile
		 * @return
		 */
		public String genoratorKey(File javaFile) {
			String splitChar = File.separator.equals("\\") ? "\\\\"
					: File.separator;
			String[] layers = javaFile.getPath().replaceAll(splitChar, ",")
					.split(",");
			String key = "";
			int tmpKeyLayer = keyLayer;
			if (layers.length - 1 < tmpKeyLayer)// 层级不足时用该文件名前三位填充
			{
				for (int i = 1; i <= tmpKeyLayer - layers.length; i++) {
					String layer = javaFile.getName().toUpperCase();
					layer = layer.length() < 4 ? layer + i + "_" : layer
							.substring(0, 4)
							+ i + "_";
					key = layer + key;
					tmpKeyLayer--;
				}
			}
			for (int i = layers.length - 2; i >= 0; i--)// layers.length-2从目录名开始
			{
				if (tmpKeyLayer < 1) {
					break;
				}
				String tmpStr = layers[i].replaceAll("[:|-|_|=|]", "");
				tmpStr = tmpStr.length() < 4 ? tmpStr : tmpStr.substring(0, 4);
				String layer = tmpStr.toUpperCase() + "_";
				key = layer + key;
				tmpKeyLayer--;
			}
			key += javaFile.getName().replaceAll("\\.java", "") + startIdx;// String.format(key,
			// "0")+increment;
			startIdx++;
			return key;
		}

		public void resetIncrement() {
			startIdx = 1;
		}

		public int getStartIdx() {
			return startIdx;
		}

		public void setStartIdx(int startIdx) {
			this.startIdx = startIdx;
		}
	}

	public class GenoratorCustomKeyImpl implements IGenoratorKey {
		private String baseKey = "AEA";
		private int baseLayer = 4;
		private int startIdx = 1;// 默认从１开始

		/**
		 * 
		 * @param keyLayer
		 *            生成properties Key名称的层级数如：xxx1_xxx2_xx3
		 */
		public GenoratorCustomKeyImpl(int baseLayer) {
			this.baseLayer = baseLayer;
		}

		/**
		 * 当前替换的java文件
		 * 
		 * @param javaFile
		 * @return
		 */
		public String genoratorKey(File javaFile) {
			String key = baseKey;
			if ((startIdx + "").length() < baseLayer) {
				for (int i = 0; i < baseLayer - (startIdx + "").length(); i++) {
					key += "0";
				}
			}
			key += startIdx + "";
			startIdx++;
			return key;
		}

		public void resetIncrement() {
			startIdx = 1;
		}

		public String getBaseKey() {
			return baseKey;
		}

		public void setBaseKey(String baseKey) {
			this.baseKey = baseKey;
		}

		public int getStartIdx() {
			return startIdx;
		}

		public void setStartIdx(int startIdx) {
			this.startIdx = startIdx;
		}
	}

	/**
	 * 加密MD5为32位
	 * 
	 * @param str
	 *            要加密的字串
	 * @return 返回32位长的32进制字符
	 */
	public String md5By32(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			return buf.toString().toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 加密MD5为16位
	 * 
	 * @param str
	 *            要加密的字串
	 * @return　返回16位长的16进制字符
	 */
	public String md5By16(String str) {
		return md5By32(str).substring(8, 24);
	}

	public static void main(String[] args) {
		String str = "abcdefg999duyydduyyy";
		String regex = "999(?:[.[^uy]+]+)uy";
		// String regex="999(?:.+)uy";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		m.find();
		System.out.println(m.group());
		System.out.println(m.groupCount());
	}

	public String getFindPrefix() {
		return findPrefix;
	}

	public void setFindPrefix(String findPrefix) {
		this.findPrefix = findPrefix;
	}

	public String getFindSuffix() {
		return findSuffix;
	}

	public void setFindSuffix(String findSuffix) {
		this.findSuffix = findSuffix;
	}
}
