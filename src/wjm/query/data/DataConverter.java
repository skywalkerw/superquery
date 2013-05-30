package wjm.query.data;

import org.apache.log4j.Logger;

import wjm.common.util.StringUtil;
import wjm.query.loader.DictionaryLoader;
import wjm.query.meta.SysQueryFieldBO;

/**
 * 用户数据格式转换
 * 
 * @author Administrator WJM 2012-12-13下午7:13:02
 */
public class DataConverter {
	private static final Logger log = Logger.getLogger(DataConverter.class);
	private static final boolean CONTAINSKEY = true;
	public static String convert(Object data, SysQueryFieldBO conf) {
		if (data == null) {
			return "";
		}
		String datastr = data.toString();
		if(conf == null){
			return datastr;
		}
		String tp = conf.getDisptype();
		if("dict".equals(tp)){
			String key = datastr;
			DictionaryLoader dl = DictionaryLoader.instance();
			if(conf.getDicttype()==null){
				log.warn("["+conf.getId().getQueryid()+"]["+conf.getId().getColalias()+"]显示类型为dict但是没有配置数据字典类型！");
			}
			String value = dl.getValueByTPAndKey(conf.getDicttype(), key);
			if(!StringUtil.isEmpty(value)){
				if(CONTAINSKEY){
					value = key+"-"+value;
				}
				datastr = value;
			}
		}
		return datastr;
	}

}
