/**
 * 
 */
package wjm.query.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wjm.common.util.StringUtil;

/**
 * @author WJM 2013-5-29 上午10:19:36
 */
public class QueryConf {
	public static final String FIELDTYPE_BOTH = "both";
	public static final String FIELDTYPE_OUTPUT = "output";
	public static final String FIELDTYPE_CONDITION = "condition";
	private SysQueryBO bo;
	private Map<String, String> tableAlias;
	private List<SysQueryFieldBO> fieldList;
	private Map<String, SysQueryFieldBO> fieldMap;

	public Map<String, String> getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(Map<String, String> tableAlias) {
		this.tableAlias = tableAlias;
	}

	public List<SysQueryFieldBO> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<SysQueryFieldBO> fieldList) {
		this.fieldList = fieldList;
		this.fieldMap = this.genFieldMap();
	}

	/**
	 * @return SysQueryFieldBO的Map，key值为getColalias的大写
	 */
	private Map<String, SysQueryFieldBO> genFieldMap() {
		Map<String, SysQueryFieldBO> map = new HashMap<String, SysQueryFieldBO>();
		if (fieldList == null) {
			return map;
		}
		for (int i = 0; i < fieldList.size(); i++) {
			map.put(StringUtil.upper(fieldList.get(i).getId().getColalias()), fieldList.get(i));
		}
		return map;
	}

	/**
	 * 获取包含Order by字段的Field
	 * 
	 * @param queryid
	 * @return
	 */
	public List<SysQueryFieldBO> getOrderByField() {
		List<SysQueryFieldBO> ret = new ArrayList<SysQueryFieldBO>();
		if (fieldList == null) {
			return ret;
		}
		for (int i = 0; i < fieldList.size(); i++) {
			if (!StringUtil.isEmpty(fieldList.get(i).getOrderby())) {
				ret.add(fieldList.get(i));
			}
		}
		return ret;
	}

	/**
	 * @param fieldtype类型both
	 *            ,output,condition
	 * @return
	 */
	public List<SysQueryFieldBO> getFieldListByFieldType(String fieldtype) {
		List<SysQueryFieldBO> ret = new ArrayList<SysQueryFieldBO>();
		if (fieldList == null || fieldtype == null) {
			return ret;
		}
		for (int i = 0; i < fieldList.size(); i++) {
			if (fieldtype.equals(fieldList.get(i).getFieldtype())) {
				ret.add(fieldList.get(i));
			} else if (FIELDTYPE_BOTH.equals(fieldList.get(i).getFieldtype())) {
				ret.add(fieldList.get(i));
			}
		}
		return ret;
	}

	public SysQueryFieldBO getByColailas(String colalias) {
		return fieldMap.get(colalias);
	}

	public SysQueryBO getBo() {
		return bo;
	}

	public void setBo(SysQueryBO bo) {
		this.bo = bo;
	}
}
