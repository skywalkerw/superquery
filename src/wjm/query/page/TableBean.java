package wjm.query.page;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import wjm.common.util.QConst;
import wjm.common.util.StringUtil;
import wjm.query.data.DataConverter;
import wjm.query.loader.DictionaryLoader;
import wjm.query.loader.QueryconfLoader;
import wjm.query.meta.QueryConf;
import wjm.query.meta.SysQueryFieldBO;

/**
 * 用于处理查询结果表格--页面
 * 
 * @author Administrator WJM 2012-12-12下午10:37:55
 */
public class TableBean implements Serializable {
	private static final String ISPK = "1";
	private static final String RN = "RN";
	private static final Logger log = Logger.getLogger(TableBean.class);
	private List<String> colalias;
	private List<Map<String, Object>> datas;
	private static final int PERLINE_FIELD_COUNT = 2;
	private QueryConf queryconf;
	private int rowsize;
	private int colsize;
	private String queryid;
	private boolean showRowNO;
	DictionaryLoader dl ;

	public TableBean(List<String> colnames, List<Map<String, Object>> datalist, String queryid) throws SQLException {
		this.colalias = colnames;
		this.datas = datalist;
		this.queryconf = QueryconfLoader.instance().getConf(queryid);
		this.setRowsize(datas.size());
		this.setColsize(colalias.size());
		this.queryid = queryid;
		this.showRowNO = QConst.SHOW_ROWNO;
		dl = DictionaryLoader.instance();
		log.info("TableBean init successed! Queryid[" + queryid + "],rowsize[" + this.getRowsize() + "],colsize["
				+ this.getColsize() + "]");
	}

	public Object get(int rowindex, int colindex) throws Exception {
		if (rowindex >= getRowsize()) {
			throw new Exception("TableBean data index out of range. rowsize:" + getRowsize() + "rowindex:" + rowindex);
		} else {
			return datas.get(rowindex).get(colindex);
		}
	}

	/**
	 * @return HTML 结果列表
	 */
	public String toHtmlListTable(boolean detail) {
		StringBuffer ret = new StringBuffer();
		ret.append("<table class='output'>\n");
		ret.append(this.genHtmlHeader(detail));
		ret.append(this.genHtmlDataTable(detail));
		if (detail) {
			ret.append("<script type='text/javascript'>function showDetail(obj){obj.parentNode.submit();return false;}</script>");
		}
		// log.debug("=====" + ret);
		return ret.toString();
	}

	/**
	 * @return HTML 详细信息
	 */
	public String toHtmlDetailTable() {
		StringBuffer sb = new StringBuffer();
		if (rowsize > 1) {
			sb.append("<h5 style='color:red;'>警告：详细信息返回了多条记录，请检查查询配置[" + queryid + "]是否配置ISPK属性</h5>");
		} else if (rowsize == 0) {
			sb.append("<h5 style='color:red;'>警告：没能找到详细信息！</h5>");
		}
		String queryname = dl.getValueByTPAndKey(QConst.SYS_DICTID_ALLCONFS, queryid);
		sb.append("<table class='output'>\n<tr>");
		sb.append("<tr><th  class='output' colspan='" + (PERLINE_FIELD_COUNT * 2) + "'>" + queryname + "-详细信息</th></tr>");
		SysQueryFieldBO bo = null;
		Object data;
		int count = 0;
		for (int i = 0; i < colalias.size(); i++) {
			if (RN.equals(colalias.get(i))) {
				continue;
			}
			if (count != 0 && count % PERLINE_FIELD_COUNT == 0) {
				sb.append("</tr>\n<tr>");
			}
			bo = queryconf.getByColailas(colalias.get(i));
			data = datas.get(0).get(colalias.get(i));
			if (bo != null) {
				sb.append("<td class='outputhead'>");
				sb.append(bo.getColcomment());
				sb.append("</td>");
				sb.append("<td  class='output'>");
				sb.append(DataConverter.convert(data, bo));
				sb.append("</td>");
			} else {
				sb.append("<td class='outputhead'>");
				sb.append(colalias.get(i));
				sb.append("</td>");
				sb.append("<td  class='output'>");
				sb.append(data);
				sb.append("</td>");
			}
			count++;
		}
		sb.append("</tr>\n<tr><td style='border:gray 0px solid;' colspan='").append(PERLINE_FIELD_COUNT * 2)
				.append("'>");
		sb.append("<input type='button' value='关闭' class='button' onclick='window.close();'></input>\n");
		sb.append("</td></tr></table>\n");
		log.info(sb);
		return sb.toString();
	}

	private StringBuffer genHtmlHeader(boolean detail) {
		StringBuffer ret = new StringBuffer();
		String queryname = dl.getValueByTPAndKey(QConst.SYS_DICTID_ALLCONFS, queryid);
		SysQueryFieldBO bo;
		ret.append("<tr>");
		int count = 0;
		if (showRowNO) {
			ret.append("<th class='output'>编号</th>");
			count++;
		}
		
		for (int i = 0; i < colalias.size(); i++) {
			if (RN.equals(colalias.get(i))) {
				continue;
			}
			bo = queryconf.getByColailas(colalias.get(i));
			if (bo == null) {
				ret.append("<th class='output'>");
				ret.append(colalias.get(i));
				ret.append("</th>");
				count++;
			} else {
				if (!QConst.DISPTYPE_NONE.equals(bo.getDisptype())) {
					ret.append("<th class='output'>");
					ret.append(bo.getColcomment());
					ret.append("</th>");
					count++;
				}
			}
		}
		if (detail) {
			ret.append("<th class='output'>操作</th>");
			count++;
		}
		ret.append("</tr>\n");
		ret.insert(0, "<tr><th  class='output' colspan='" + (count) + "'>" + queryname + "</th></tr>");
		return ret;
	}

	private StringBuffer genHtmlDataTable(boolean detail) {
		StringBuffer ret = new StringBuffer();
		Map<String, Object> row;
		String data;
		SysQueryFieldBO bo;
		Map<String, Object> pks = new HashMap<String, Object>();
		for (int j = 0; j < datas.size(); j++) {
			row = datas.get(j);
			ret.append("<tr>");
			if (showRowNO) {
				ret.append("<td class='output'>").append(j + 1).append("</td>");
			}
			for (int i = 0; i < colalias.size(); i++) {
				if (RN.equals(colalias.get(i))) {
					continue;
				}
				bo = queryconf.getByColailas(colalias.get(i));
				if (bo == null) {
					ret.append("<td  class='output'>");
					ret.append(StringUtil.trim(row.get(colalias.get(i))));
					ret.append("</td>");
				} else {
					if (ISPK.equals(bo.getIspk())) {
						pks.put(bo.getId().getColalias(), row.get(colalias.get(i)));
					}
					if (!QConst.DISPTYPE_NONE.equals(bo.getDisptype())) {
						ret.append("<td  class='output'>");
						data = DataConverter.convert(row.get(colalias.get(i)), bo);
						ret.append(data);
						ret.append("</td>");
					}
				}
			}
			if (detail) {
				ret.append("<td class='output'>");
				ret.append("<form target='_blank' style='margin:0px auto;padding:0px 0px;' action='").append(queryid)
				.append(QConst.ACTION_SUFFIX_OUTPUT).append("'>");
				ret.append(PageUtil.inputHiddens(pks));
				ret.append("<input type='hidden' name='action' value='detail'></input>");
				ret.append("<a href='#' onclick='return showDetail(this);'>详细信息</a>");
				ret.append("</form>");
				ret.append("</td>");
			}
			ret.append("</tr>\n");
		}
		return ret;
	}

	/**
	 * 获取表头
	 * 
	 * @return
	 */
	public List<String> getHeaders() {
		return colalias;
	}

	/**
	 * 获取数据集
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getDatas() {
		return datas;
	}

	/**
	 * 数据条数
	 * 
	 * @return
	 */
	public int getRowsize() {
		return rowsize;
	}

	/**
	 * 列数
	 * 
	 * @return
	 */
	public int getColsize() {
		return colsize;
	}

	public String getQueryid() {
		return queryid;
	}

	public void setQueryid(String queryid) {
		this.queryid = queryid;
	}

	public void setRowsize(int rowsize) {
		this.rowsize = rowsize;
	}

	public void setColsize(int colsize) {
		this.colsize = colsize;
	}

	public boolean isShowRowNO() {
		return showRowNO;
	}

	public void setShowRowNO(boolean showRowNO) {
		this.showRowNO = showRowNO;
	}

}
