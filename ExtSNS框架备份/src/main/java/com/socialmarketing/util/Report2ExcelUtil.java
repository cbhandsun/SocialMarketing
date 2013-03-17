/**********************************************************************
 * FILE : Report2ExcelUtil.java
 * CREATE DATE : 2008-12-10
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2008-06-06 |  ZhangGuojie  |    创建草稿版本
 *---------------------------------------------------------------------              
 *
 ******************************************************************************/
package com.socialmarketing.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 装载excel
 * 
 * @author Administrator
 */
public class Report2ExcelUtil {
	/**
	 * Excel的单元信息
	 */
	private Map<String, List<ELInCellInfo>> elAllInfo;

	/**
	 * 导出excel文件(目前版本不支持group by)
	 * 
	 * @param templateXls
	 *            excel表格模板文件位置(文件系统路径)
	 * @param sheetName
	 *            templateXls模板所在的sheet的名称
	 * @param reportData
	 *            套打到templateXls文件中所需要的数据. 说明：套打的数据目前分为两种：
	 *            一、一般的pojo类，二、java.util.List类。
	 *            第一种主要应用在templateXls文件中单个数据;第二种应用在列表。
	 *            第二种应用如果在templateXls某列是空白需要加上一个不存在的属性否则列出的单元格样式会没掉
	 * 
	 *            使用例子： public static void testSupp() throws Exception 　{
	 *            SPUSInfo spusInfo= new SPUSInfo();//一般的pojo类
	 *            spusInfo.setSpusSourceTypeName("这是SpusSourceTypeName");
	 *            spusInfo.setSheetNo("这是sheetNo"); spusInfo.setPlantNo("攻其不备");
	 *            spusInfo.setCreateTime(new Date());
	 *            spusInfo.setSheetNo128("ÌS08111200041VÎ");
	 *            spusInfo.setSupplName("右是supplName");
	 *            spusInfo.setSupplNo("supplNo dfd");
	 *            spusInfo.setDockNo("dockNo jjjj");
	 *            spusInfo.setDeliverySupplName("deliverySupplName jjddsf");
	 * 
	 * 
	 *            Supplier deliverySupplier=new Supplier();//一般的pojo类
	 *            deliverySupplier.setSupplTelNo1("supplTelNo1 fdfdf");
	 *            deliverySupplier.setSupplFaxNo1("supplFaxNo1 ddd");
	 *            spusInfo.setDeliverySupplier(deliverySupplier);
	 * 
	 *            Supplier supplier=new Supplier();//一般的pojo类
	 *            supplier.setSupplNameC("供isupplName");
	 *            supplier.setSupplAddrC("supplAddrCjjj");
	 *            supplier.setSupplTelNo1("supplTelNo1ddkj");
	 *            supplier.setSupplContact1("supplContact1 kdfd");
	 *            supplier.setSupplFaxNo1("supplFaxNo1 kjdfdf");
	 *            spusInfo.setSupplier(supplier);
	 * 
	 *            Plant plantInfo=new Plant();//一般的pojo类
	 *            plantInfo.setPlantNameE("中是plantNameE");
	 *            plantInfo.setPlantNameC("中是plantNameC");
	 *            plantInfo.setPlantAddrE1("中是PlantAddrE1");
	 *            plantInfo.setPlantNameES("中是PlantNameES");
	 *            plantInfo.setPlantNameCS("中是PlantNameCS");
	 *            spusInfo.setPlantInfo(plantInfo);
	 * 
	 *            List<SPUSInfoDetail> plist=new
	 *            ArrayList<SPUSInfoDetail>();//java.util.List类,注意：这个是列表数据
	 *            SPUSInfoDetail sd=new SPUSInfoDetail(); sd.setShowID(1);
	 *            sd.setPartNo("001"); sd.setPartName("partName 001");
	 *            sd.setReqQty("reqQty 1000");
	 *            sd.setPoPackageNo("poPackageNo 1090");
	 *            sd.setDockReceiveQty("dockReceiveQty 122"); plist.add(sd);
	 *            sd=new SPUSInfoDetail(); sd.setShowID(2); sd.setPartNo("002");
	 *            sd.setPartName("partName 002"); sd.setReqQty("reqQty 2000");
	 *            sd.setPoPackageNo("poPackageNo 1010");
	 *            sd.setDockReceiveQty("dockReceiveQty 122"); plist.add(sd);
	 *            sd=new SPUSInfoDetail(); sd.setShowID(3); sd.setPartNo("003");
	 *            sd.setPartName("partName 003"); sd.setReqQty("reqQty 3000");
	 *            sd.setPoPackageNo("poPackageNo 3090");
	 *            sd.setDockReceiveQty("dockReceiveQty 322"); plist.add(sd);
	 *            sd=new SPUSInfoDetail(); sd.setShowID(4); sd.setPartNo("004");
	 *            sd.setPartName("partName 004"); sd.setReqQty("reqQty 4000");
	 *            sd.setPoPackageNo("poPackageNo 4090");
	 *            sd.setDockReceiveQty("dockReceiveQty 422"); plist.add(sd);
	 * 
	 *            Map<String,Object> result=new HashMap<String, Object>();
	 *            //注意：key=plist，plist是java.util.List类对象 result.put("plist",
	 *            plist);
	 * 
	 *            //注意：key=supsInfo，supsInfo是一般pojo类 result.put("spusInfo",
	 *            spusInfo);
	 * 
	 * 
	 *            
	 *            //特别说明：以上excel模板所需要数据已经做好，如何在excel模板中单元格应用EL表达式绑定到这些数据呢？请认真看完下面说明
	 *            // 如果要在某个单元格显示对应到某个pojo类的某个属性只需在所在单元格对应位置加上EL表达式（假设数据如上面的）： //
	 *            #{supsInfo.supplier.supplFaxNo1}这样即可绑定到supsInfo对象中的supplier属性中
	 *            (此属性也是pojo对象)的suppliFaxNo1属性 //
	 *            如果是列表这种情况则必须对应到List对象使用方法同一般pojo对象 //
	 *            如：#{plist.partNo}，plist是map中的key名称不是变量称，pojo形式的也是一样这个很重要!
	 * 
	 *            String tXls="D:\\project\\reports\\SuppTemplate.xls";
	 *            FileOutputStream fos=new FileOutputStream("d:\\supp.xls");
	 * 
	 *            Report2ExcelUtil.print2Excel(tXls, "supp", result,
	 *            fos);//生成excel报表 fos.close();
	 * 
	 *            }
	 * @param out
	 *            生成xls文件out对象
	 * @throws Exception
	 */
	public void print2Excel(String templateXls, String sheetName,
			Map<String, Object> reportData, OutputStream out) throws Exception {
		InputStream template = null;
		Workbook wbTpl = null;
		WritableWorkbook book = null;
		WritableSheet sheet = null;
		try {
			templateXls = URLDecoder.decode(templateXls, "UTF-8").replace(
					"file:/", "");
			template = new FileInputStream(templateXls);// Report2ExcelUtil.class.getResourceAsStream(templateXls);
			wbTpl = Workbook.getWorkbook(template);
			book = Workbook.createWorkbook(out, wbTpl);
			sheet = book.getSheet(sheetName);// 3,20
			elAllInfo = initExcelCellContents2Map(sheet);
			Set<String> reportKeySet = reportData.keySet();
			for (String key : reportKeySet) {
				if (reportData.get(key) instanceof List)// 处理动态list
				{
					processList(sheet, key, reportData.get(key));
					continue;
				}
				processPojo(sheet, key, reportData.get(key));// 处理固定内容
			}
			book.write();
			return;
		} finally {
			if (template != null) {
				template.close();
			}
			if (book != null) {
				book.close();
			}
			elAllInfo = null;
			wbTpl = null;
			book = null;
			sheet = null;
			template = null;
		}
	}

	/**
	 * 处理一般pojo
	 * 
	 * @param sheet
	 * @param key
	 * @param pojo
	 */
	private void processPojo(WritableSheet sheet, String key, Object pojo) {
		List<ELInCellInfo> elList = elAllInfo.get(key);
		if (elList == null || elList.size() == 0) {
			return;
		}
		try {
			for (ELInCellInfo info : elList)// 处理不同的column
			{
				String cellContent = sheet.getCell(info.getC(), info.getR())
						.getContents();
				for (int i = 0; i < info.getElInfoArray().length; i++)// 处理在同一格子中有多个EL表达式
				{
					String propertyName = info.getElInfoArray()[i];
					String regx = key.replace(".", "\\.") + "\\."
							+ propertyName.replace(".", "\\.");
					String elStr = "#\\{\\s*" + regx + "\\s*\\}";
					cellContent = cellContent.replaceAll(elStr, value2String(
							pojo, propertyName));

				}
				Label label = new Label(info.getC(), info.getR(), cellContent,
						sheet.getCell(info.getC(), info.getR()).getCellFormat());
				try {
					sheet.addCell(label);
				} catch (Exception e) {
					System.out.println("添加excle数据错误：processList()");
					e.printStackTrace();
				}
			}
			return;
		} finally {
			elList = null;
		}
	}

	/**
	 * 解析list数据到excel文件中
	 * 
	 * @param sheet
	 * @param key
	 * @param listData
	 *            　list数据
	 */
	@SuppressWarnings("unchecked")
	private void processList(WritableSheet sheet, String key, Object listData) {
		List<Object> list = (List<Object>) listData;
		int elAtLine = -1;// list EL表达式所在行,等处理完后对此行进行删除
		try {
			for (int idx = list.size() - 1; idx >= 0; idx--)// 处理所有的记录
			{
				Object pojo = list.get(idx);
				List<ELInCellInfo> elList = elAllInfo.get(key);
				if (elList == null || elList.size() == 0) {
					break;
				}
				sheet.insertRow(elList.get(0).getR() + 1);// 新加一行
				if (elAtLine == -1) {
					elAtLine = elList.get(0).getR();
				}
				for (ELInCellInfo info : elList)// 处理不同的column
				{
					String cellContent = sheet
							.getCell(info.getC(), info.getR()).getContents();
					for (int i = 0; i < info.getElInfoArray().length; i++)// 处理在同一格子中有多个EL表达式
					{
						String propertyName = info.getElInfoArray()[i];
						String regx = key.replace(".", "\\.") + "\\."
								+ propertyName.replace(".", "\\.");
						String elStr = "#\\{\\s*" + regx + "\\s*\\}";
						cellContent = cellContent.replaceAll(elStr,
								value2String(pojo, propertyName));
					}
					Label label = new Label(info.getC(), info.getR() + 1,
							cellContent, sheet
									.getCell(info.getC(), info.getR())
									.getCellFormat());
					try {
						sheet.addCell(label);
					} catch (Exception e) {
						System.out.println("添加excle数据错误：processList()");
						e.printStackTrace();
					}
				}
			}
			sheet.removeRow(elAtLine);
			return;
		} finally {
			list = null;
		}
	}

	/**
	 * 把pojoData转换本身实际的数据类型
	 * 
	 * @param pojoData
	 * @param elName
	 * @return 实际数据类型
	 */
	private String value2String(Object pojoData, String elName) {
		String result = "";
		try {
			result = BeanUtils.getProperty(pojoData, elName) == null ? ""
					: BeanUtils.getProperty(pojoData, elName);
			Pattern p = Pattern
					.compile("[\\w]{1,4} [\\w]{1,4} \\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2} CST \\d{4}");// "Wed Nov 12 16:45:36 CST 2008"这种格式
			Matcher m = p.matcher(result);
			if (m.find()) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
				Date d = sdf.parse(result);
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				result = sdf.format(d);
			}

			return result;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 初始化所有单元格有含EL表达式的放到Map中以便处理
	 * 
	 * @param sheet
	 * @return 单元信息
	 */
	private Map<String, List<ELInCellInfo>> initExcelCellContents2Map(
			WritableSheet sheet) {
		int rowCnt = sheet.getRows();
		int colCnt = sheet.getColumns();
		Map<String, List<ELInCellInfo>> elMap = new HashMap<String, List<ELInCellInfo>>();// key,el所在格子的el表达式
		try {
			for (int r = 0; r < rowCnt; r++) {
				for (int c = 0; c < colCnt; c++) {
					String content = sheet.getCell(c, r).getContents() == null ? ""
							: sheet.getCell(c, r).getContents();
					getEl2ELMap(content, elMap, c, r);
				}
			}
			return elMap;
		} finally {
			elMap = null;
		}
	}

	/**
	 * @author Administrator ELInCellInfo
	 */
	private static class ELInCellInfo {
		private int r;// row
		private int c;// column
		private String[] elInfoArray = new String[] {};// 对应于r,c的单元格所有的EL表达式内容

		public int getR() {
			return r;
		}

		public void setR(int r) {
			this.r = r;
		}

		public int getC() {
			return c;
		}

		public void setC(int c) {
			this.c = c;
		}

		public String[] getElInfoArray() {
			return elInfoArray;
		}

		public void setElInfoArray(String[] elInfoArray) {
			this.elInfoArray = elInfoArray;
		}

	}

	/**
	 * 取得每个格子中有几个el表达式并保存
	 * 
	 * @param str
	 *            el表达式字符串
	 * @param resultMap
	 *            结果集
	 * @param r
	 *            行
	 * @param c
	 *            列
	 */
	private void getEl2ELMap(String str,
			Map<String, List<ELInCellInfo>> resultMap, int c, int r) {
		Pattern p = Pattern.compile("#\\{\\s*[\\w\\._-]+\\s*\\}");
		Matcher m = p.matcher(str);
		int start = 0;
		boolean isExist = m.find(start);
		if (!isExist) {
			return;
		}
		String key = m.group().replaceAll("\\s", "").replace("#{", "").replace(
				"}", "").split("\\.")[0];// replaceAll("#{\\s",
		// "").replaceAll("\\s\\}",
		// "").replaceAll("\\s",
		// "").split("\\.")[0];
		List<ELInCellInfo> list = resultMap.get(key);
		if (resultMap.get(key) == null) {
			list = new ArrayList<ELInCellInfo>();
		}
		List<String> elList = new ArrayList<String>();
		while (isExist) {
			start = m.start() + 1;
			String elStr = m.group().replaceAll("\\s", "").replace("#{", "")
					.replace("}", "");
			elStr = elStr.replace(key + ".", "");// 去掉key字串,只保留key以外的属性名称
			elList.add(elStr);
			isExist = m.find(start);
		}
		ELInCellInfo el = new ELInCellInfo();
		el.setC(c);
		el.setR(r);
		el.setElInfoArray(elList.toArray(new String[0]));
		list.add(el);
		resultMap.put(key, list);
		elList = null;
		el = null;
	}

	public static void main(String[] args) throws Exception {
		// String tXls="/com/saicmotor/framework/util/SuppTemplate.xls";
		// FileOutputStream fos=new FileOutputStream("d:\\supp.xls");
		// print2Excel(tXls,"supp",null,fos);
		// System.out.println("ok");#{plist.partnumber }
		String key = "plist";
		String propertyName = "partnumber";
		String ex = "#\\{\\s*plist\\.linenumber\\s*\\}";
		String str = "#{plist.linenumber}";
		System.out.println(str.replaceAll(ex, "10ee"));
	}
}
