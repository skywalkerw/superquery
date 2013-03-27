package wjm.query.meta;

import wjm.query.common.base.BaseBO;

/**
 * SysQueryconfBOId entity. @author MyEclipse Persistence Tools
 */

public class SysQueryconfBOId extends BaseBO{

	// Fields

	private String queryid;
	private String colalias;

	// Constructors

	/** default constructor */
	public SysQueryconfBOId() {
	}

	/** full constructor */
	public SysQueryconfBOId(String queryid, String colalias) {
		this.queryid = queryid;
		this.colalias = colalias;
	}

	// Property accessors

	public String getQueryid() {
		return this.queryid;
	}

	public void setQueryid(String queryid) {
		this.queryid = queryid;
	}

	public String getColalias() {
		return this.colalias;
	}

	public void setColalias(String colalias) {
		this.colalias = colalias;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SysQueryconfBOId))
			return false;
		SysQueryconfBOId castOther = (SysQueryconfBOId) other;

		return ((this.getQueryid() == castOther.getQueryid()) || (this.getQueryid() != null
				&& castOther.getQueryid() != null && this.getQueryid().equals(castOther.getQueryid())))
				&& ((this.getColalias() == castOther.getColalias()) || (this.getColalias() != null
						&& castOther.getColalias() != null && this.getColalias().equals(castOther.getColalias())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getQueryid() == null ? 0 : this.getQueryid().hashCode());
		result = 37 * result + (getColalias() == null ? 0 : this.getColalias().hashCode());
		return result;
	}

}