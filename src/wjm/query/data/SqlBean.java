package wjm.query.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import wjm.common.exception.SuperQueryException;
import wjm.common.util.QConst;
import wjm.common.util.StringUtil;
import wjm.query.loader.QueryconfLoader;
import wjm.query.meta.SysQueryconfBO;

/**
 * 用于生成SQL
 * 
 * @author Administrator WJM 2012-12-13下午7:13:25
 */
public class SqlBean {
	private static final String JOINWAY_LEFT = "left";
	private static final String JOINWAY_RIGHT = "right";
	private static final Logger log = Logger.getLogger(SqlBean.class);
	private Map<String, SysQueryconfBO> confMap;
	private Map<String, Object> pMap;
	private Map<String, String> tableAlias;
	private String queryid;

	public SqlBean(String queryid, Map<String, Object> param) throws SuperQueryException {
		this.queryid = queryid;
		this.pMap = param;
		if (pMap == null) {
			pMap = new HashMap<String, Object>();
		}
		this.confMap = QueryconfLoader.instance().getConfMapByQueryid(queryid);
		if (this.confMap == null || this.confMap.isEmpty()) {
			throw new SuperQueryException("没有配置查询:[" + queryid + "]");
		}
		this.tableAlias = QueryconfLoader.instance().getTablesAlias(this.queryid);
	}

	public String makeSqlSelect() {
		
		StringBuffer sb = new StringBuffer();
		sb.append(makeOutput());
		sb.append(makeFrom());
		sb.append(makeCondition());
		sb.append(makeOrderby());
		return sb.toString();
	}
	
	public String makeSqlByPage(int startIndex, int pageSize) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select * from(");
		sb.append("select page.*,rownum rn from (");
		sb.append(makeSqlSelect());
		sb.append(") page  where rownum <= ");
		sb.append(startIndex+pageSize);
		sb.append(") where rn >");
		sb.append(startIndex);
		return sb.toString();
	}

	private StringBuffer makeFrom() {
		StringBuffer sb = new StringBuffer(" from ");
		Iterator<Entry<String, String>> it = tableAlias.entrySet().iterator();
		Entry<String, String> entry;
		while (it.hasNext()) {
			entry = it.next();
			sb.append(entry.getKey());
			sb.append(" ");
			sb.append(entry.getValue());
			sb.append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		log.debug("[" + sb + "]");
		return sb;
	}

	private StringBuffer makeOrderby() {
		StringBuffer sb = new StringBuffer();
		List<SysQueryconfBO> list = QueryconfLoader.instance().getOrderByConf(this.queryid);
		if (list == null || list.size() == 0) {
			return sb;
		}
		sb.append(" order by ");
		SysQueryconfBO bo;
		for (int i = 0; i < list.size(); i++) {
			bo = list.get(i);
			sb.append(tableAlias.get(bo.getTabname()));
			sb.append(".");
			sb.append(bo.getColrealname());
			sb.append(" ");
			sb.append(bo.getOrderby());
			sb.append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		log.debug("[" + sb + "]");
		return sb;
	}

	private StringBuffer makeCondition() {
		StringBuffer sb = new StringBuffer();
		List<SysQueryconfBO> list = QueryconfLoader.instance().getConfListByConfType(this.queryid,
				QueryconfLoader.CONFTYPE_CONDITION);
		if (list == null || list.size() == 0) {
			return sb;
		}
		sb.append(" where 1=1");
		SysQueryconfBO bo;
		Object param;
		String operand;
		SysQueryconfBO operandbo;
		StringBuffer buf;// 一条AND语句
		StringBuffer subbuf;// 操作符及右边值的处理
		for (int i = 0; i < list.size(); i++) {
			bo = list.get(i);
			param = pMap.get(bo.getId().getColalias());
			buf = new StringBuffer();
			buf.append(" and ");
			buf.append(tableAlias.get(bo.getTabname()));
			buf.append(".");
			buf.append(bo.getColrealname());
			subbuf = new StringBuffer();
			if (!StringUtil.isEmpty(param)) {
				// 外界传参
				subbuf.append(makeParamByOpper(param, bo.getOpper()));
			} else {
				// 联表查询条件
				operand = bo.getOperand();
				if (!StringUtil.isEmpty(operand)) {
					operandbo = confMap.get(StringUtil.upper(operand));
					if (operandbo != null) {
						// 判断join方式
						String left = "", right = "";
						if (JOINWAY_LEFT.equals(bo.getJoinway())) {
							// 左连接，在等号右边的列右加“(+)"表示即使右边为空，左边依旧返回，参见SQL的使用说明
							right = "(+)";
						} else if (JOINWAY_RIGHT.equals(bo.getJoinway())) {
							left = "(+)";
						}// full 或者不填表示全连接
						subbuf.append(left);
						subbuf.append(bo.getOpper());
						subbuf.append(tableAlias.get(operandbo.getTabname()));
						subbuf.append(".");
						subbuf.append(operandbo.getColrealname());
						subbuf.append(right);
					}else if(operand.startsWith("#")){//默认值
						subbuf.append(makeParamByOpper(operand.substring(1), bo.getOpper()));
					}
				}
			}
			buf.append(subbuf);
			if (subbuf.length() > 0) {
				sb.append(buf);
			}

		}
		log.debug("[" + sb + "]");
		return sb;
	}

	private StringBuffer makeParamByOpper(Object param, String opper) {
		StringBuffer ret = new StringBuffer();
		String left = "";
		String right = "";
		if (StringUtil.isEmpty(param)) {
			return ret;
		}
		if (param instanceof CharSequence) {
			left = "'";
			right = "'";
		}
		if ("%like".equals(opper)) {
			ret.append(" like ");
			ret.append(left);
			ret.append("%");
			ret.append(param);
			ret.append(right);
		} else if ("like%".equals(opper)) {
			ret.append(" like ");
			ret.append(left);
			ret.append(param);
			ret.append("%");
			ret.append(right);
		} else if ("%like%".equals(opper)) {
			ret.append(" like ");
			ret.append(left);
			ret.append("%");
			ret.append(param);
			ret.append("%");
			ret.append(right);
		} else {
			ret.append(opper);
			ret.append(left);
			ret.append(param);
			ret.append(right);
		}
		return ret;
	}

	private StringBuffer makeOutput() {
		StringBuffer sb = new StringBuffer();
		List<SysQueryconfBO> list = QueryconfLoader.instance().getConfListByConfType(this.queryid,
				QueryconfLoader.CONFTYPE_OUTPUT);
		if (list == null || list.size() == 0) {
			return sb;
		}
		sb.append("select ");
		SysQueryconfBO bo;
		for (int i = 0; i < list.size(); i++) {
			bo = list.get(i);

			sb.append(" ");
			sb.append(tableAlias.get(bo.getTabname()));
			sb.append(".");
			sb.append(bo.getColrealname());
			sb.append(" ");
			sb.append(bo.getId().getColalias());
			sb.append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		log.debug("[" + sb + "]");
		return sb;
	}

	public String makeSqlcount() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) count from(");
		sb.append(makeSqlSelect());
		sb.append(")");
		return sb.toString();
	}

}
