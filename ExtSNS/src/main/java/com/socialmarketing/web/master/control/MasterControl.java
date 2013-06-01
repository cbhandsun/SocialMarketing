package com.socialmarketing.web.master.control;

/**
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.generator.IncludeValidation;
import ch.ralscha.extdirectspring.generator.ModelGenerator;
import ch.ralscha.extdirectspring.generator.OutputFormat;
import ch.ralscha.extdirectspring.generator.ViewGenerator;
import ch.ralscha.extdirectspring.util.ExtDirectSpringUtil;
import ch.ralscha.extdirectspring.util.JsonHandler;

import com.socialmarketing.service.BaseMasterService;
import com.socialmarketing.util.ExtBeanUtils;
import com.socialmarketing.util.SpringContextUtil;

/**********************************************************************
 * FILE : MasterControl.java CREATE DATE : 2013-5-9 DESCRIPTION :
 * 
 * 
 * CHANGE HISTORY LOG
 * --------------------------------------------------------------------- NO.|
 * DATE | NAME | REASON | DESCRIPTION
 * --------------------------------------------------------------------- 1 |
 * 2013-5-9 | hongtao | 创建草稿版本
 * ---------------------------------------------------------------------
 ******************************************************************************/
@Controller(value = "masterControl")
public class MasterControl {
	@Autowired(required = true)
	private JsonHandler jsonHandler;

	@RequestMapping("js/app/model/master/{masterID}.js")
	public void masterModel(@PathVariable("masterID") String name,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException {
		ModelGenerator.writeModel(request, response,
				Class.forName("com.socialmarketing.vo.master." + name + "VO"),
				OutputFormat.EXTJS4, IncludeValidation.BUILTIN, true);
	}

	@RequestMapping("js/app/view/master/{masterID}/Column.js")
	public void ViewColumn(@PathVariable("masterID") String name,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException {

		ViewGenerator.writeModel(request, response,
				Class.forName("com.socialmarketing.vo.master." + name + "VO"),
				OutputFormat.EXTJS4, true);
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("js/app/store/master/{masterID}.js")
	public void genStrore(@PathVariable("masterID") String name,
			HttpServletRequest request,HttpServletResponse response) throws IOException {
		String path = request.getSession().getServletContext()
				.getRealPath("/js/app/store/Master.json");
		String str = readFile(path);
		Map map = new HashMap();
		map.put("modelID", name);
		StrSubstitutor sub = new StrSubstitutor(map);
		strOutput(request,response,sub.replace(str));
		// System.out.println(sub.replace(str));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("js/app/controller/master/{masterID}.js")
	public void genController(@PathVariable("masterID") String name,
			HttpServletRequest request,HttpServletResponse response) throws IOException {
		String path = request.getSession().getServletContext()
				.getRealPath("/js/app/controller/Master.json");
		String str = readFile(path);
		Map map = new HashMap();
		map.put("modelID", name);
		StrSubstitutor sub = new StrSubstitutor(map);
		strOutput(request,response,sub.replace(str));
		// System.out.println(sub.replace(str));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("js/app/view/master/{masterID}/List.js")
	public void generateViewList(@PathVariable("masterID") String name,
			HttpServletRequest request,HttpServletResponse response) throws IOException {

		String path = request.getSession().getServletContext()
				.getRealPath("/js/app/view/master/List.json");
		String str = readFile(path);
		Map map = new HashMap();
		map.put("modelID", name);
		StrSubstitutor sub = new StrSubstitutor(map);
		strOutput(request,response,sub.replace(str));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("js/app/view/master/{masterID}/Edit.js")
	public void generateViewEdit(@PathVariable("masterID") String name,
			HttpServletRequest request,HttpServletResponse response) throws IOException {

		String path = request.getSession().getServletContext()
				.getRealPath("/js/app/view/master/Edit.json");
		String str = readFile(path);
		Map map = new HashMap();
		map.put("modelID", name);
		StrSubstitutor sub = new StrSubstitutor(map);
		strOutput(request,response,sub.replace(str));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExtDirectMethod(group = "master", value = ExtDirectMethodType.SIMPLE_NAMED)
	public void saveMasters(String masterID, Map data)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		if (data instanceof LinkedHashMap) {
			String beanName = StringUtils.lowerCase(masterID) + "Service";
			BaseMasterService baseMasterService = (BaseMasterService) SpringContextUtil
					.getBean(beanName);
			List modelList = prossParams(masterID, data, "modifyRecords");
			List delList = prossParams(masterID, data, "removeRecords");
			if (!modelList.isEmpty()) {
				baseMasterService.saveMasters(modelList);
			}
			if (!delList.isEmpty()) {
				baseMasterService.deleteMasters(masterID, delList);
			}
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List prossParams(String masterID, Map data, String name)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		if (data instanceof LinkedHashMap) {
			List mapList = (List) data.get(name);
			// List removeList = (List) data.get("removeRecords");
			List retList = new ArrayList();
			if (!mapList.isEmpty()) {
				for (Object obj : mapList) {
					if (obj instanceof LinkedHashMap) {
						LinkedHashMap map = (LinkedHashMap) obj;
						Object VO = jsonHandler.convertValue(
								map,
								Class.forName("com.socialmarketing.vo.master."
										+ masterID + "VO"));
						Object model = Class.forName(
								"com.socialmarketing.model.master." + masterID)
								.newInstance();
						ExtBeanUtils.copyProperties(model, VO);
						retList.add(model);
					}
				}
			}
			return retList;
		}
		return Collections.emptyList();
	}

	// String masterID = (String)map.get("masterID");

	@ExtDirectMethod(group = "master", value = ExtDirectMethodType.STORE_READ)
	public void updateMasters(ExtDirectStoreReadRequest request) {
		Collection masters = Collections.emptyList();
		// BaseMasterService baseMasterService;
		// baseMasterService.updateModels(masters);
	}

	@ExtDirectMethod(group = "master", value = ExtDirectMethodType.STORE_READ)
	public void deleteMasters(ExtDirectStoreReadRequest request) {
		Map map = request.getParams();
		String masterID = (String) map.get("masterID");
		String beanName = StringUtils.lowerCase(masterID) + "Service";
		BaseMasterService baseMasterService = (BaseMasterService) SpringContextUtil
				.getBean(beanName);
		// List list = baseMasterService.deleteMasters(masters);
	}

	@SuppressWarnings("rawtypes")
	@ExtDirectMethod(group = "master", value = ExtDirectMethodType.STORE_READ)
	public List getMasters(ExtDirectStoreReadRequest request) {
		Map map = request.getParams();
		String masterID = (String) map.get("masterID");
		String beanName = StringUtils.uncapitalize(masterID) + "Service";
		BaseMasterService baseMasterService = (BaseMasterService) SpringContextUtil
				.getBean(beanName);
		List list = baseMasterService.getMasters();
		return list;
	}

	public static void main(String[] args) throws IOException {
		String path = "D:/DevEnv/GitRep/SocialMarketing/ExtSNS/src/main/webapp/js/app/controller/Master.json";// request.getSession().getServletContext().getRealPath("/js/app/controller/Master.json");
		String str = readFile(path);
		Map map = new HashMap();
		map.put("modelID", "Company");
		StrSubstitutor sub = new StrSubstitutor(map);
		System.out.println(sub.replace(str));
	}

	private static String readFile(String fileName) throws IOException {
		File myFile = new File(fileName);
		if (!myFile.exists()) {
			System.err.println("Can't Find " + fileName);
		}
		BufferedReader br = null;
		String ret = null;
		try {
			//br = new BufferedReader(new FileReader(myFile, "UTF-8"));
			InputStreamReader isr = new InputStreamReader(new FileInputStream(myFile), "UTF-8"); 
			br = new BufferedReader(isr); 
			String line = null;
			StringBuffer sb = new StringBuffer((int) myFile.length());
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			ret = sb.toString();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
		}
		return ret;

	}
	private void strOutput(HttpServletRequest request,HttpServletResponse response,String str) throws IOException
	{
		byte[] data = str.getBytes(
				ExtDirectSpringUtil.UTF8_CHARSET);
		String ifNoneMatch = request.getHeader("If-None-Match");
		String etag = "\"0" + DigestUtils.md5DigestAsHex(data) + "\"";

		if (etag.equals(ifNoneMatch)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}

		response.setContentType("application/javascript");
		response.setCharacterEncoding(ExtDirectSpringUtil.UTF8_CHARSET.name());
		response.setContentLength(data.length);

		response.setHeader("ETag", etag);

		@SuppressWarnings("resource")
		ServletOutputStream out = response.getOutputStream();
		out.write(data);
		out.flush();
	}
}
