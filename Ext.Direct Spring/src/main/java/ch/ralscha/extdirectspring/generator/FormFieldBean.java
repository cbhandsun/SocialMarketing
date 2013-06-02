package ch.ralscha.extdirectspring.generator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class FormFieldBean {
	private String name;
	private String fieldLabel;
	private boolean allowBlank;
	private String vtype;
	private String vtypeText;
	private int columnWidth;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the fieldLabel
	 */
	public String getFieldLabel() {
		return fieldLabel;
	}
	/**
	 * @param fieldLabel the fieldLabel to set
	 */
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	/**
	 * @return the allowBlank
	 */
	public boolean isAllowBlank() {
		return allowBlank;
	}
	/**
	 * @param allowBlank the allowBlank to set
	 */
	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
	}
	/**
	 * @return the vtype
	 */
	public String getVtype() {
		return vtype;
	}
	/**
	 * @param vtype the vtype to set
	 */
	public void setVtype(String vtype) {
		this.vtype = vtype;
	}
	/**
	 * @return the vtypeText
	 */
	public String getVtypeText() {
		return vtypeText;
	}
	/**
	 * @param vtypeText the vtypeText to set
	 */
	public void setVtypeText(String vtypeText) {
		this.vtypeText = vtypeText;
	}
	/**
	 * @return the columnWidth
	 */
	public int getColumnWidth() {
		return columnWidth;
	}
	/**
	 * @param columnWidth the columnWidth to set
	 */
	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
	}
}
