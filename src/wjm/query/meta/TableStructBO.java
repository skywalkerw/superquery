package wjm.query.meta;

import java.math.BigDecimal;

import wjm.query.common.base.BaseBO;

/**
 * 对应查询query_tablestruct的输出结果
 * @author Administrator WJM 2012-12-11下午6:24:39
 */
public class TableStructBO extends BaseBO {
	private static final long serialVersionUID = 8028649985177449924L;
	/**
	 * 所属表名称
	 */
	private String tabname;
	/**
	 * 表注释
	 */
	private String tabcomment;
	/**
	 * 列标识
	 */
	private BigDecimal colid;
	/**
	 * 列名称
	 */
	private String colname;
	/**
	 * 数据类型
	 */
	private String datatype;
	/**
	 * 长度
	 */
	private BigDecimal datalength;
	/**
	 * 整数长度
	 */
	private BigDecimal dataprecision;
	/**
	 * 小数长度
	 */
	private BigDecimal datascale;
	/**
	 * 列注释
	 */
	private String colcnmment;

	public String getTabname() {
		return tabname;
	}

	public void setTabname(String tabname) {
		this.tabname = tabname;
	}

	public String getTabcomment() {
		return tabcomment;
	}

	public void setTabcomment(String tabcomment) {
		this.tabcomment = tabcomment;
	}

	public BigDecimal getColid() {
		return colid;
	}

	public void setColid(BigDecimal colid) {
		this.colid = colid;
	}

	public String getColname() {
		return colname;
	}

	public void setColname(String colname) {
		this.colname = colname;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public BigDecimal getDatalength() {
		return datalength;
	}

	public void setDatalength(BigDecimal datalength) {
		this.datalength = datalength;
	}

	public BigDecimal getDataprecision() {
		return dataprecision;
	}

	public void setDataprecision(BigDecimal dataprecision) {
		this.dataprecision = dataprecision;
	}

	public BigDecimal getDatascale() {
		return datascale;
	}

	public void setDatascale(BigDecimal datascale) {
		this.datascale = datascale;
	}

	public String getColcnmment() {
		return colcnmment;
	}

	public void setColcnmment(String colcnmment) {
		this.colcnmment = colcnmment;
	}

	

}
