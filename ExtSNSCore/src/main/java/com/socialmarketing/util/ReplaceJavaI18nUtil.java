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
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceJavaI18nUtil {
	/**
	 * 查找代码中的所有注释块
	 * 
	 * @param fileName
	 *            文件名称
	 * @param code
	 *            　从fileName中读取的内容返回给code对象
	 * @param blockPrefix
	 *            注释块的前缀
	 * @param blockSuffix
	 *            注释块的后缀
	 * @return
	 * @throws Exception
	 */
	private static List<CommentBlcokPoint> findCommentBlock(String fileName,
			StringBuilder code, String blockPrefix, String blockSuffix)
			throws Exception {
		String lineStr = null;
		InputStreamReader isr = new InputStreamReader(new FileInputStream(
				fileName), "UTF-8");
		BufferedReader raf = new BufferedReader(isr);
		lineStr = "";// raf.readLine();
		while (lineStr != null)// 取出java文件内容
		{
			lineStr = raf.readLine();
			code.append(lineStr + CR);
		}
		String regex = blockPrefix + ".+?" + blockSuffix;
		Pattern p = Pattern.compile(regex);
		char c1 = 1;
		Character cc1 = new Character(c1);
		char c2 = 2;
		Character cc2 = new Character(c2);
		Matcher m = p.matcher(code.toString().replaceAll("\\r", cc1.toString())
				.replaceAll("\\n", cc2.toString()));
		List<CommentBlcokPoint> result = new ArrayList<CommentBlcokPoint>();
		while (m.find()) {
			/**
			 * 找到注释块时记录其起始和终止位置
			 */
			CommentBlcokPoint cbp = new CommentBlcokPoint();
			cbp.setStartIdx(m.start());
			cbp.setEndIdx(m.end());
			// cbp.setContent(m.group().replace(cc1.toString(),
			// "\r").replace(cc2.toString(), "\\n"));
			result.add(cbp);
		}
		return result;
	}

	/**
	 * 替换“”中的中文文字
	 * 
	 * @param sourceDir
	 * @throws Exception
	 */
	public static void replaceJavaDoubleByte(String sourceDir) throws Exception {
		System.out.println("开始.....");
		File dir = new File(sourceDir);
		if (dir.isFile()) {
			System.out.println("\"" + sourceDir + "\"不是文件夹!");
			return;
		}

		Stack<File> dirStack = new Stack<File>();
		dirStack.push(dir);
		HashMap<String, String> processedFileList = new HashMap<String, String>();// 保存处理过的文件
		String regexChinese = "\"(?:[\u4e00-\u9fa5\\s,\\.!。，！=＝\\(\\)（）\\-\\$\\w]+)(?:[\\w\\s]*)(?:[\u4e00-\u9fa5\\s,\\.!。，！=＝\\(\\)（）\\-\\$\\w]*)\"";
		Pattern p = Pattern.compile(regexChinese);
		IGenoratorKey key = new GenoratorFilePathKeyImpl(3);
		BufferedWriter newClass = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(sourceDir + "\\MessageKey.java"), "UTF-8"));// 生成常量类
		newClass.append(PACKAGE_NAME + CR);
		newClass.append("public class " + CLASS_NAME + CR + "{" + CR);
		int findCount = 0;
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
				StringBuilder code = new StringBuilder();
				/**
				 * 提取所有的注释块并把代码内容传给code变量
				 */
				List<CommentBlcokPoint> cbpList = findCommentBlock(file
						.getPath(), code, "/\\*", "\\*/");
				Matcher m = p.matcher(code);
				StringBuffer javaCode = new StringBuffer();
				boolean isWritable = false;
				while (m.find())// 取出java文件内容
				{
					boolean isInCommentBlock = false;
					if (isFilterList(m.group())) {
						continue;
					}
					for (CommentBlcokPoint point : cbpList) {
						/**
						 * 判断是否在注释块列表中
						 */
						if (m.start() > point.getStartIdx()
								&& m.end() < point.getEndIdx()) {
							isInCommentBlock = true;
							break;
						}
					}
					if (!isInCommentBlock) {
						if (checkLineComment(code, m.start())) {
							/**
							 * 在行注释里不做处理
							 */
							continue;
						}
						if (findCount == 22) {
							System.out.println();
						}
						/**
						 * 替换处理
						 */
						String keyname = key.genoratorKey(file);
						m.appendReplacement(javaCode, PREFIXKEY + keyname
								+ "/*" + m.group() + "*/");
						String replaceStr = trimWhiteSpace(m.group());
						newClass.append(PUBLIC_VAR + keyname + "=" + replaceStr
								+ ";" + CR);
						isWritable = true;
						findCount++;
						System.out
								.println("找到：" + m.group() + "替换成：" + PREFIXKEY
										+ keyname + "/*" + m.group() + "*/");
					}
				}
				if (isWritable) {
					m.appendTail(javaCode);
					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(file),
									"UTF-8"));
					bw
							.write(javaCode.toString().replaceAll(
									"\r\nnull\r\n", CR));
					bw.close();
				}
			}
		}
		newClass.append(CR + "}");
		newClass.flush();
		newClass.close();
		System.out.println("完成!共找到：" + findCount + "处内容。");
	}

	/**
	 * 判断str全是空格或字母
	 * 
	 * @param str
	 * @return 是返回true
	 */
	public static boolean isFilterList(String str) {
		String regex = "[\u4e00-\u9fa5]";// "[\"\\s\\w\\-\\!\\)\\(]";
		int find = 0;
		for (int i = 0; i < str.length(); i++) {
			if (i == str.length() - 1) {
				if (isExistWord(regex, str.substring(i))) {
					find++;
				}
			} else {
				if (isExistWord(regex, str.substring(i, i + 1))) {
					find++;
				}
			}
		}
		return find > 0 ? false : true;
	}

	public static void file2Utf8(String sourceDir) throws Exception {
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
				if (!file.getName().toLowerCase().endsWith(".properties")) {
					continue;
				}
				String lineStr = null;
				isr = new InputStreamReader(
						new FileInputStream(file.getPath()), "UTF-8");
				raf = new BufferedReader(isr);
				lineStr = raf.readLine();
				StringBuilder properties = new StringBuilder();
				while (lineStr != null)// 取出java文件内容
				{
					if (trimWhiteSpace(lineStr).length() > 0
							&& trimWhiteSpace(lineStr).indexOf("#") < 0) {
						lineStr = "#" + lineStr + CR
								+ MakePropertiesI18nUtil.stringToUTF8(lineStr)
								+ CR + CR;
						properties.append(lineStr);
					} else if (trimWhiteSpace(lineStr).indexOf("#") > -1) {
						properties.append(lineStr + CR);
					}
					lineStr = raf.readLine();
				}

				raf.close();
				isr.close();
				if (properties.length() > 0) {
					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(file),
									"UTF-8"));// 写国际化文件
					bw.write(properties.toString());
					bw.close();
				}
			}
		}
		System.out.println("完成!");
	}

	/**
	 * 判断findStr中是否存在regex正则表达式
	 * 
	 * @param regex
	 * @param findStr
	 * @return 存在返回true否则false
	 */
	private static boolean isExistWord(String regex, String findStr) {
		Pattern pattern1 = Pattern.compile(regex);
		return pattern1.matcher(findStr).find();
	}

	private static String CR = "\r\n";
	private static String PREFIXKEY = "solution.auto.imes.messageservice.constants.MessageKey.";
	private static String PUBLIC_VAR = "\tpublic final static String ";
	private static String PACKAGE_NAME = "package solution.auto.imes.messageservice.constants;";
	private static String CLASS_NAME = "MessageKey";

	/**
	 * 去除expStr字串前后的\n、\r、\t和空格
	 * 
	 * @param expStr
	 * @return 存在返回去除完成的后的内容，否则不做处理
	 */
	private static String trimWhiteSpace(String expStr) {
		if (expStr == null || expStr.length() == 0) {
			return "";
		}

		while (true) {
			if (expStr.indexOf("\n") == 0 || expStr.indexOf(" ") == 0
					|| expStr.indexOf("\r") == 0 || expStr.indexOf("\t") == 0
					|| expStr.indexOf("\f") == 0) {
				expStr = expStr.substring(1);
				continue;
			}
			break;
		}
		while (true) {
			String whiteSpace = expStr.substring(expStr.length() - 1);
			if (whiteSpace.indexOf("\n") == 0 || expStr.indexOf(" ") == 0
					|| whiteSpace.indexOf("\r") == 0
					|| whiteSpace.indexOf("\t") == 0
					|| expStr.indexOf("\f") == 0) {
				expStr = expStr.substring(0, expStr.length() - 1);
				continue;
			}
			break;
		}
		return expStr;
	}

	/**
	 * 解析字串拼凑形式的消息转换成消息带参数或不带参数的格式
	 * 
	 * @param inputStr
	 *            　常量字串拼凑形式的消息（如："中国"+var+"你好"....）
	 * @return　成功返回如:{"中国{0}你好.....","new String[]{var}"}
	 * 
	 */
	private static String[] getMessageAndParams(String inputStr) {
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
	 * 查找替换入口
	 * 
	 * @param sourceDir
	 *            开始查找java的目录
	 * @param gkey
	 *            生成key的对象
	 * @param propertiesFile
	 *            生成properties路径文件名
	 * @throws Exception
	 */
	public static void startReplaceJava(String sourceDir, IGenoratorKey gkey,
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
				String suffStr = ");";
				String prefErrStr = " IOException(";// IOException后缀
				while (lineStr != null)// 取出java文件内容
				{
					lineStr = lineStr.replaceAll(
							"[\\s]+IOException[\\s]*\\([\\s]*", prefErrStr);
					lineStr = lineStr.replaceAll("[\\s]*\\)[\\s]*;", suffStr);
					java.append(lineStr + CR);
					lineStr = raf.readLine();
					lineNo++;
				}
				raf.close();
				isr.close();

				beginIdx = java.indexOf(prefErrStr);
				endIdx = java.indexOf(suffStr, beginIdx + 1);
				boolean isWritable = false;
				List<CommentBlcokPoint> cbList = catchJavaCommentOfBlock(java);
				while (beginIdx > -1 && endIdx > -1)// 处理IOException("xxx")这种形式的
				{
					String argMsg = trimWhiteSpace(java.substring(beginIdx
							+ prefErrStr.length(), endIdx));// 取得"xxx"内容
					argMsg = argMsg.replaceAll("[\\t\\n\\f\\r]+", "");// 处理空格换行符等

					if ("\"".equals(trimWhiteSpace(argMsg))) {
						beginIdx = java.indexOf(prefErrStr, endIdx
								+ suffStr.length());// 继续找下一个
						endIdx = java.indexOf(suffStr, beginIdx + 1);
						continue;
					}
					if (checkLineComment(java, beginIdx)) {
						msgList.add("该行属于注释行不做处理：" + argMsg + "("
								+ file.getName() + ")");
						beginIdx = java.indexOf(prefErrStr, endIdx
								+ suffStr.length());// 继续找下一个
						endIdx = java.indexOf(suffStr, beginIdx + 1);
						continue;
					}
					if (checkInJavaCommentOfBlock(beginIdx, cbList)) {
						msgList.add("该行属于注释块中不做处理：" + argMsg + "("
								+ file.getName() + ")");
						beginIdx = java.indexOf(prefErrStr, endIdx
								+ suffStr.length());// 继续找下一个
						endIdx = java.indexOf(suffStr, beginIdx + 1);
						continue;
					}
					if (isExistWord("\"[a-zA-Z]{1,4}[0-9]{4}\"", argMsg)
							|| isExistWord("^\"[a-zA-Z]{1,20}_[a-zA-Z]{2,20}",
									argMsg))// 符合处理过的的不做处理
					{
						msgList.add("该字串符合处理过的Key规则不做处理：" + argMsg + "("
								+ file.getName() + ")");
						beginIdx = java.indexOf(prefErrStr, endIdx
								+ suffStr.length());// 继续找下一个
						endIdx = java.indexOf(suffStr, beginIdx + 1);
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
								+ "(" + file.getName() + ")" + CR);// 注释所在的文件
						bw.write(key
								+ " = "
								+ MakePropertiesI18nUtil.stringToUTF8(argMsg
										.substring(1, argMsg.length() - 1))
								+ CR + CR);// 去掉＂＂
						java = java.replace(beginIdx, endIdx, prefErrStr + "\""
								+ key + "\"");
						msgList.add("成功替换IOException()并在尾部加上注释：" + argMsg + "("
								+ file.getName() + ")");
						isWritable = true;
					} else if (getFirstParam(argMsg).indexOf("+") > 0
							&& getFirstParam(argMsg).indexOf("\"") > -1
							&& getFirstParam(argMsg).length() == argMsg
									.length()) {
						/* 判断()中第一个参数内容是否有是用"+"拼出来的字串 */
						bw.write("#" + key + " = " + argMsg + "("
								+ file.getName() + ")" + CR);// 注释所在的文件

						String[] result = getMessageAndParams(getFirstParam(argMsg));
						String tmp = result[0].replaceAll("\"", "");
						bw.write(key
								+ " = "
								+ MakePropertiesI18nUtil
										.stringToUTF8(trimWhiteSpace(tmp)) + CR
								+ CR);// 去掉＂＂
						java = java.replace(beginIdx, endIdx, prefErrStr + "\""
								+ key + "\"," + result[1]);
						msgList.add("成功替换IOException()并在尾部加上注释：" + argMsg + "("
								+ file.getName() + ")");
						isWritable = true;
					} else if (getFirstParam(argMsg).indexOf("\"") < 0) {
						((GenoratorCustomKeyImpl) gkey)
								.setStartIdx(((GenoratorCustomKeyImpl) gkey)
										.getStartIdx() - 1);
						/* 参数是变量 */
						msgList.add("IOException()中的参数是变量不做处理!("
								+ file.getName() + ")");
						notAddComment = true;
					} else if (getFirstParam(argMsg).indexOf("\"") > 0
							&& getFirstParam(argMsg).indexOf(".") > 0) {
						((GenoratorCustomKeyImpl) gkey)
								.setStartIdx(((GenoratorCustomKeyImpl) gkey)
										.getStartIdx() - 1);
						/* 参数可能已经是调用资源处理了 */
						msgList.add("IOException()中参数可能已经是调用资源Key不做处理：("
								+ file.getName() + ")");
						notAddComment = true;
					} else {
						bw.write("#" + argMsg + " (" + file.getName() + ")"
								+ CR);// 注释所在的文件
						bw.write(key + " = 请输入内容 " + CR);// 去掉＂＂
						msgList.add("IOException()语法不规范需要在资源文件中的Key=" + key
								+ "及java文件进行手工处理：(" + file.getName() + ")");
						isWritable = true;
					}
					endIdx = java.indexOf(suffStr, beginIdx + 1);
					if (endIdx > -1 && !notAddComment) {
						java = java.replace(endIdx, endIdx + suffStr.length(),
								suffStr + "// " + key + " = " + argMsg + "  "
										+ CR);
					}
					beginIdx = java.indexOf(prefErrStr, endIdx);// 查下一个
					endIdx = java.indexOf(suffStr, beginIdx + 1);
				}
				if (isWritable)/* 　找不到的不需要重写java文件 */
				{
					BufferedWriter bwJava = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(file),
									"UTF-8"));
					bwJava.write(java.toString());
					bwJava.close();
				}
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
	private static boolean checkLineComment(StringBuilder contents, int endIdx) {
		boolean result = false;
		while (endIdx > -1) {
			String chkStr = contents.substring(endIdx - 1, endIdx);
			if (chkStr.equals("\n") || chkStr.equals("\r")) {
				result = false;
				break;
			} else if (chkStr.equals("/")) {
				endIdx--;
				if (endIdx < 0) {
					break;
				}
				chkStr = contents.substring(endIdx - 1, endIdx);
				if (chkStr.equals("/")) {
					result = true;
					break;
				}
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
	private static String getFirstParam(String findStr) {
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

	private static void startReplace() throws Exception {
		String dir = "D:\\tmp\\project\\src";
		GenoratorCustomKeyImpl gkey = new GenoratorCustomKeyImpl(4);
		gkey.setStartIdx(168);
		String propertiesFile = "D:\\tmp\\saic_zh_CN.properties";
		startReplaceJava(dir, gkey, propertiesFile);
	}

	/**
	 * 检查keyWordStartIdx是否在List<CommentBlok>中
	 * 
	 * @param keyWordStartIdx
	 * @param cb
	 * @return
	 */
	private static boolean checkInJavaCommentOfBlock(int keyWordStartIdx,
			List<CommentBlcokPoint> cb) {
		boolean result = false;
		for (CommentBlcokPoint blok : cb) {
			if (keyWordStartIdx > blok.getStartIdx()
					&& keyWordStartIdx < blok.getEndIdx()) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * 查询javaCode中有注释的块并记录其起始位置和终止位置
	 * 
	 * @param javaCode
	 * @return
	 */
	private static List<CommentBlcokPoint> catchJavaCommentOfBlock(
			StringBuilder javaCode) {
		String regex = "/\\*(?:[.\\n[^/\\*]+]+)\\*/";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(javaCode);
		ArrayList<CommentBlcokPoint> reslutList = new ArrayList<CommentBlcokPoint>();
		while (m.find()) {
			CommentBlcokPoint cb = new CommentBlcokPoint();
			cb.setStartIdx(m.start());
			cb.setEndIdx(m.end());
			reslutList.add(cb);
		}
		return reslutList;
	}

	/**
	 * @author wzs TODO 注释下标记录vo
	 */
	private static class CommentBlcokPoint {
		private int startIdx;
		private int endIdx;
		private String content;

		public int getEndIdx() {
			return endIdx;
		}

		public void setEndIdx(int endIdx) {
			this.endIdx = endIdx;
		}

		public int getStartIdx() {
			return startIdx;
		}

		public void setStartIdx(int startIdx) {
			this.startIdx = startIdx;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}

	public static void main(String[] args) throws Exception {
		// startReplace();
		// file2Utf8("d:\\tmp\\pro");
		replaceJavaDoubleByte("D:\\tmp\\project");
		/*
		 * StringregexChinese=
		 * "\"(\\n*)([.[^\"]+]*)(\\n*)[\u4e00-\u9fa5]+(\\n*)(.*)(\\n*)\"";
		 * Pattern p=Pattern.compile(regexChinese); Stringstr=
		 * "s.setMaxStorage(Double.pars\n\reDouble\"(obj[12].\"toString()));a=\"\n  	中国d\";"
		 * ; Matcher m=p.matcher(str); if(m.find()) {
		 * System.out.println("group count:"+m.groupCount());
		 * System.out.println("m.group()=="+m.group()); if(m.groupCount()>0) {
		 * for(int i=1;i<=m.groupCount();i++) {
		 * System.out.println("m.group("+i+")=="+m.group(i)); } } }
		 */
	}

	public static interface IGenoratorKey {
		public String genoratorKey(File javaFile);

		public void resetIncrement();
	}

	public static class GenoratorFilePathKeyImpl implements IGenoratorKey {
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

	public static class GenoratorCustomKeyImpl implements IGenoratorKey {
		private String baseKey = "AEB";
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
	public static String md5By32(String str) {
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
	public static String md5By16(String str) {
		return md5By32(str).substring(8, 24);
	}
}
