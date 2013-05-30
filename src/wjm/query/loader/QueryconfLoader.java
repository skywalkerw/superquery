package wjm.query.loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import wjm.common.exception.DataStoreException;
import wjm.common.exception.SuperQueryException;
import wjm.common.util.SpringUtil;
import wjm.common.util.StringUtil;
import wjm.query.data.DataStore;
import wjm.query.meta.QueryConf;
import wjm.query.meta.SysQueryBO;
import wjm.query.meta.SysQueryFieldBO;

public class QueryconfLoader {
	private static final String LOADERNAME = "queryconfLoader";
	private static final Logger log = Logger.getLogger(QueryconfLoader.class);
	private Map<String, QueryConf> confmap;
	private DataStore dataStore;

	public QueryconfLoader() {
		confmap = new HashMap<String, QueryConf>();
	}

	public static QueryconfLoader instance() {
		return (QueryconfLoader) SpringUtil.findBean(LOADERNAME);
	}

	public DataStore getDataStore() {
		return dataStore;
	}

	public void setDataStore(DataStore dataStore) {
		this.dataStore = dataStore;
	}

	/**
	 * 加载所有
	 * 
	 * @return
	 */
	public boolean load() {
		try {
			List<SysQueryBO> list = dataStore.selectBOList("select * from sys_query", SysQueryBO.class);
			QueryConf conf;
			SysQueryBO bo;
			for (int i = 0; i < list.size(); i++) {
				conf = new QueryConf();
				bo = list.get(i);
				conf = buildConfBean(bo);
				confmap.put(conf.getBo().getQueryid(), conf);
			}
			log.info("查询配置表加载完成[" + list.size() + "]");
			return true;
		} catch (DataStoreException e) {
			log.error("查询配置加载失败", e);
		}
		return false;
	}

	private QueryConf buildConfBean(SysQueryBO bo) throws DataStoreException {
		String queryid = bo.getQueryid();
		QueryConf conf = new QueryConf();
		log.debug("加载：" + queryid);
		conf.setBo(bo);
		conf.setFieldList(loadFields(queryid));
		conf.setTableAlias(initTablesAlias(queryid));
		return conf;
	}

	/**
	 * 加载一个
	 * 
	 * @param queryid
	 * @throws DataStoreException
	 */
	private List<SysQueryFieldBO> loadFields(String queryid) throws DataStoreException {
		String sqlbuf;
		sqlbuf = "select * from sys_queryfield where queryid='" + queryid + "' order by fieldorder asc";
		List<SysQueryFieldBO> entry = dataStore.selectBOList(sqlbuf, SysQueryFieldBO.class);
		return entry;
	}

	/**
	 * 找出一个查询配置的表，并返回表与表别名
	 * 
	 * @param queryid
	 * @return
	 */
	private Map<String, String> initTablesAlias(String queryid) {
		List<Map<String, String>> list;
		Map<String, String> ret = new HashMap<String, String>();
		try {
			list = dataStore.selectMapList("select distinct tabname from sys_queryfield where queryid='" + queryid
					+ "'");
			for (int i = 0; i < list.size(); i++) {
				ret.put(list.get(i).get("TABNAME"), "" + (char) ('a' + i));
			}
		} catch (DataStoreException e) {
			log.error("", e);
		}
		return ret;
	}

	public QueryConf getConf(String queryid) {
		return confmap.get(queryid);
	}

	public boolean refeshFields(String queryid) throws SuperQueryException {
		if (StringUtil.isEmpty(queryid)) {
			throw new SuperQueryException("刷新配置时，查询ID为空！");
		}
		try {
			List<SysQueryBO> list = dataStore.selectBOList("select * from sys_query where queryid='" + queryid + "'",
					SysQueryBO.class);
			QueryConf conf;
			SysQueryBO bo;
			for (int i = 0; i < list.size(); i++) {
				conf = new QueryConf();
				bo = list.get(i);
				conf = buildConfBean(bo);
				confmap.put(conf.getBo().getQueryid(), conf);
			}
			return true;
		} catch (DataStoreException e) {
			throw new SuperQueryException("", e);
		}
	}
}
