package wjm.query.loader;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import wjm.common.exception.DataStoreException;
import wjm.common.util.QConst;
import wjm.common.util.SpringUtil;
import wjm.query.data.DataStore;

public class DictionaryLoader {
	private static final Logger log = Logger.getLogger(DictionaryLoader.class);
	private static final String LOADERNAME = "dictionaryLoader";
	private Map<String, Map<String, String>> dictmap;
	private DataStore dataStore;

	public  boolean load() {
		dictmap = new HashMap<String, Map<String, String>>();
		String typesql = "select dicttp from sys_dicttp order by seqno asc";
		List<Map<String, String>> list;
		int count = 0;
		try {
			list = this.dataStore.selectMapList(typesql);
			String dictp;
			for (int i = 0; i < list.size(); i++) {
				dictp = list.get(i).get("DICTTP");
				loadSingleDict(dictp);
				count++;
			}
			count += loadAddon();
			log.info("数据字典加载完成[" + (count) + "]");
			return true;
		} catch (DataStoreException e) {
			log.error("数据字典加载失败", e);
		}
		return false;
	}

	/**
	 * 加载非字典表里面存的字典项，比如表明，所有字典类型
	 * @return
	 * @throws DataStoreException
	 */
	public int loadAddon() throws DataStoreException {
		int count = 0;
		// 将字典种类也添加进来
		String adddicttypes = "select dicttp key,tpname value from sys_dicttp order by seqno asc";
		loadBySql(QConst.SYS_DICTID_DICTTYPE, adddicttypes);
		dictmap.get(QConst.SYS_DICTID_DICTTYPE).put(QConst.SYS_DICTID_DICTTYPE, "数据字典类型列表");
		count++;
		String addqueryconfs = "select distinct queryid key,querycomment value from sys_queryconf order by queryid asc";
		loadBySql(QConst.SYS_DICTID_ALLCONFS, addqueryconfs);
		count++;
		return count;
	}

	public boolean loadSingleDict(String dictp) throws DataStoreException {
		String dicsql;
		dicsql = "select key,value from sys_dict where dicttp='" + dictp + "' order by seqno asc,key asc,value desc";
		loadBySql(dictp, dicsql);
		return true;
	}

	private  void loadBySql(String dictp, String dicsql) throws DataStoreException {
		List<Map<String, String>> buflist;
		Map<String, String> map;
		buflist = dataStore.selectMapList(dicsql);
		map = new LinkedHashMap<String, String>();//此处必须实例化LinkedHashMap,否则排序将失效
		for (int k = 0; k < buflist.size(); k++) {
			map.put(buflist.get(k).get("KEY"), buflist.get(k).get("VALUE"));
		}
		dictmap.put(dictp, map);
	}

	public static DictionaryLoader instance() {
		return (DictionaryLoader) SpringUtil.findBean(LOADERNAME);
	}

	public DataStore getDataStore() {
		return dataStore;
	}

	public void setDataStore(DataStore dataStore) {
		this.dataStore = dataStore;
	}

	public String getValueByTPAndKey(String dicttp, String key) {
		if (dictmap.get(dicttp) == null) {
			return key + "-警告:调用了数据字典但是没有成功翻译。";
		}
		return this.dictmap.get(dicttp).get(key);
	}

	public Map<String, String> getEntryByTP(String dicttp) {
		return this.dictmap.get(dicttp);
	}

}
