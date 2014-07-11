package wjm.query.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.util.LinkedCaseInsensitiveMap;

import wjm.common.exception.SuperQueryException;
import wjm.common.util.QConst;
import wjm.common.util.StringUtil;
import wjm.query.common.base.BOUtil;
import wjm.query.core.ConfManager;
import wjm.query.core.QueryService;
import wjm.query.page.PageUtil;

public class QueryFactory implements IActionFactory {
	private static final String ACTION_QUERY_DETAIL = "detail";
	private static final Logger log = Logger.getLogger(QueryFactory.class);

	/**
	 * 查询请求的处理 控制层
	 * 
	 * @param request
	 * @return 返回HTML代码
	 * @throws SuperQueryException
	 */
	public String mapping(HttpServletRequest request) throws SuperQueryException {
		StringBuffer ret = new StringBuffer();
		String servPath = request.getServletPath();
		String queryid = "";
		queryid = servPath.substring(servPath.lastIndexOf('/') + 1, servPath.lastIndexOf('.'));
		String action = request.getParameter("action");
		log.info("收到请求:" + servPath + "======>" + queryid + "==>" + action);
		if (servPath.endsWith(QConst.ACTION_SUFFIX_QUERY)) {
			try {
				ret.append(PageUtil.makeConditionPageByQueryid(queryid, null));
			} catch (SuperQueryException e) {
				throw e;
			}
		} else if (servPath.endsWith(QConst.ACTION_SUFFIX_OUTPUT)) {
			int pageIndex = StringUtil.parseInt(request.getParameter("pageIndex"), 1);
			int pageSize = StringUtil.parseInt(request.getParameter("pageSize"), 20);
			LinkedCaseInsensitiveMap<Object> conditions = BOUtil.paraMap2Map(request.getParameterMap());
			ret.append(new QueryService().queryByPage(queryid, ACTION_QUERY_DETAIL.equals(action), conditions, pageIndex, pageSize));
		} else if (servPath.endsWith(QConst.ACTION_SUFFIX_CONF)) {
			return new ConfManager().mapping(request);
		}
		log.debug(ret);
		return ret.toString();
	}

}
