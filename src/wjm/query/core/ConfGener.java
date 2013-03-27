package wjm.query.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import wjm.common.exception.DataStoreException;
import wjm.common.exception.SuperQueryException;
import wjm.common.util.QConst;
import wjm.common.util.StringUtil;
import wjm.query.common.base.BOUtil;
import wjm.query.data.DataStore;
import wjm.query.data.SqlBean;
import wjm.query.loader.DictionaryLoader;
import wjm.query.loader.QueryconfLoader;
import wjm.query.meta.SysQueryconfBO;
import wjm.query.meta.SysQueryconfBOId;
import wjm.query.meta.TableStructBO;
import wjm.query.page.PageUtil;
import wjm.query.page.TableBean;
import wjm.query.page.TableStructBean;

/**
 * 查询配置处理类 控制层
 * 
 * @author Administrator WJM 2012-12-17下午6:02:58
 */
public class ConfGener {
	private static final String COLALIAS = "colalias";
	private static final String QUERYID = "queryid";
	private static final String DEFAULT_OPPER = "=";
	private static final String DEFAULT_DISPLAYTYPE = "plain";
	private static final String DEFAULT_CTRLTYPE = "text";
	private static final String DEFAULT_CONFTYPE = "both";
	private static final String COLNAME = "colname";
	private static final String TABLENAME = "tablename";
	private static final Logger log = Logger.getLogger(ConfGener.class);
	private static final String headers = "<tr>                                     "
			+ "	<th class='output'>调序</th>          " + "	<th class='output'>表名</th>          "
			+ "	<th class='output'>列名</th>          " + "	<th class='output'>列注释</th>        "
			+ "	<th class='output'>列别名</th>        " + "	<th class='output'>配置类别</th>      "
			+ "	<th class='output'>控件类型</th>      " + "	<th class='output'>控制输入长度</th>  "
			+ "	<th class='output'>控件显示长度</th>  " + "	<th class='output'>输入验证</th>      "
			+ "	<th class='output'>操作符</th>        " + "	<th class='output'>被操作数</th>      "
			+ "	<th class='output'>JOIN类型</th>      " + "	<th class='output'>排序</th>          "
			+ "	<th class='output'>数据字典类别</th>  " + "	<th class='output'>显示类型</th>      "
			+ "	<th class='output'>CSS样式</th>       " + " <th class='output'>结果唯一索引</th>" + "</tr>";

	public String mapping(HttpServletRequest request) throws SuperQueryException {
		String servPath = request.getServletPath();
		String action = request.getParameter("action");
		log.info("收到请求:" + servPath + "." + action);
		log.debug("action=" + action);
		if ("addQueryConf".equals(action)) {
			return addQueryConf(request.getParameterMap());
		} else if ("updateConf".equals(action)) {
			return updateConf(request.getParameterMap());
		}
		return "Can not find Action: " + action;
	}

	public String ajax(HttpServletRequest request) throws SuperQueryException {
		String servPath = request.getServletPath();
		String action = request.getParameter("action");
		log.info("收到请求:" + servPath + "." + action);
		log.debug("action=[" + action + "]");
		if ("getTableStruct".equals(action)) {
			return getTableStruct(request.getParameter(TABLENAME));
		} else if ("getAllTables".equals(action)) {
			return getAllTables();
		} else if ("initAddConfRow".equals(action)) {
			return initAddConfRow(request.getParameter(TABLENAME), request.getParameter(COLNAME));
		} else if ("batchInitAddConfRow".equals(action)) {
			return batchInitAddConfRow(request.getParameter(TABLENAME));
		} else if ("checkQueryid".equals(action)) {
			return checkQueryid(request.getParameter(QUERYID));
		} else if ("queryConf".equals(action)) {
			return queryConf(request.getParameter(QUERYID));
		} else if ("initModifyConfRow".equals(action)) {
			return initModifyConfRow(request.getParameter(QUERYID));
		} else if ("deleteConf".equals(action)) {
			return deleteConf(request.getParameter(QUERYID), request.getParameter(COLALIAS));
		} else if ("getAllConfs".equals(action)) {
			return getAllConfs();
		} else if ("getAllTableCols".equals(action)) {
			return getAllTableCols(request.getParameter(TABLENAME));
		}
		return "Can not find Action: " + action;
	}

	private String getAllTableCols(String tablename) {
		log.debug("tablename=[" + tablename + "]");
		if (tablename == null) {
			log.warn("没有传入表名称！");
			return "";
		}
		StringBuffer ret = new StringBuffer();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("tabname", tablename);
			String s = new SqlBean(QConst.SYS_QUERYID_TABLESTURCT, param).makeSqlSelect();
			List<TableStructBO> list = DataStore.instance().selectBOList(s, TableStructBO.class);
			Map<String, String> map = new HashMap<String, String>();
			for (int k = 0; k < list.size(); k++) {
				map.put(list.get(k).getColname(), list.get(k).getColcnmment());
			}
			ret.append(PageUtil.makeSelect(map, COLNAME, COLNAME, null, false));
		} catch (DataStoreException e) {
			log.error("", e);
			ret.append(e.getMessage());
		} catch (SuperQueryException e) {
			log.error("", e);
			ret.append(e.getMessage());
		}
		log.debug(ret);
		return ret.toString();
	}

	private String getAllConfs() {
		return PageUtil.makeOptions("allconfs", null, false).toString();
	}

	private String updateConf(Map<String, String[]> parameterMap) throws SuperQueryException {
		StringBuffer ret = new StringBuffer();
		if (parameterMap.get(QUERYID) == null) {
			return "非法请求！";
		}
		try {
			List<SysQueryconfBO> bolist = BOUtil.parameterMap2BO(parameterMap, SysQueryconfBO.class);
			String queryid = parameterMap.get(QUERYID)[0];
			SysQueryconfBO bo;
			List<String> sqllist = new ArrayList<String>();
			DataStore ds = DataStore.instance();
			int updatesize = 0;
			for (int i = 0; i < bolist.size(); i++) {
				bo = bolist.get(i);
				bo.setConforder(new BigDecimal(i + 1));// 设置顺序
				//log.info("-----------------"+parameterMap.get("insert")[i]);
				if ("true".equals(parameterMap.get("insert")[i])) {
					sqllist.add(BOUtil.bo2InsertSql(bo, QConst.SYS_QUERYCONF_TABLENAME));
				} else {
					sqllist.add(BOUtil.bo2UpdateSql(bo, QConst.SYS_QUERYCONF_TABLENAME));
				}
			}
			updatesize = ds.excuteUpdateBath(sqllist.toArray(new String[0]));
			if (updatesize > 0) {
				log.debug("更新" + updatesize + "条记录.");
			}
			refresh(queryid);
			ret.append("配置更新成功！[" + updatesize + "]" + "[" + queryid + "]");
		} catch (SuperQueryException e) {
			throw e;
		} catch (DataStoreException e) {
			throw new SuperQueryException(e.getMessage(), e);
		}
		log.debug(ret);
		return ret.toString();
	}

	private String deleteConf(String queryid, String colalias) throws SuperQueryException {
		if (StringUtil.isEmpty(queryid)) {
			throw new SuperQueryException("不安全的操作：删除查询配置。需要指定查询ID");
		}
		try {
			String sql = "delete from sys_queryconf where queryid='" + queryid + "'";
			if (!StringUtil.isEmpty(colalias)) {
				sql += " and colalias='" + colalias + "'";
			}
			DataStore ds = DataStore.instance();
			int size = ds.excuteUpdateBath(new String[] { sql });
			refresh(queryid);
			return "已删除,[" + queryid + "][" + StringUtil.trim(colalias) + "]size[" + size + "]";
		} catch (DataStoreException e) {
			throw new SuperQueryException(e.getMessage(), e);
		}
	}

	private String checkQueryid(String queryid) throws SuperQueryException {
		String sql = "select count(*) count from sys_queryconf where queryid='" + queryid + "'";
		try {
			List<Map<String, Object>> list = DataStore.instance().selectMapList(sql);
			if (list.size() > 0) {
				return "" + list.get(0).get("COUNT");
			}
		} catch (DataStoreException e) {
			throw new SuperQueryException(e.getMessage(), e);
		}
		return "0";
	}

	private String batchInitAddConfRow(String tablename) {
		log.info("批量添加配置编辑控件：" + tablename);
		return initAddConfRow(tablename, null);
	}

	/**
	 * 添加配置到数据库
	 * 
	 * @param parameterMap
	 * @return
	 * @throws SuperQueryException
	 */
	private String addQueryConf(Map<String, String[]> parameterMap) throws SuperQueryException {
		StringBuffer ret = new StringBuffer();
		try {
			List<SysQueryconfBO> bolist = BOUtil.parameterMap2BO(parameterMap, SysQueryconfBO.class);
			if (parameterMap.get(QUERYID) == null) {
				return "非法请求！";
			}
			String queryid = parameterMap.get(QUERYID)[0];
			SysQueryconfBO bo;
			List<String> sqllist = new ArrayList<String>();
			DataStore ds = DataStore.instance();
			int updatesize;
			for (int i = 0; i < bolist.size(); i++) {
				bo = bolist.get(i);
				bo.setConforder(new BigDecimal(i + 1));// 设置顺序
				sqllist.add(BOUtil.bo2InsertSql(bo, QConst.SYS_QUERYCONF_TABLENAME));
			}
			updatesize = ds.excuteUpdateBath(sqllist.toArray(new String[0]));
			if (updatesize > 0) {
				log.debug("新增" + updatesize + "条记录.");
			}
			refresh(queryid);
			ret.append("配置增加成功！[" + updatesize + "]" + "[" + queryid + "]");
		} catch (SuperQueryException e) {
			throw e;
		} catch (DataStoreException e) {
			throw new SuperQueryException(e.getMessage(), e);
		}
		log.debug(ret);
		return ret.toString();
	}

	/**
	 * 刷新缓存
	 * 
	 * @param queryid
	 * @throws DataStoreException
	 */
	private void refresh(String queryid) throws DataStoreException {
		QueryconfLoader cl = QueryconfLoader.instance();
		DictionaryLoader dl = DictionaryLoader.instance();
		if (cl.loadSingle(queryid)) {
			log.info("加载查询配置成功！");
			dl.loadAddon();
			log.info("加载数据字典附加部分成功！");
		}
	}

	/**
	 * 初始一行配置编辑列
	 * 
	 * @param tablename
	 * @param colname
	 * @return
	 */
	private String initAddConfRow(String tablename, String colname) {
		StringBuffer ret = new StringBuffer();
		String sql;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("tabname", tablename);
			param.put("colname", colname);
			sql = new SqlBean(QConst.SYS_QUERYID_TABLESTURCT, param).makeSqlSelect();
			List<TableStructBO> list = DataStore.instance().selectBOList(sql, TableStructBO.class);
			ret.append(makeConfTRs_Add(list));
		} catch (SuperQueryException e) {
			log.error("", e);
			ret.append(e.getMessage());
		} catch (DataStoreException e) {
			log.error("", e);
			ret.append(e.getMessage());
		}
		return ret.toString();
	}

	/**
	 * @param list
	 * @return
	 */
	private StringBuffer makeConfTRs_Add(List<TableStructBO> list) {
		StringBuffer ret = new StringBuffer();
		TableStructBO bo;
		SysQueryconfBO conf;
		SysQueryconfBOId id;
		for (int i = 0; i < list.size(); i++) {
			bo = list.get(i);
			// 先根据表结构初始化查询配置
			id = new SysQueryconfBOId();
			id.setColalias(bo.getColname());
			conf = new SysQueryconfBO();
			conf.setId(id);
			conf.setTabname(bo.getTabname());
			conf.setConftype(DEFAULT_CONFTYPE);
			conf.setCtrltype(DEFAULT_CTRLTYPE);
			conf.setDisptype(DEFAULT_DISPLAYTYPE);
			conf.setOpper(DEFAULT_OPPER);
			conf.setColrealname(bo.getColname());
			conf.setColcomment(bo.getColcnmment());
			conf.setCtrllen(bo.getDatalength());
			conf.setDisplen(bo.getDatalength());
			ret.append(PageUtil.makeConfModifyTRbyConfBO(conf, true));
		}
		log.debug(ret);
		return ret;
	}

	private String initModifyConfRow(String queryid) throws SuperQueryException {
		StringBuffer ret = new StringBuffer(headers);
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("QUERYID", queryid);
			String sql = new SqlBean(QConst.SYS_QUERYID_QUERYCONF, param).makeSqlSelect();
			List<SysQueryconfBO> bolist = DataStore.instance().selectBOList(sql, SysQueryconfBO.class);
			for (int i = 0; i < bolist.size(); i++) {
				ret.append(PageUtil.makeConfModifyTRbyConfBO(bolist.get(i), false));
			}
		} catch (DataStoreException e) {
			throw new SuperQueryException(e.getMessage(), e);
		} catch (SuperQueryException e) {
			throw e;
		}
		log.debug(ret);
		return ret.toString();
	}

	private String queryConf(String queryid) throws SuperQueryException {
		StringBuffer ret = new StringBuffer();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("QUERYID", queryid);
			TableBean tbean = DataStore.instance().selectTableBean(QConst.SYS_QUERYID_QUERYCONF, param);
			ret.append(tbean.toHtmlListTable(false));
		} catch (DataStoreException e) {
			throw new SuperQueryException(e.getMessage(), e);
		} catch (SuperQueryException e) {
			throw e;
		}
		return ret.toString();
	}

	private String getAllTables() throws SuperQueryException {
		StringBuffer ret = new StringBuffer();
		String tablessql = new SqlBean(QConst.SYS_QUERYID_ALLTABLES, null).makeSqlSelect();
		List<Map<String, String>> buflist;
		Map<String, String> map;
		try {
			buflist = DataStore.instance().selectMapList(tablessql);
			map = new HashMap<String, String>();
			for (int k = 0; k < buflist.size(); k++) {
				map.put(buflist.get(k).get("KEY"), buflist.get(k).get("VALUE"));
			}
			ret.append(PageUtil.makeSelect(map, TABLENAME, TABLENAME, null, false));
		} catch (DataStoreException e) {
			throw new SuperQueryException("", e);
		}
		return ret.toString();
	}

	private String getTableStruct(String tablename) {
		log.debug("tablename=[" + tablename + "]");
		if (tablename == null) {
			log.warn("没有传入表名称！");
			return "";
		}
		StringBuffer ret = new StringBuffer();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("tabname", tablename);
			String s = new SqlBean(QConst.SYS_QUERYID_TABLESTURCT, param).makeSqlSelect();
			List<TableStructBO> list = DataStore.instance().selectBOList(s, TableStructBO.class);
			TableStructBean tsbean = new TableStructBean(list);
			ret.append(tsbean.toHtml());
		} catch (DataStoreException e) {
			log.error("", e);
			ret.append(e.getMessage());
		} catch (SuperQueryException e) {
			log.error("", e);
			ret.append(e.getMessage());
		}
		log.debug(ret);
		return ret.toString();
	}
}
