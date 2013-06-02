package ch.ralscha.extdirectspring.generator;

import org.hibernate.validator.constraints.NotEmpty;

public class Company {
	@GridColumn(text = "ID")
	private Long ID;
	@NotEmpty
	@GridColumn(text = "公司名称")
	private String compName;
	@GridColumn(text = "公司代码")
	private String compCode;

	/**
	 * @return the iD
	 */

	public Long getID() {
		return ID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(Long iD) {
		ID = iD;
	}

	/**
	 * @return the compName
	 */
	public String getCompName() {
		return compName;
	}

	/**
	 * @param compName
	 *            the compName to set
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	 * @return the compCode
	 */
	public String getCompCode() {
		return compCode;
	}

	/**
	 * @param compCode
	 *            the compCode to set
	 */
	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

}
