package wjm.test;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import wjm.common.exception.DataStoreException;
import wjm.common.exception.SuperQueryException;
import wjm.query.data.DataSourceFactory;
import wjm.query.data.DataStore;
import wjm.query.meta.SysQueryconfBO;
import wjm.query.page.ConditionBean;

public class Test {
	private static final Logger log = Logger.getLogger(Test.class);
	public static String sql = "select b.table_name tabname,                                      "
			+ "       c.comments tabcomment,                                        "
			+ "       b.column_id colid,                                            "
			+ "       b.column_name colname,                                        "
			+ "       b.data_type datatype,                                         "
			+ "       b.data_length datalength,                                     "
			+ "       b.data_precision dataprecision,                               "
			+ "       b.data_scale datascale,                                       "
			+ "       a.comments colcnmment                                         "
			+ "from   all_col_comments a, all_tab_columns b, all_tab_comments c     "
			+ "where  a.table_name = b.table_name                                   "
			+ "       and a.table_name = c.table_name                               "
			+ "       and a.column_name = b.column_name                             "
			+ "       and a.owner = b.owner                                         "
			+ "       and a.owner = c.owner                                         "
			+ "       and lower(a.table_name) = 'sys_queryconf' order by b.column_id asc                 ";

	public String testDataSource() {
		StringBuffer ret = new StringBuffer();
		DataSource ds = DataSourceFactory.getDataSource();
		Connection conn = null;
		try {
			conn = ds.getConnection();
			Statement stat = conn.createStatement();
			if (stat.execute(sql)) {
				ResultSet res = stat.getResultSet();
				String buf;
				ResultSetMetaData meta = res.getMetaData();
				int colcount = meta.getColumnCount();
				log.info(sql);
				log.info("column count:" + colcount);
				ret.append("<table border='1'>");
				ret.append("<tr>");
				for (int i = 1; i <= colcount; i++) {
					ret.append("<th>");
					ret.append(meta.getColumnName(i));
					ret.append("</th>");
				}
				ret.append("</tr>");
				while (res.next()) {
					ret.append("<tr>");
					for (int i = 1; i <= colcount; i++) {
						ret.append("<td>");
						buf = res.getString(i);
						ret.append(buf);
						ret.append("</td>");
					}
					ret.append("</tr>");
				}
				ret.append("</table>");
			}

		} catch (SQLException e) {
			log.error("", e);
			return "ERROR:" + e.getMessage();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("", e);
					ret.append("<hr/>关闭数据库连接失败！");
				}
			}
		}
		return ret.toString();
	}

	public String testDataStore() {
		String ret = "OK!!!";
		DataStore ds;
		try {
			ds = DataStore.instance();
			//ds.selectBOList(sql, TableStructBO.class);
			ret += new ConditionBean("query_tabstruct", null).toHtml();
			ret += "<hr/>";
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("tabname", "SYS_QUERYCONF");
			ret += ds.selectTableBean("query_tabstruct", param).toHtmlListTable(true);
		} catch (DataStoreException e) {
			ret = e.getMessage();
			log.error("",e);
		} catch (SuperQueryException e) {
			ret = e.getMessage();
			log.error("",e);
		}
		return ret;
	}

	public static void main(String[] args) {
		try {
			SysQueryconfBO bo = SysQueryconfBO.class.newInstance();
			PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bo);
			for (PropertyDescriptor pd : descriptors) {
				System.out.println(pd.getName());
			}
			Class idc = PropertyUtils.getPropertyType(bo, "id");
			if (idc != null) {
				Serializable id = (Serializable) idc.newInstance();
				descriptors = PropertyUtils.getPropertyDescriptors(id);
				for (PropertyDescriptor pd : descriptors) {
					System.out.println(pd.getName());
				}
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

	}
}
