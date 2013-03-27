package wjm.query.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import wjm.common.exception.DataStoreException;
import wjm.common.exception.SuperQueryException;
import wjm.common.util.QConst;
import wjm.query.core.ConfGener;
import wjm.query.data.DataStore;
import wjm.query.page.PageUtil;
import wjm.query.page.TableBean;

public class QueryFactory implements IActionFactory {
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
		log.info("收到请求:" + servPath + "======>" + queryid);
		String action = request.getParameter("action");
		log.info("action:" + action);
		if (servPath.endsWith(QConst.ACTION_SUFFIX_QUERY)) {
			try {
				ret.append(PageUtil.makeConditionPageByQueryid(queryid, null));
			} catch (SuperQueryException e) {
				throw e;
			}
		} else if (servPath.endsWith(QConst.ACTION_SUFFIX_OUTPUT)) {
			try {
				long start = System.currentTimeMillis();
				DataStore ds = DataStore.instance();
				int startIndex = 1;
				int pageIndex = Integer.parseInt(request.getParameter("pageIndex") == null ? "1" : request
						.getParameter("pageIndex"));
				int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "20" : request
						.getParameter("pageSize"));
				startIndex = pageSize * (pageIndex - 1);

				log.info("pageIndex:" + pageIndex + ",pageSize:" + pageSize);
				Map<String, Object> param = parameterMap2HashMap(request.getParameterMap());
				BigDecimal recordCount = ds.selectCount(queryid, param);
				TableBean tbean = ds.selectTableBeanByPage(queryid, param, startIndex, pageSize);
				if("detail".equals(action)){
					ret.append(PageUtil.makeOutDetailByTableBean(tbean));
				}else{
					ret.append(PageUtil.makeOutListByTableBeanWithPage(tbean, param, pageIndex, pageSize, recordCount.intValue()));
				}
				log.info("查询["+queryid+"]处理完成,返回记录["+tbean.getRowsize()+"]条,耗时：["+(System.currentTimeMillis()-start)+"]ms");
			} catch (DataStoreException e) {
				throw new SuperQueryException(e.getMessage(), e);
			} catch (SuperQueryException e) {
				throw e;
			}
		} else if (servPath.endsWith(QConst.ACTION_SUFFIX_CONF)) {
			return new ConfGener().mapping(request);
		}
		log.debug(ret);
		return ret.toString();
	}

	private Map<String, Object> parameterMap2HashMap(Map<String, String[]> parameterMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<Entry<String, String[]>> it = parameterMap.entrySet().iterator();
		Entry<String, String[]> entry;
		String[] ss;
		log.info("::开始转换Request参数！");
		while (it.hasNext()) {
			entry = it.next();
			ss = entry.getValue();
			if (ss != null && ss.length > 0) {
				if (ss.length > 1) {
					log.warn("表单存在同名Parameter[" + entry.getKey() + "]");
				}
				map.put(entry.getKey(), entry.getValue()[0]);
				log.debug(entry.getKey() + "==[" + entry.getValue()[0] + "]");
			}
		}
		return map;
	}
}
