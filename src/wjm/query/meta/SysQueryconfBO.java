package wjm.query.meta;

import java.math.BigDecimal;

import wjm.query.common.base.BaseBO;

/**
 * SysQueryconfBO entity. @author MyEclipse Persistence Tools
 */

public class SysQueryconfBO extends BaseBO {

	// Fields

	/**
	 * 联合主键名必须叫ID
	 */
	private SysQueryconfBOId id;
	private String querycomment;
	private String tabname;
	private String joinway;
	private String conftype;
	private BigDecimal conforder;
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
	public SysQueryconfBO() {
	}

	/** minimal constructor */
	public SysQueryconfBO(SysQueryconfBOId id, String tabname, String conftype, String colrealname) {
		this.id = id;
		this.tabname = tabname;
		this.conftype = conftype;
		this.colrealname = colrealname;
	}

	/** full constructor */
	public SysQueryconfBO(SysQueryconfBOId id, String querycomment, String tabname, String joinway, String conftype,
			BigDecimal conforder, String colrealname, String colcomment, String ctrltype, BigDecimal ctrllen,
			BigDecimal displen, String disptype, String dicttype, String css, String orderby, String opper,String operand) {
		this.id = id;
		this.querycomment = querycomment;
		this.tabname = tabname;
		this.joinway = joinway;
		this.conftype = conftype;
		this.setConforder(conforder);
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

	public SysQueryconfBOId getId() {
		return this.id;
	}

	public void setId(SysQueryconfBOId id) {
		this.id = id;
	}

	public String getQuerycomment() {
		return this.querycomment;
	}

	public void setQuerycomment(String querycomment) {
		this.querycomment = querycomment;
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

	public String getConftype() {
		return this.conftype;
	}

	public void setConftype(String conftype) {
		this.conftype = conftype;
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

	public BigDecimal getConforder() {
		return conforder;
	}

	public void setConforder(BigDecimal conforder) {
		this.conforder = conforder;
	}

	public String getIspk() {
		return ispk;
	}

	public void setIspk(String ispk) {
		this.ispk = ispk;
	}

}