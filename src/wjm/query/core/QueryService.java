package wjm.query.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import wjm.common.exception.DataStoreException;
import wjm.common.exception.SuperQueryException;
import wjm.query.data.DataStore;
import wjm.query.page.PageUtil;
import wjm.query.page.TableBean;

public class QueryService {
	private static final Logger log = Logger.getLogger(QueryService.class);
	/**
	 * @param queryid 查询ID
	 * @param action 操作，比如是否显示详细信息
	 * @param conditions where子句的参数，查询条件
	 * @param pageIndex 页号
	 * @param pageSize 页大小
	 * @return
	 * @throws SuperQueryException
	 */
	public StringBuffer queryByPage(String queryid,boolean detail,Map<String, Object> conditions,int pageIndex,int pageSize) throws SuperQueryException{
		StringBuffer ret = new StringBuffer();
		try {
			long start = System.currentTimeMillis();
			DataStore ds = DataStore.instance();
			int startIndex = 1;
			startIndex = pageSize * (pageIndex - 1);
			log.info("pageIndex:" + pageIndex + ",pageSize:" + pageSize);
			BigDecimal recordCount = ds.selectCount(queryid, conditions);
			TableBean tbean = ds.selectTableBeanByPage(queryid, conditions, startIndex, pageSize);
			if(detail){
				ret.append(PageUtil.makeOutDetailByTableBean(tbean));
			}else{
				ret.append(PageUtil.makeOutListByTableBeanWithPage(tbean, conditions, pageIndex, pageSize, recordCount.intValue()));
			}
			log.info("查询["+queryid+"]处理完成,返回记录["+tbean.getRowsize()+"]条,耗时：["+(System.currentTimeMillis()-start)+"]ms");
		} catch (DataStoreException e) {
			throw new SuperQueryException(e.getMessage(), e);
		} catch (SuperQueryException e) {
			throw e;
		}
		return ret;
	}
	
	
}
