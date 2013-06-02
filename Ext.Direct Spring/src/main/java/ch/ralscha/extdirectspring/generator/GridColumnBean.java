package ch.ralscha.extdirectspring.generator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class GridColumnBean {
	private String text ;
	private String dataIndex;
	private String xtype;
	public GridColumnBean(String text, String dataIndex, String xtype) {
		super();
		this.text = text;
		this.dataIndex = dataIndex;
		this.xtype = xtype;
	}
	private String format;
	private String tpl;
	private Integer columnWidth;
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the dataIndex
	 */
	public String getDataIndex() {
		return dataIndex;
	}
	/**
	 * @param dataIndex the dataIndex to set
	 */
	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}
	/**
	 * @return the xtype
	 */
	public String getXtype() {
		return xtype;
	}
	/**
	 * @param xtype the xtype to set
	 */
	public void setXtype(String xtype) {
		this.xtype = xtype;
	}
	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * @return the tpl
	 */
	public String getTpl() {
		return tpl;
	}
	/**
	 * @param tpl the tpl to set
	 */
	public void setTpl(String tpl) {
		this.tpl = tpl;
	}
	/**
	 * @return the columnWidth
	 */
	public Integer getColumnWidth() {
		return columnWidth;
	}
	/**
	 * @param columnWidth the columnWidth to set
	 */
	public void setColumnWidth(Integer columnWidth) {
		this.columnWidth = columnWidth;
	}

}
