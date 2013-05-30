package wjm.query.meta;

import java.math.BigDecimal;

import wjm.query.common.base.BaseBO;

/**
 * 对应表SYS_QUERYFIELD
 * SysQueryFieldBO entity. @author MyEclipse Persistence Tools
 */

public class SysQueryFieldBO extends BaseBO {

	// Fields

	/**
	 * 联合主键名必须叫ID
	 */
	private SysQueryFieldBOId id;
	private String tabname;
	private String joinway;
	private String fieldtype;
	private BigDecimal fieldorder;
	private String colrealname;
	private String colcomment;
	private String ctrltype;
	private BigDecimal ctrllen;
	private BigDecimal displen;
	private String validator;
	private String disptype;
	private String dicttype;
	private String css;
	private String orderby;
	private String opper;
	private String operand;
	private String ispk;

	// Constructors

	/** default constructor */
	public SysQueryFieldBO() {
	}

	/** minimal constructor */
	public SysQueryFieldBO(SysQueryFieldBOId id, String tabname, String fieldtype, String colrealname) {
		this.id = id;
		this.tabname = tabname;
		this.fieldtype = fieldtype;
		this.colrealname = colrealname;
	}

	/** full constructor */
	public SysQueryFieldBO(SysQueryFieldBOId id, String tabname, String joinway, String fieldtype,
			BigDecimal fieldorder, String colrealname, String colcomment, String ctrltype, BigDecimal ctrllen,
			BigDecimal displen, String disptype, String dicttype, String css, String orderby, String opper,String operand) {
		this.id = id;
		this.tabname = tabname;
		this.joinway = joinway;
		this.fieldtype = fieldtype;
		this.setFieldorder(fieldorder);
		this.colrealname = colrealname;
		this.colcomment = colcomment;
		this.ctrltype = ctrltype;
		this.ctrllen = ctrllen;
		this.displen = displen;
		this.disptype = disptype;
		this.dicttype = dicttype;
		this.css = css;
		this.orderby = orderby;
		this.opper = opper;
		this.operand = operand;
	}

	// Property accessors

	public SysQueryFieldBOId getId() {
		return this.id;
	}

	public void setId(SysQueryFieldBOId id) {
		this.id = id;
	}

	public String getTabname() {
		return this.tabname;
	}

	public void setTabname(String tabname) {
		this.tabname = tabname;
	}

	public String getJoinway() {
		return this.joinway;
	}

	public void setJoinway(String joinway) {
		this.joinway = joinway;
	}

	public String getFieldtype() {
		return this.fieldtype;
	}

	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}


	public String getColrealname() {
		return this.colrealname;
	}

	public void setColrealname(String colrealname) {
		this.colrealname = colrealname;
	}

	public String getColcomment() {
		return this.colcomment;
	}

	public void setColcomment(String colcomment) {
		this.colcomment = colcomment;
	}

	public String getCtrltype() {
		return this.ctrltype;
	}

	public void setCtrltype(String ctrltype) {
		this.ctrltype = ctrltype;
	}

	public BigDecimal getCtrllen() {
		return this.ctrllen;
	}

	public void setCtrllen(BigDecimal ctrllen) {
		this.ctrllen = ctrllen;
	}

	public BigDecimal getDisplen() {
		return this.displen;
	}

	public void setDisplen(BigDecimal displen) {
		this.displen = displen;
	}

	public String getDisptype() {
		return this.disptype;
	}

	public void setDisptype(String disptype) {
		this.disptype = disptype;
	}

	public String getDicttype() {
		return this.dicttype;
	}

	public void setDicttype(String dicttype) {
		this.dicttype = dicttype;
	}

	public String getCss() {
		return this.css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getOrderby() {
		return this.orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String getOpper() {
		return this.opper;
	}

	public void setOpper(String opper) {
		this.opper = opper;
	}

	public String getOperand() {
		return operand;
	}

	public void setOperand(String operand) {
		this.operand = operand;
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public BigDecimal getFieldorder() {
		return fieldorder;
	}

	public void setFieldorder(BigDecimal fieldorder) {
		this.fieldorder = fieldorder;
	}

	public String getIspk() {
		return ispk;
	}

	public void setIspk(String ispk) {
		this.ispk = ispk;
	}

}