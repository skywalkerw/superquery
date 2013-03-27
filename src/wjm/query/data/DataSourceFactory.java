package wjm.query.data;

import javax.sql.DataSource;

import wjm.common.util.SpringUtil;

public class DataSourceFactory {
	public  static DataSource getDataSource(){
		return (DataSource) SpringUtil.findBean("dataSource");
	}
}
