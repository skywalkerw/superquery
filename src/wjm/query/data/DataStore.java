package wjm.query.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import wjm.common.exception.DataStoreException;
import wjm.common.exception.SuperQueryException;
import wjm.common.util.SpringUtil;
import wjm.common.util.StringUtil;
import wjm.query.common.base.BOUtil;
import wjm.query.common.base.BaseBO;
import wjm.query.page.TableBean;

/**
 * @author Administrator WJM 2012-12-22上午10:41:07
 */
public class DataStore {
	private static final Logger log = Logger.getLogger(DataStore.class);
	private static final String DATASTORE = "dataStore";
	private DataSource dataSource;

	/**
	 * 返回Map类型的List类型
	 */
	public static final int RESULT_MAPLIST = 0;
	/**
	 * 返回二维list
	 */
	public static final int RESULT_LISTLIST = 1;
	/**
	 * 返回TableBean
	 */
	public static final int RESULT_TABLEBEAN = 2;
	/**
	 * 返回BO类型的List
	 */
	public static final int RESULT_BOLIST = 3;

	public static DataStore instance() {
		return (DataStore) SpringUtil.findBean(DATASTORE);
	}

	public <T extends Object> List<Map<String, T>> selectMapList(String sql) throws DataStoreException {
		return select(sql, RESULT_MAPLIST);
	}

	public BigDecimal selectCount(String queryid, Map<String, Object> param) throws SuperQueryException,
			DataStoreException {
		String sql = new SqlMaker(queryid, param).makeSqlcount();
		List<Map<String, Object>> list = select(sql, RESULT_MAPLIST, queryid);
		return (BigDecimal) list.get(0).get("COUNT");
	}

	public TableBean selectTableBeanByPage(String queryid, Map<String, Object> param, int startIndex, int pageSize)
			throws DataStoreException, SuperQueryException {
		String sql = new SqlMaker(queryid, param).makeSqlByPage(startIndex, pageSize);
		return select(sql, RESULT_TABLEBEAN, queryid);
	}

	public TableBean selectTableBean(String queryid, Map<String, Object> param) throws DataStoreException,
			SuperQueryException {
		String sql = new SqlMaker(queryid, param).makeSqlSelect();
		return select(sql, RESULT_TABLEBEAN, queryid);
	}

	public <T extends Object> List<Map<String, T>> selectListList(String sql) throws DataStoreException {
		return select(sql, RESULT_LISTLIST);
	}

	public <T extends BaseBO> List<T> selectBOList(String sql, Class<T> classz) throws DataStoreException {
		return select(sql, RESULT_BOLIST, classz);
	}

	/**
	 * 执行查询，根据restype 返回结果集，统一做到一个方法里面是为了统一事务控制
	 * 
	 * @param sql
	 * @param restype
	 * @param objects
	 * @return
	 * @throws DataStoreException
	 */
	@SuppressWarnings("unchecked")
	private <T> T select(String sql, int restype, Object... objects) throws DataStoreException {
		Connection conn = null;
		T ret;
		try {
			conn = dataSource.getConnection();
			Statement state = conn.createStatement();
			log.info("SELECT-->" + sql);
			if (state.execute(sql)) {
				switch (restype) {
				case RESULT_MAPLIST:
					ret = (T) resultSet2MapList(state.getResultSet());
					break;
				case RESULT_LISTLIST:
					ret = (T) resultSet2ListList(state.getResultSet());
					break;
				case RESULT_TABLEBEAN:
					ret = (T) resultSet2TableBean(state.getResultSet(), (String) objects[0]);
					break;
				case RESULT_BOLIST:
					ret = (T) resultSet2BOList(state.getResultSet(), (Class) objects[0]);
					break;

				default:
					throw new DataStoreException("无法识别的结果集类型[" + restype + "]");
				}
			} else {
				throw new DataStoreException("数据操作没有返回结果集.");
			}
			conn.commit();
		} catch (SQLException e) {
			throw new DataStoreException("数据操作失败.", e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new DataStoreException("数据库连接关闭失败.", e);
			}
		}
		return ret;
	}

	private <T extends BaseBO> List<T> resultSet2BOList(ResultSet res, Class<T> classz) throws SQLException,
			DataStoreException {
		List<Map<String, Object>> datas = resultSet2MapList(res);
		List<T> list = new ArrayList<T>();

		for (int i = 0; i < datas.size(); i++) {
			T bo;
			try {
				bo = BOUtil.map2BO(datas.get(i), classz);
				list.add(bo);
			} catch (SuperQueryException e) {
				throw new DataStoreException("", e);
			}
		}
		return list;
	}

	private TableBean resultSet2TableBean(ResultSet res, String queryid) throws SQLException, DataStoreException {
		List<String> colnames = getAllColnames(res);
		List<Map<String, Object>> datas = resultSet2MapList(res);
		return new TableBean(colnames, datas, queryid);
	}

	private List<String> getAllColnames(ResultSet res) throws SQLException {
		List<String> headers = new ArrayList<String>();
		ResultSetMetaData meta = res.getMetaData();
		int colcount = meta.getColumnCount();
		for (int i = 1; i <= colcount; i++) {
			headers.add(StringUtil.upper(meta.getColumnName(i)));
		}
		return headers;
	}

	private <T extends Object> List<Map<String, T>> resultSet2MapList(ResultSet res) throws SQLException,
			DataStoreException {
		List<Map<String, T>> list = new ArrayList<Map<String, T>>();
		Map<String, T> row;
		ResultSetMetaData meta = res.getMetaData();
		int colcount = meta.getColumnCount();
		Object obj;
		while (res.next()) {
			row = new HashMap<String, T>();
			for (int i = 1; i <= colcount; i++) {
				if (row.containsKey(meta.getColumnLabel(i))) {
					throw new DataStoreException("数据机构转换不能返回Map.原因:结果集中存在重复列名[" + meta.getColumnLabel(i) + "]");
				}
				// log.info(meta.getColumnLabel(i) + "===" +
				// meta.getColumnType(i) + "=====" +
				// meta.getColumnClassName(i));
				switch (meta.getColumnType(i)) {
				case Types.INTEGER:
					obj = res.getInt(i);
					break;
				case Types.DOUBLE:
					obj = res.getDouble(i);
					break;
				case Types.NUMERIC:
					obj = res.getBigDecimal(i);
					break;
				default:
					obj = res.getObject(i);
					break;
				}
				// TODO 其他数据类型的转换
				row.put(StringUtil.upper(meta.getColumnLabel(i)), (T) obj);
			}
			list.add(row);
		}
		return list;
	}

	private <T extends Object> List<List<T>> resultSet2ListList(ResultSet res) throws SQLException, DataStoreException {
		List<List<T>> list = new ArrayList<List<T>>();
		List<T> row;
		ResultSetMetaData meta = res.getMetaData();
		int colcount = meta.getColumnCount();
		while (res.next()) {
			row = new ArrayList<T>();
			for (int i = 1; i <= colcount; i++) {
				row.add((T) res.getObject(i));
			}
			list.add(row);
		}
		return list;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public int excuteUpdateBath(String[] sql) throws DataStoreException {
		Connection conn = null;
		int[] size;
		int ret = 0;
		try {
			conn = dataSource.getConnection();
			Statement state = conn.createStatement();
			for (int i = 0; i < sql.length; i++) {
				state.addBatch(sql[i]);
				log.info("EXECUTEUPDATE-->" + sql[i]);
			}
			size = state.executeBatch();
			conn.commit();
			for (int i : size) {
				ret += i;
			}
			return ret;
		} catch (SQLException e) {
			log.error("操作失败，事务即将回滚!");
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				throw new DataStoreException("执行SQL失败且，事务回滚失败.", e);
			}
			throw new DataStoreException("SQL执行出错:" + e.getMessage(), e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new DataStoreException("数据库连接关闭失败.", e);
				}
			}
		}
	}
}
