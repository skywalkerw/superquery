package wjm.common.util;

public class QConst {
	/**
	 * 错误码 查询配置没找到
	 */
	public static final int ERRCODE_QUERYNOTFOUNT = 100;
	/**
	 * 查询结果显示最大数
	 */
	public static final int MAX_QUERYPAGE_SIZE = 2000;
	/**
	 * 是否显示行号
	 */
	public static final boolean SHOW_ROWNO = true;
	/**
	 * 输出URL后缀
	 */
	public static final String ACTION_SUFFIX_OUTPUT = ".out";
	/**
	 * 查询请求URL后缀
	 */
	public static final String ACTION_SUFFIX_QUERY = ".qry";
	public static final String ACTION_SUFFIX_CONF = ".cfg";
	/**
	 * 查询id，查表结构用
	 */
	public static final String SYS_QUERYID_TABLESTURCT = "query_tabstruct";
	/**
	 * 查询id：列出所有表
	 */
	public static final String SYS_QUERYID_ALLTABLES = "query_alltables";
	/**
	 * 查询id：列出所有表
	 */
	public static final String SYS_QUERYID_QUERYCONF = "query_queryconf";
	/**
	 * 查询配置表表明
	 */
	public static final String SYS_QUERYCONF_TABLENAME = "sys_queryconf";
	
	/**
	 * 所有数据字典类型
	 */
	public static final String SYS_DICTID_DICTTYPE ="alldicttypes";
	/**
	 * 所有表名
	 */
	public static final String SYS_DICTID_ALLTABLES ="alltables";
	/**
	 * 所有查询配置
	 */
	public static final String SYS_DICTID_ALLCONFS ="allconfs";
	
	public static final String CONTEXTPATH ="/superquery/";
	/**
	 * 控件类型none
	 */
	public static final String CTRLTYPE_NONE = "none";
	/**
	 * 显示类型none
	 */
	public static final String DISPTYPE_NONE = "none";
}
 