package wjm.query.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import wjm.common.exception.SuperQueryException;
import wjm.common.util.QConst;
import wjm.common.util.StringUtil;
import wjm.query.loader.DictionaryLoader;
import wjm.query.loader.QueryconfLoader;
import wjm.query.meta.QueryConf;
import wjm.query.meta.SysQueryFieldBO;

/**
 * 用户处理查询条件--页面
 * 
 * @author Administrator WJM 2012-12-12下午10:37:48
 */
public class ConditionBean implements Serializable {
	private static final Logger log = Logger.getLogger(ConditionBean.class);
	private static final int PERLINE_FIELD_COUNT = 2;
	private Map<String, Object> initParams;
	private List<SysQueryFieldBO> confList;
	private String queryid;

	public ConditionBean(String queryid, Map<String, Object> initParams) throws SuperQueryException {
		this.initParams = initParams;
		this.queryid = queryid;
		if (this.initParams == null) {
			this.initParams = new HashMap<String, Object>();
		}
		this.confList = QueryconfLoader.instance().getConf(queryid).getFieldListByFieldType(QueryConf.FIELDTYPE_CONDITION);
		if (this.confList == null || this.confList.isEmpty()) {
			throw new SuperQueryException(QConst.ERRCODE_QUERYNOTFOUNT, "没有配置查询:[" + queryid + "]或该查询没有配置查询条件");
		}
	}

	public String toHtml() {
		StringBuffer sb = new StringBuffer();
		SysQueryFieldBO bo;
		String queryname = DictionaryLoader.instance().getValueByTPAndKey(QConst.SYS_DICTID_ALLCONFS, queryid);
		sb.append("<form action='").append(this.queryid).append(QConst.ACTION_SUFFIX_OUTPUT)
				.append("' method='post'>\n");
		sb.append("<table class='output'>\n<tr>");
		sb.append("<tr><th  class='output' colspan='" + (PERLINE_FIELD_COUNT * 2) + "'>" + queryname + "</th></tr>");
		int count = 0;
		for (int i = 0; i < confList.size(); i++) {
			bo = confList.get(i);
			if (!StringUtil.isEmpty(bo.getCtrltype()) && !QConst.CTRLTYPE_NONE.equals(bo.getCtrltype())) {
				if (count != 0 && count % PERLINE_FIELD_COUNT == 0) {
					sb.append("</tr>\n<tr>");
				}
				sb.append("<td class='outputhead'>");
				sb.append(bo.getColcomment());
				sb.append("</td>");
				sb.append("<td  class='output'>");
				sb.append(PageUtil.makeConditionCtrl(bo, initParams.get(bo.getId().getColalias())));
				sb.append("</td>");
				count++;
			}

		}
		sb.append("</tr>\n<tr><td style='border:gray 0px solid;' colspan='").append(PERLINE_FIELD_COUNT * 2)
				.append("'>");
		sb.append("<input type='submit' value='提交' class='button'></input><input type='reset' value='重置' class='button'></input>\n");
		sb.append("</td></tr></table>\n</form>");
		log.info(sb);
		return sb.toString();
	}
}
