package wjm.query.page;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import wjm.common.util.StringUtil;
import wjm.query.loader.DictionaryLoader;

/**
 * HTML 原子标签
 * @author WJM 
 * 2013-5-31 下午2:47:50
 */
public class ControlUtil {
	private static final Logger log = Logger.getLogger(ControlUtil.class);

	public static StringBuffer makeInput(Object value, String ctrlname, String type) {
		StringBuffer ret = new StringBuffer();
		value = StringUtil.trim(value);
		ret.append("<input type='").append(type).append("' value='").append(value).append("' ");
		ret.append("name='").append(ctrlname).append("' ");
		ret.append("></input>");
		return ret;
	}

	/**
	 * @param map
	 * @param id
	 * @param name
	 * @param needWrite
	 *            是否加一条空白
	 * @return
	 */
	public static StringBuffer makeSelect(Map<String, String> map, String id, String name, String value,
			boolean needWrite) {
		id = StringUtil.trim(id);
		StringBuffer ret = new StringBuffer();
		ret.append("<select id='").append(id).append("' name='").append(name).append("'>");
		ret.append(makeOptionsByMap(map, value, needWrite));
		ret.append("</select>");
		log.debug(ret);
		return ret;
	}

	/**
	 * 
	 * @param map
	 * @param value
	 * @param needWrite
	 * @return
	 */
	public static StringBuffer makeOptionsByMap(Map<String, String> map, String value, boolean needWrite) {
		StringBuffer ret = new StringBuffer();
		if (map == null) {
			return ret;
		}
		value = StringUtil.trim(value);
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		Entry<String, String> entry;
		if (needWrite) {
			ret.append("<option value=''>");
			ret.append("[空]");
			ret.append("</option>\n");
		}
		while (it.hasNext()) {
			entry = it.next();
			// <option value="SYS_QUERYCONF">SYS_QUERYCONF查询配置表</option>
			ret.append("<option value='").append(entry.getKey()).append("'");
			if (value.equals(entry.getKey())) {
				ret.append(" selected='selected'");
			}
			ret.append(">");
			ret.append(entry.getKey() == null ? "" : entry.getKey());
			ret.append(entry.getValue() == null ? "" : "-" + entry.getValue());
			ret.append("</option>\n");
		}
		// log.debug(ret);
		return ret;
	}

	/**
	 * 生成HTML oprion 标签列表
	 * 
	 * @param dictType
	 * @param needWrite
	 * @return
	 */
	public static StringBuffer makeOptionsByDict(String dictType, String value, boolean needWrite) {
		Map<String, String> map = DictionaryLoader.instance().getEntryByTP(dictType);
		return makeOptionsByMap(map, value, needWrite);
	}

}
