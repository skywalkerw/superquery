package wjm.query.page;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.util.LinkedCaseInsensitiveMap;

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

	public static StringBuffer makeConditionPageByQueryid(String queryid, LinkedCaseInsensitiveMap<Object> param)
			throws SuperQueryException {
		ConditionBean condition;
		StringBuffer ret = new StringBuffer();
		try {
			condition = new ConditionBean(queryid, param);
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
			sb.append(ControlUtil.makeInput(entry.getValue(), entry.getKey(), "hidden"));
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
			sb.append(ControlUtil.makeOptionsByDict(bo.getCtrltype(), def_V.toString(), true));
			sb.append("</select>");
		} else {
			// TODO 其他控件，例如calender
		}
		return sb;
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
	public static StringBuffer makeConfModifyTRbyFieldBO(SysQueryFieldBO field, boolean insert) {
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

		ret.append(makeTdWrapedInput(field.getTabname(), "tabname", false));
		ret.append(makeTdWrapedInput(field.getColrealname(), "colrealname", false));
		ret.append(makeTdWrapedInput(field.getColcomment(), "colcomment", true));
		ret.append(makeTdWrapedInput(field.getId().getColalias(), "colalias", insert));// colailas
		ret.append(makeTdWrapedSelect(field.getFieldtype(), "fieldtype", "fieldtype", false));
		ret.append(makeTdWrapedSelect(field.getCtrltype(), "ctrltype", "ctrltype", true));
		ret.append(makeTdWrapedInput(StringUtil.trim(field.getCtrllen()), "ctrllen", true));
		ret.append(makeTdWrapedInput(StringUtil.trim(field.getDisplen()), "displen", true));
		ret.append(makeTdWrapedSelect(field.getValidator(), "validator", "validator", true));
		ret.append(makeTdWrapedSelect(field.getOpper(), "opper", "opper", true));
		ret.append(makeTdWrapedInput(field.getOperand(), "operand", true));
		ret.append(makeTdWrapedSelect(field.getJoinway(), "joinway", "joinway", true));
		ret.append(makeTdWrapedSelect(field.getOrderby(), "orderby", "orderby", true));
		ret.append(makeTdWrapedSelect(field.getDicttype(), "dicttype", QConst.SYS_DICTID_DICTTYPE, true));
		ret.append(makeTdWrapedSelect(field.getDisptype(), "disptype", "disptype", false));
		ret.append(makeTdWrapedInput(field.getCss(), "css", true));
		ret.append(makeTdWrapedSelect(field.getIspk(), "ispk", "ispk", true));
		ret.append("</tr>\n");
		return ret;
	}

	private static Object makeTdWrapedSelect(String value, String ctrlname, String dictType, boolean nullable) {
		value = StringUtil.trim(value);
		StringBuffer ret = new StringBuffer();
		ret.append("<td class='output'>");
		ret.append("<select ").append("name='").append(ctrlname).append("'>");
		ret.append(ControlUtil.makeOptionsByDict(dictType, value, nullable));
		ret.append("</select>");
		ret.append("</td>");
		return ret;
	}

	

	private static StringBuffer makeTdWrapedInput(String value, String ctrlname, boolean editable) {
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
		ret.append(ControlUtil.makeInput(value, ctrlname, type));
		ret.append("</td>");
		return ret;
	}

	

}
