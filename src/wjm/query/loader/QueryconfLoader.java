package wjm.query.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import wjm.common.exception.DataStoreException;
import wjm.common.util.SpringUtil;
import wjm.common.util.StringUtil;
import wjm.query.data.DataStore;
import wjm.query.meta.SysQueryconfBO;

public class QueryconfLoader {
	public static final String CONFTYPE_BOTH = "both";
	public static final String CONFTYPE_OUTPUT = "output";
	public static final String CONFTYPE_CONDITION = "condition";
	private static final String LOADERNAME = "queryconfLoader";
	private static final Logger log = Logger.getLogger(QueryconfLoader.class);
	private Map<String, List<SysQueryconfBO>> confmap;
	private Map<String, String> queryComments;
	private DataStore dataStore;
	private Map<String,Map<String,String>> tablesAlias;

	public QueryconfLoader() {
		confmap = new HashMap<String, List<SysQueryconfBO>>();
		queryComments = new HashMap<String, String>();
		tablesAlias = new HashMap<String, Map<String,String>>();
	}

	public static QueryconfLoader instance() {
		return (QueryconfLoader) SpringUtil.findBean(LOADERNAME);
	}

	/**
	 * 加载所有
	 * @return
	 */
	public  boolean load() {
		List<Map<String, String>> list;
		try {
			list = dataStore.selectMapList("select distinct queryid from sys_queryconf");
			Map<String, String> map;
			for (int i = 0; i < list.size(); i++) {
				map = list.get(i);
				loadSingle(map.get("QUERYID"));
			}
			log.info("查询配置表加载完成[" + list.size() + "]");
			return true;
		} catch (DataStoreException e) {
			log.error("查询配置加载失败", e);
		}
		return false;
	}

	/**
	 * 加载一个
	 * @param queryid
	 * @throws DataStoreException
	 */
	public  boolean loadSingle(String queryid) throws DataStoreException {
		String sqlbuf;
		String querycomment;
		sqlbuf = "select * from sys_queryconf where queryid='" + queryid + "' order by conforder asc";
		List<SysQueryconfBO> entry = dataStore.selectBOList(sqlbuf, SysQueryconfBO.class);
		confmap.put(queryid, entry);
		if (entry.size() > 0) {
			querycomment = entry.get(0).getQuerycomment();
			queryComments.put(queryid, querycomment);
		}
		tablesAlias.put(queryid, initTablesAlias(queryid));
		return true;
	}

	public DataStore getDataStore() {
		return dataStore;
	}

	public void setDataStore(DataStore dataStore) {
		this.dataStore = dataStore;
	}

	/**
	 * 找出一个查询配置的表，并返回表与表别名
	 * 
	 * @param queryid
	 * @return
	 */
	public Map<String, String> initTablesAlias(String queryid) {
		List<Map<String, String>> list;
		Map<String, String> ret = new HashMap<String, String>();
		try {
			list = dataStore
					.selectMapList("select distinct tabname from sys_queryconf where queryid='" + queryid + "'");
			for (int i = 0; i < list.size(); i++) {
				ret.put(list.get(i).get("TABNAME"), "" + (char) ('a' + i));
			}
		} catch (DataStoreException e) {
			log.error("", e);
		}
		return ret;
	}
	/**
	 * 找出一个查询配置的表，并返回表与表别名
	 * 
	 * @param queryid
	 * @return
	 */
	public Map<String, String> getTablesAlias(String queryid) {
		return tablesAlias.get(queryid);
	}

	/**
	 * 
	 * @param queryid
	 * @return 返回map key值为getColalias的大写值
	 */
	public Map<String, SysQueryconfBO> getConfMapByQueryid(String queryid) {
		List<SysQueryconfBO> list = getConfListByQueryid(queryid);
		Map<String, SysQueryconfBO> map = new HashMap<String, SysQueryconfBO>();
		if (list == null) {
			return map;
		}
		for (int i = 0; i < list.size(); i++) {
			map.put(StringUtil.upper(list.get(i).getId().getColalias()), list.get(i));
		}
		return map;
	}

	/**
	 * 获取Order by字段列表
	 * 
	 * @param queryid
	 * @return
	 */
	public List<SysQueryconfBO> getOrderByConf(String queryid) {
		List<SysQueryconfBO> list = getConfListByQueryid(queryid);
		List<SysQueryconfBO> ret = new ArrayList<SysQueryconfBO>();
		if (list == null) {
			return ret;
		}
		for (int i = 0; i < list.size(); i++) {
			if (!StringUtil.isEmpty(list.get(i).getOrderby())) {
				ret.add(list.get(i));
			}
		}
		return ret;
	}

	/**
	 * @param queryid
	 *            查询配置id
	 * @param conftype
	 *            配置类型
	 * @return
	 */
	public List<SysQueryconfBO> getConfListByConfType(String queryid, String conftype) {
		List<SysQueryconfBO> list = getConfListByQueryid(queryid);
		List<SysQueryconfBO> ret = new ArrayList<SysQueryconfBO>();
		if (list == null || conftype == null) {
			return ret;
		}
		for (int i = 0; i < list.size(); i++) {
			if (conftype.equals(list.get(i).getConftype())) {
				ret.add(list.get(i));
			} else if (CONFTYPE_BOTH.equals(list.get(i).getConftype())) {
				ret.add(list.get(i));
			}
		}
		return ret;
	}

	public List<SysQueryconfBO> getConfListByQueryid(String queryid) {
		return confmap.get(queryid);
	}

	public String getQueryName(String queryid) {
		String name = queryComments.get(queryid);
		log.info(queryid + "===" + name);
		return name;
	}

}
