package wjm.query.common.base;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.util.LinkedCaseInsensitiveMap;

import wjm.common.exception.SuperQueryException;
import wjm.common.util.StringUtil;

/**
 * 用与数据转换
 * 
 * @author Administrator WJM 2012-12-20下午5:08:25
 */
public class BOUtil {
	private static final Logger log = Logger.getLogger(BOUtil.class);
	/**
	 * 需要排除的属性
	 */
	public static final String PROPERTY_EXCEPT_CLASS = "class";
	public static final String PROPERTY_EXCEPT_MGRED = "mgred";
	public static final String PROPERTY_ID = "id";

	/**
	 * 取bo的所有属性描述符
	 * 
	 * @param classz
	 * @param expend
	 *            是否包含属性里的属性
	 * @param expProperties
	 *            需要展开的属性名列表
	 * @return
	 */
	public static <T extends BaseBO> List<PropertyDescriptor> getAllBOPropertys(Class<T> classz, boolean expend,
			String[] expProperties) {
		List<PropertyDescriptor> ret = new ArrayList<PropertyDescriptor>();
		List<PropertyDescriptor> descriptors = getAllProperty(classz, new String[] { "class", "mgred" });
		PropertyDescriptor pd;
		Class<?> idc;
		boolean hasexpend;
		for (int i = 0; i < descriptors.size(); i++) {
			pd = descriptors.get(i);
			hasexpend = false;
			if (expend && expProperties != null) {
				for (int j = 0; j < expProperties.length; j++) {
					if (expProperties[j].equals(pd.getName())) {
						hasexpend = true;
						idc = pd.getPropertyType();
						ret.addAll(getAllProperty(idc, new String[] { "class", "mgred" }));
					}
				}
			}
			if (!hasexpend) {
				ret.add(pd);
			}

		}
		return ret;
	}

	/**
	 * 取bo的所有属性名
	 * 
	 * @param classz
	 * @param expend
	 *            是否包含属性里的属性
	 * @param expProperties
	 *            需要展开的属性名列表
	 * @return
	 */
	public static <T extends BaseBO> List<String> getAllBOPropertyNames(Class<T> classz, boolean expend,
			String[] expProperties) {
		List<PropertyDescriptor> lpd = getAllBOPropertys(classz, expend, expProperties);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < lpd.size(); i++) {
			list.add(lpd.get(i).getName());
		}
		return list;
	}

	/**
	 * 找出所有属性描述符
	 * 
	 * @param classz
	 * @param except排除属性
	 * @return
	 */
	public static List<PropertyDescriptor> getAllProperty(Class<?> classz, String[] except) {
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(classz);
		List<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>();
		for (int j = 0; j < descriptors.length; j++) {
			if (!Arrays.asList(except).contains(descriptors[j].getName())) {
				list.add(descriptors[j]);
			}
		}
		return list;
	}

	/**
	 * 页面传来的参数转换为BO对象的List
	 * 
	 * @param parameterMap
	 * @param classz
	 * @return
	 * @throws SuperQueryException
	 */
	public static <T extends BaseBO> List<T> paramMap2BOList(Map<String, String[]> parameterMap, Class<T> classz)
			throws SuperQueryException {
		log.info("转换Parameter为BO:" + classz);
		List<T> list = new ArrayList<T>();
		List<LinkedCaseInsensitiveMap< Object>> maplist = paraMap2ListMap(parameterMap, classz);
		log.info("size:" + maplist.size());
		for (int i = 0; i < maplist.size(); i++) {
			list.add(map2BO(maplist.get(i), classz));
		}
		return list;
	}

	/**
	 * 页面传来的参数转换为BO对象
	 * 丢弃同名重复值
	 * @param parameterMap
	 * @param classz
	 * @return
	 * @throws SuperQueryException
	 */
	public static <T extends BaseBO> T paramMap2SingleBO(Map<String, String[]> parameterMap, Class<T> classz)
			throws SuperQueryException {
		log.info("转换Parameter为BO:" + classz);
		LinkedCaseInsensitiveMap<Object> map = paraMap2Map(parameterMap);
		T t = map2BO(map, classz);
		return t;
	}

	/**
	 * 将Map<String, String[]>转换为Map<String, Object> 即，丢弃同名重复值
	 * 
	 * @param parameterMap
	 * @param key2Upper 将map的key值转成大写，便于SQL取值
	 * @return
	 */
	public static LinkedCaseInsensitiveMap<Object> paraMap2Map(Map<String, String[]> parameterMap) {
		LinkedCaseInsensitiveMap<Object> map = new LinkedCaseInsensitiveMap<Object>();
		Iterator<Entry<String, String[]>> it = parameterMap.entrySet().iterator();
		Entry<String, String[]> entry;
		String[] ss;
		String key;
		log.info("::开始转换Request参数！");
		while (it.hasNext()) {
			entry = it.next();
			ss = entry.getValue();
			key = entry.getKey();
			if (ss != null && ss.length > 0) {
				if (ss.length > 1) {
					log.warn("表单存在同名Parameter[" + key + "]");
				}
				map.put(key, ss[0]);
				log.debug(key + "==[" + ss[0] + "]");
			}
		}
		return map;
	}

	/**
	 * 根据classz的属性名 搜索ParameterMap里面的值，并封装为返回List<Map<String, Object>>
	 * 
	 * @param parameterMap
	 * @param classz
	 * @return
	 * @throws SuperQueryException
	 */
	public static <T extends BaseBO> List<LinkedCaseInsensitiveMap<Object>> paraMap2ListMap(Map<String, String[]> parameterMap,
			Class<T> classz) throws SuperQueryException {
		Iterator<Entry<String, String[]>> it = parameterMap.entrySet().iterator();
		Entry<String, String[]> entry;
		String[] values;
		String key;
		LinkedCaseInsensitiveMap<Object> map;
		List<LinkedCaseInsensitiveMap<Object>> maplist = new ArrayList<LinkedCaseInsensitiveMap<Object>>();
		List<String> pdlist = getAllBOPropertyNames(classz, true, new String[] { PROPERTY_ID });
		Object obj;
		PropertyDescriptor pd;
		T instance;
		try {
			instance = classz.newInstance();
		} catch (InstantiationException e1) {
			throw new SuperQueryException("Map转换失败,无访问权限" + classz.getName(), e1);
		} catch (IllegalAccessException e1) {
			throw new SuperQueryException("Map转换失败" + classz.getName(), e1);
		}
		while (it.hasNext()) {
			entry = it.next();
			key = entry.getKey();
			values = entry.getValue();
			if (!pdlist.contains(key)) {
				log.info("不是BO的属性，忽略：" + key);
				continue;
			}
			log.debug("========" + key);
			try {
				pd = PropertyUtils.getPropertyDescriptor(instance, key);
				if (pd == null) {
					pd = PropertyUtils.getPropertyDescriptor(instance, PROPERTY_ID);
					pd = PropertyUtils.getPropertyDescriptor(pd.getPropertyType().newInstance(), key);
				}

				for (int i = 0; i < values.length; i++) {
					obj = castValueByProDescriptor(values[i], pd);
					// log.debug(key + ":" + obj);
					if (maplist.size() <= i) {
						map = new LinkedCaseInsensitiveMap<Object>();
						map.put(key, obj);
						maplist.add(map);
					} else {
						maplist.get(i).put(key, obj);
					}
				}
			} catch (IllegalAccessException e) {
				throw new SuperQueryException("Map转换失败,无访问权限" + classz.getName() + "." + key, e);
			} catch (InvocationTargetException e) {
				throw new SuperQueryException("Map转换失败" + classz.getName() + "." + key, e);
			} catch (NoSuchMethodException e) {
				throw new SuperQueryException("Map转换失败" + classz.getName() + "." + key, e);
			} catch (InstantiationException e) {
				throw new SuperQueryException("Map转换失败" + classz.getName() + "." + key, e);
			}
		}
		return maplist;
	}

	/**
	 * 根据PropertyDescriptor 将value转换为对应类型的对象，便于set值
	 * 
	 * @param value
	 * @param pd
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SuperQueryException
	 */
	public static Object castValueByProDescriptor(String value, PropertyDescriptor pd) throws InstantiationException,
			IllegalAccessException, SuperQueryException {
		Class<?> classz = pd.getPropertyType();
		if (StringUtil.isEmpty(value)) {
			return null;
		}
		try {
			if (classz.isAssignableFrom(Integer.class)) {
				return Integer.parseInt(value);
			}
			if (classz.isAssignableFrom(Double.class)) {
				return new Double(value);
			}
			if (classz.isAssignableFrom(BigDecimal.class)) {
				return new BigDecimal(value);
			}
			if (classz.isAssignableFrom(Long.class)) {
				return new Long(value);
			}
			if (classz.isAssignableFrom(BigInteger.class)) {
				return new BigInteger(value);
			}
		} catch (NumberFormatException e) {
			throw new SuperQueryException("数字转换失败" + classz + "[" + value + "]", e);
		}

		return value;
	}

	/**
	 * bo对象转换为insert语句,(包含数据)
	 * 
	 * @param bo
	 * @param tableName
	 * @return
	 * @throws SuperQueryException
	 */
	public static <T extends BaseBO> String bo2InsertSql(T bo, String tableName) throws SuperQueryException {
		StringBuffer ret = new StringBuffer();
		StringBuffer values = new StringBuffer();
		List<String> cols = getAllBOPropertyNames(bo.getClass(), true, new String[] { PROPERTY_ID });
		ret.append(" insert into ");
		ret.append(tableName);
		ret.append("(");
		values.append(" values(");
		Object obj;
		for (int i = 0; i < cols.size(); i++) {
			try {
				ret.append(cols.get(i));
				ret.append(",");
				obj = getValueInBO(bo, cols.get(i));
				if (obj != null) {
					if (obj instanceof Number) {
						values.append(obj.toString());
					} else {
						values.append("'");
						values.append(obj.toString());
						values.append("'");
					}

				} else {
					values.append("''");
				}
				values.append(",");
			} catch (SuperQueryException e) {
				throw e;
			}
		}
		values.deleteCharAt(values.lastIndexOf(","));
		values.append(")");
		ret.deleteCharAt(ret.lastIndexOf(","));
		ret.append(")");
		ret.append(values);
		log.debug(ret);
		return ret.toString();
	}

	/**
	 * map转换为BO
	 * 
	 * @param mp
	 *            为忽略大小写，map中key统一大写
	 * @param classz
	 * @return
	 * @throws SuperQueryException
	 */
	public static <T extends BaseBO> T map2BO(LinkedCaseInsensitiveMap<Object> mp, Class<T> classz) throws SuperQueryException {
		T bo = null;
		try {
			bo = classz.newInstance();
			Object id;
			List<PropertyDescriptor> props = BOUtil.getAllProperty(classz, new String[] { "class", "mgred" });
			String propertyname;
			for (PropertyDescriptor pd : props) {
				propertyname = pd.getName();
				try {
					if (BOUtil.PROPERTY_ID.equals(propertyname)) {
						Class<T> idc;
						try {
							idc = (Class<T>) pd.getPropertyType();
						} catch (ClassCastException e) {
							throw new SuperQueryException("数据转换BO失败" + classz.getName() + "." + propertyname + "不是继承自"
									+ BaseBO.class);
						}
						id = map2BO(mp, idc);
						// log.info(propertyname + "==[" + id + "]===" +
						// pd.getPropertyType());
						PropertyUtils.setProperty(bo, propertyname, id);
					} else {
						// log.info(propertyname + "==[" +
						// mp.get(StringUtil.upper(propertyname)) + "]==="+
						// pd.getPropertyType());
						PropertyUtils.setProperty(bo, propertyname, mp.get(propertyname));
					}
				} catch (IllegalArgumentException e) {
					throw new SuperQueryException("数据转换BO失败,类型不匹配" + classz.getName() + "." + propertyname + ":"
							+ mp.get(propertyname).getClass(), e);
				} catch (InvocationTargetException e) {
					throw new SuperQueryException("数据转换BO失败,无法设置值" + classz.getName() + "." + propertyname, e);
				} catch (NoSuchMethodException e) {
					throw new SuperQueryException("数据转换BO失败,没找到set方法" + classz.getName() + "." + propertyname, e);
				}
			}
		} catch (InstantiationException e1) {
			throw new SuperQueryException("数据转换BO失败:" + classz.getName() + "不能初始化.", e1);
		} catch (IllegalAccessException e1) {
			throw new SuperQueryException("数据转换BO失败:" + classz.getName() + "无访问权限.", e1);
		}
		return bo;
	}

	/**
	 * 取bo对象里面的值
	 * 
	 * @param bo
	 * @param name
	 * @return
	 * @throws SuperQueryException
	 */
	public static <T extends BaseBO> Object getValueInBO(T bo, String name) throws SuperQueryException {
		if (bo == null) {
			throw new SuperQueryException("BO空！无法取值");
		}
		Object obj = null;
		List<String> list;
		try {
			list = getAllBOPropertyNames(bo.getClass(), false, null);
			if (list.contains(name)) {
				obj = PropertyUtils.getProperty(bo, name);
			} else {
				BaseBO id = (BaseBO) PropertyUtils.getProperty(bo, PROPERTY_ID);
				if (id != null) {
					list = getAllBOPropertyNames(id.getClass(), false, null);
					if (list.contains(name)) {
						obj = PropertyUtils.getProperty(id, name);
					}
				}
			}

		} catch (IllegalAccessException e) {
			throw new SuperQueryException("BO取值失败,权限不足" + bo + "." + name, e);
		} catch (InvocationTargetException e) {
			throw new SuperQueryException("BO取值失败" + bo + "." + name, e);
		} catch (NoSuchMethodException e) {
			throw new SuperQueryException("没有get方法" + bo + "." + name, e);
		}
		log.debug(bo + ".[" + name + "]value:[" + obj + "]");
		return obj;
	}

	/**
	 * 转换为update语句，主键作为where条件，如果没有主键，报错！
	 * 
	 * @param bo
	 * @param tablename
	 * @return
	 * @throws SuperQueryException
	 */
	public static <T extends BaseBO> String bo2UpdateSql(T bo, String tablename) throws SuperQueryException {
		StringBuffer ret = new StringBuffer();
		T id;
		try {
			id = (T) PropertyUtils.getProperty(bo, PROPERTY_ID);
		} catch (IllegalAccessException e) {
			throw new SuperQueryException("取主键失败,权限不足" + bo + "." + PROPERTY_ID, e);
		} catch (InvocationTargetException e) {
			throw new SuperQueryException("取主键失败" + bo + "." + PROPERTY_ID, e);
		} catch (NoSuchMethodException e) {
			throw new SuperQueryException("取主键失败,没有get方法" + bo + "." + PROPERTY_ID, e);
		}
		if (id == null) {
			throw new SuperQueryException("无法找到主键属性,拒绝转换为" + bo + "." + PROPERTY_ID);
		}
		List<String> unpkCols = getAllBOPropertyNames(bo.getClass(), false, null);
		List<String> pkCols = getAllBOPropertyNames(id.getClass(), false, null);
		ret.append("update ");
		ret.append(tablename);
		ret.append(" set ");
		Object obj;
		for (int i = 0; i < unpkCols.size(); i++) {
			try {
				if (PROPERTY_ID.equals(unpkCols.get(i))) {
					continue;
				}
				ret.append(unpkCols.get(i));
				ret.append("=");
				obj = getValueInBO(bo, unpkCols.get(i));
				if (obj != null) {
					if (obj instanceof Number) {
						ret.append(obj.toString());
					} else {
						ret.append("'");
						ret.append(obj.toString());
						ret.append("'");
					}

				} else {
					ret.append("''");
				}
				ret.append(",");
			} catch (SuperQueryException e) {
				throw e;
			}
		}
		ret.deleteCharAt(ret.lastIndexOf(","));
		ret.append(" where 1=1 ");
		for (int i = 0; i < pkCols.size(); i++) {
			try {
				ret.append(" and ");
				ret.append(pkCols.get(i));
				ret.append("=");
				obj = getValueInBO(id, pkCols.get(i));
				if (obj != null) {
					if (obj instanceof Number) {
						ret.append(obj.toString());
					} else {
						ret.append("'");
						ret.append(obj.toString());
						ret.append("'");
					}

				} else {
					ret.append("''");
				}
			} catch (SuperQueryException e) {
				throw e;
			}
		}
		log.debug(ret);
		return ret.toString();
	}

}
