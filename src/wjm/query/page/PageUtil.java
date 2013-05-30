package wjm.query.page;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import wjm.common.exception.SuperQueryException;
import wjm.common.util.QConst;
import wjm.common.util.StringUtil;
import wjm.query.common.base.BOUtil;
import wjm.query.loader.DictionaryLoader;
import wjm.query.loader.QueryconfLoader;
import wjm.query.meta.SysQueryFieldBO;
import wjm.query.meta.SysQueryFieldBOId;
import wjm.query.meta.TableStructBO;

/**
 * 用于生成页面
 * 
 * @author Administrator WJM 2012-12-13下午7:13:11
 */
public class PageUtil {
	private static final Logger log = Logger.getLogger(PageUtil.class);
	/**
	 * 控件最大显示宽度
	 */
	private static int MAX_DISPLEN = 400;// px
	private static int DEFAULT_DISPLEN = 150;// px
	private static int MIN_DISPLEN = 100;// px
	private static int DEFAULT_MAXLEN = 64;// 单位字符
	private static int FONTSIZE = 12;// px

	public static StringBuffer makeConditionPageByQueryid(String queryid, Map<String, Object> param)
			throws SuperQueryException {
		ConditionBean condition;
		StringBuffer ret = new StringBuffer();
		try {
			condition = new ConditionBean(queryid, null);
			ret.append(condition.toHtml());
		} catch (SuperQueryException e) {
			throw e;
		}
		return wrapByHtmlBody(ret, QueryconfLoader.instance().getConf(queryid).getBo().getQueryname());
	}

	public static StringBuffer makeOutListByTableBeanWithPage(TableBean tbean, Map<String, Object> param,
			int pageIndex, int pageSize, int recordCount) {
		StringBuffer ret = new StringBuffer();
		ret.append(tbean.toHtmlListTable(true));
		ret.append("<form id='queryform' action='").append(tbean.getQueryid()).append(QConst.ACTION_SUFFIX_OUTPUT);
		ret.append("' method='post'>\n");
		ret.append("<tr><td style='border:gray 0px solid;text-align:left;' colspan='")
				.append(tbean.isShowRowNO() ? tbean.getColsize() + 1 : tbean.getColsize()).append("'>");
		ret.append(new PageBean(pageIndex, pageSize, recordCount).toHtml());
		ret.append("</td></tr>\n");

		ret.append("<tr><td style='border:gray 0px solid;text-align:center;' colspan='")
				.append(tbean.isShowRowNO() ? tbean.getColsize() + 1 : tbean.getColsize()).append("'>");
		ret.append("<input type='button' value='返回' class='button' onclick=\"window.location.href='")
				.append(tbean.getQueryid()).append(QConst.ACTION_SUFFIX_QUERY).append("';\">");
		ret.append("</td></tr>\n");
		ret.append("</table>\n");
		ret.append(inputHiddens(param));
		ret.append("</form>");
		return wrapByHtmlBody(ret, QueryconfLoader.instance().getConf(tbean.getQueryid()).getBo().getQueryname());
	}

	public static Object makeOutDetailByTableBean(TableBean tbean) {
		StringBuffer ret = new StringBuffer();
		ret.append(tbean.toHtmlDetailTable());
		return wrapByHtmlBody(ret, QueryconfLoader.instance().getConf(tbean.getQueryid()).getBo().getQueryname() + "-详细信息");
	}

	public static StringBuffer inputHiddens(Map<String, Object> param) {
		StringBuffer sb = new StringBuffer();
		Iterator<Entry<String, Object>> it = param.entrySet().iterator();
		Entry<String, Object> entry;
		while (it.hasNext()) {
			entry = it.next();
			// sb.append("<input type ='hidden'");
			// sb.append(" name = '"+entry.getKey()+"'");
			// sb.append(" value = '"+entry.getValue()+"'");
			// sb.append("></input>");
			sb.append(makeInput(entry.getValue(), entry.getKey(), "hidden"));
		}
		return sb;
	}

	public static StringBuffer makeConditionCtrl(SysQueryFieldBO bo, Object def_V) {
		StringBuffer sb = new StringBuffer();
		if (def_V == null) {
			def_V = "";
		}
		String ctrlType = bo.getCtrltype();
		if (ctrlType == null) {
			sb.append(bo.getId().getColalias()).append("没有配置控件类型!");
			return sb;
		}
		if ("none".equals(ctrlType)) {

		} else if ("text".equals(ctrlType)) {
			sb.append("<input type='text' name='").append(bo.getId().getColalias()).append("'");
			sb.append(" value='").append(def_V).append("'");
			sb.append(" value='").append(def_V).append("'");
			int maxlength = bo.getCtrllen() == null ? DEFAULT_MAXLEN : bo.getCtrllen().intValue();
			int width = 0;
			width = bo.getDisplen() == null ? DEFAULT_DISPLEN : bo.getDisplen().intValue();
			width = width * FONTSIZE;
			width = width > MAX_DISPLEN ? MAX_DISPLEN : width;
			width = width < MIN_DISPLEN ? MIN_DISPLEN : width;
			sb.append(" maxlength='").append(maxlength).append("'");
			sb.append(" style='width:").append(width).append("px;'");
			sb.append("></input>");
		} else if ("select".equals(ctrlType)) {
			sb.append("<select ").append("name='").append(bo.getId().getColalias()).append("'>");
			sb.append(makeOptions(bo.getCtrltype(), def_V.toString(), true));
			sb.append("</select>");
		} else {
			// TODO 其他控件，例如calender
		}
		return sb;
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
		ret.append(makeOptions(map, value, needWrite));
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
	public static StringBuffer makeOptions(Map<String, String> map, String value, boolean needWrite) {
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

	public static StringBuffer wrapByHtmlBody(StringBuffer sb, String title) {
		StringBuffer ret = new StringBuffer();
		ret.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		ret.append("<HTML>");
		ret.append("  <HEAD><TITLE>").append(title).append("</TITLE>");
		ret.append(" <link href='").append(QConst.CONTEXTPATH)
				.append("res/css/default.css' type='text/css' rel='stylesheet'></link>");
		ret.append(" <script src='").append(QConst.CONTEXTPATH)
				.append("res/script/jquery.js' type='text/javascript'></script>");
		ret.append("</HEAD>");
		ret.append("  <BODY>");
		ret.append("<div align='center'>");
		ret.append(sb);
		ret.append("</div>");
		ret.append("  </BODY>");
		ret.append("</HTML>");
		return ret;
	}

	/**
	 * @param field
	 * @param insert
	 *            true 新增，false 修改
	 * @return
	 */
	public static StringBuffer makeConfModifyTRbyConfBO(SysQueryFieldBO field, boolean insert) {
		StringBuffer ret = new StringBuffer();
		ret.append("<tr>");
		// 顺序调整
		ret.append("<td class='output'>");
		ret.append("<a name='delrow' href='#' style='display:none;'>删除</a> &nbsp;");
		ret.append("<a name='down' href='#'><img style='border:0px;' src='");
		ret.append(QConst.CONTEXTPATH);
		ret.append("res/image/arrow_down.gif' width='12' height='12' alt='向下'></a> &nbsp;");
		ret.append("<a name='up'  href='#'><img style='border:0px;' src='");
		ret.append(QConst.CONTEXTPATH);
		ret.append("res/image/arrow_up.gif' width='12' height='12' alt='向上'></a>");
		ret.append("<input name='queryid' type='hidden' value='" + StringUtil.trim(field.getId().getQueryid()));
		ret.append("'></input>");
		ret.append("<input name='queryname' type='hidden' value='");
		if(QueryconfLoader.instance().getConf(field.getId().getQueryid())!=null)
		ret.append(StringUtil.trim(QueryconfLoader.instance().getConf(field.getId().getQueryid()).getBo().getQueryname()));
		ret.append("'></input>");
		ret.append("<input type='hidden' name='insert' value='" + insert + "'></input>").append("</td>");

		ret.append(makeConfTDText(field.getTabname(), "tabname", false));
		ret.append(makeConfTDText(field.getColrealname(), "colrealname", false));
		ret.append(makeConfTDText(field.getColcomment(), "colcomment", true));
		ret.append(makeConfTDText(field.getId().getColalias(), "colalias", insert));// colailas
		ret.append(makeConfTDSelect(field.getFieldtype(), "fieldtype", "fieldtype", false));
		ret.append(makeConfTDSelect(field.getCtrltype(), "ctrltype", "ctrltype", true));
		ret.append(makeConfTDText(StringUtil.trim(field.getCtrllen()), "ctrllen", true));
		ret.append(makeConfTDText(StringUtil.trim(field.getDisplen()), "displen", true));
		ret.append(makeConfTDSelect(field.getValidator(), "validator", "validator", true));
		ret.append(makeConfTDSelect(field.getOpper(), "opper", "opper", true));
		ret.append(makeConfTDText(field.getOperand(), "operand", true));
		ret.append(makeConfTDSelect(field.getJoinway(), "joinway", "joinway", true));
		ret.append(makeConfTDSelect(field.getOrderby(), "orderby", "orderby", true));
		ret.append(makeConfTDSelect(field.getDicttype(), "dicttype", QConst.SYS_DICTID_DICTTYPE, true));
		ret.append(makeConfTDSelect(field.getDisptype(), "disptype", "disptype", false));
		ret.append(makeConfTDText(field.getCss(), "css", true));
		ret.append(makeConfTDSelect(field.getIspk(), "ispk", "ispk", true));
		ret.append("</tr>\n");
		return ret;
	}

	private static Object makeConfTDSelect(String value, String ctrlname, String dictType, boolean nullable) {
		value = StringUtil.trim(value);
		StringBuffer ret = new StringBuffer();
		ret.append("<td class='output'>");
		ret.append("<select ").append("name='").append(ctrlname).append("'>");
		ret.append(makeOptions(dictType, value, nullable));
		ret.append("</select>");
		ret.append("</td>");
		return ret;
	}

	/**
	 * 生成HTML oprion 标签列表
	 * 
	 * @param dictType
	 * @param needWrite
	 * @return
	 */
	public static StringBuffer makeOptions(String dictType, String value, boolean needWrite) {
		Map<String, String> map = DictionaryLoader.instance().getEntryByTP(dictType);
		return PageUtil.makeOptions(map, value, needWrite);
	}

	private static StringBuffer makeConfTDText(String value, String ctrlname, boolean editable) {
		value = StringUtil.trim(value);
		String type;
		StringBuffer ret = new StringBuffer();
		ret.append("<td class='output'>");
		if (editable) {
			type = "text";
		} else {
			type = "hidden";
			ret.append(value);
		}
		ret.append(makeInput(value, ctrlname, type));
		ret.append("</td>");
		return ret;
	}

	private static StringBuffer makeInput(Object value, String ctrlname, String type) {
		StringBuffer ret = new StringBuffer();
		value = StringUtil.trim(value);
		ret.append("<input type='").append(type).append("' value='").append(value).append("' ");
		ret.append("name='").append(ctrlname).append("' ");
		ret.append("></input>");
		return ret;
	}

}
