/**
 * 
 */
package wjm.query.meta;

import wjm.query.common.base.BaseBO;


/**
 * 对应表SYS_QUERY
 * @author WJM 2013-5-29 上午10:19:36
 */
public class SysQueryBO extends BaseBO {
	private String queryid;
	private String queryname;
	private String pqueryid;
	private String querytype;
	private String remark;
	public String getQueryid() {
		return queryid;
	}
	public void setQueryid(String queryid) {
		this.queryid = queryid;
	}
	public String getQueryname() {
		return queryname;
	}
	public void setQueryname(String queryname) {
		this.queryname = queryname;
	}
	public String getPqueryid() {
		return pqueryid;
	}
	public void setPqueryid(String pqueryid) {
		this.pqueryid = pqueryid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getQuerytype() {
		return querytype;
	}
	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}
}
