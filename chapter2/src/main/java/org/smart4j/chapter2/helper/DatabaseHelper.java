package org.smart4j.chapter2.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.util.ColleactionUtils;
import org.smart4j.chapter2.util.PropsUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库操作助手类
 * @author yuanlai
 * @date 2014-03-28
 */
public final class DatabaseHelper {

	private final static Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

	private static final QueryRunner QUERY_RUNNER;

	private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL;

	private static final BasicDataSource DATA_SOURCE;


	static {
		CONNECTION_THREAD_LOCAL = new ThreadLocal<>();
		QUERY_RUNNER = new QueryRunner();

		Properties conf = PropsUtils.loadProps("config.properties");
		String driver = conf.getProperty("jdbc.driver");
		String url = conf.getProperty("jdbc.url");
		String username = conf.getProperty("jdbc.username");
		String password = conf.getProperty("jdbc.password");

		DATA_SOURCE = new BasicDataSource();
		DATA_SOURCE.setDriverClassName(driver);
		DATA_SOURCE.setUrl(url);
		DATA_SOURCE.setUsername(username);
		DATA_SOURCE.setPassword(password);
	}

	/**
	 * 获取数据库连接
	 * @return
	 */
	public static Connection getConnection(){
		// 当每次获取Connection时，首先在ThreadLocal中寻找，若不存在，则创建一个，并放入ThreadLocal中
		Connection conn = CONNECTION_THREAD_LOCAL.get();
		if(null==conn){
			try {
				conn = DATA_SOURCE.getConnection();
			}catch (SQLException e){
				logger.error("get connection failure", e);
				throw new RuntimeException(e);
			}finally {
				CONNECTION_THREAD_LOCAL.set(conn);
			}
		}

		return conn;
	}


	/**
	 * 查询实体列表
	 *
	 * DbUtils提供的QueryRunner对象可以面向实体进行查询。实际上，DbUtils首先执行SQL语句，并返回一个ResultSet，
	 * 随后通过反射区创建并初始化实体对象。
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params){
		List<T> entityList;
		try {
			Connection conn = getConnection();
			entityList = QUERY_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass),params);
		}catch (Exception e){
			logger.error("query entity list failure", e);
			throw new RuntimeException(e);
		}

		return entityList;
	}

	/**
	 * 查询实体
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @param <T>
	 * @return
	 */
	public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params){
		T entity;
		try {
			Connection conn = getConnection();
			entity = QUERY_RUNNER.query(conn,sql,new BeanHandler<T>(entityClass),params);
		}catch (Exception e){
			logger.error("query entity list failure", e);
			throw new RuntimeException(e);
		}

		return entity;
	}

	/**
	 * 执行查询语句
	 * 可能进行夺标查询，输入一个SQL和动态参数，输出一个List>对象，其中的Map表示列名与列值的映射关系
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Map<String,Object>> executeQuery( String sql, Object... params){
		List<Map<String,Object>> result;
		try {
			Connection conn = getConnection();
			result = QUERY_RUNNER.query(conn,sql,new MapListHandler(),params);
		}catch (Exception e){
			logger.error("query entity list failure", e);
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * 执行更新语句(包括update，insert，delete)
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int executeUpdate(String sql, Object... params){
        int rows = 0;
        try{
        	Connection conn = getConnection();
        	rows = QUERY_RUNNER.update(conn,sql,params);
        }catch (SQLException e){
        	logger.error("execute update failure", e);
            throw new RuntimeException(e);
        }

        return rows;
	}

	/**
	 * 插入实体
	 * @param entityClass
	 * @param fieldMap
	 * @param <T>
	 * @return
	 */
	public static <T> boolean insertEntity(Class<T> entityClass, Map<String,Object> fieldMap){
		if(ColleactionUtils.isEmpty(fieldMap)){
			logger.error("can not insert entity: fieldMap is empty");
			return false;
		}

		String sql = "INSERT INTO " + getTableName(entityClass);
		StringBuilder columns = new StringBuilder();
		StringBuilder values = new StringBuilder();

		for(String fieldName : fieldMap.keySet()){
			columns.append(fieldName).append(", ");
			values.append("?, ");
		}

		columns.replace(columns.lastIndexOf(", "), columns.length(), ") ");
		values.replace(values.lastIndexOf(", "), values.length(), ")");
		sql += columns + " VALUES " + values;

		Object[] params = fieldMap.values().toArray();
		return executeUpdate(sql,params)==1;

	}

	/**
	 * 更新实体
	 * @param entityClass
	 * @param id
	 * @param fieldMap
	 * @param <T>
	 * @return
	 */
	public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String,Object> fieldMap){
		if(ColleactionUtils.isEmpty(fieldMap)){
			logger.error("can not update entity: fieldMap is empty");
			return false;
		}

		String sql = "UPDATE " + getTableName(entityClass) + " SET ";
		StringBuilder columns = new StringBuilder();
		for(String fieldName : fieldMap.keySet()){
			columns.append(fieldName).append("=?, ");
		}

		sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id = ?";

		List<Object> paramList =  new ArrayList<>();
		paramList.addAll(fieldMap.values());
		paramList.add(id);
		Object[] params = paramList.toArray();

		return executeUpdate(sql,params)==1;

	}

	/**
	 * 删除实体
	 * @param entityClass
	 * @param id
	 * @param <T>
	 * @return
	 */
	public static <T> boolean deleteEntity(Class<T> entityClass, long id){
		String sql = "DELETE FROM " + getTableName(entityClass) + "WHERE id = ?";
		return executeUpdate(sql,id) == 1;
	}


	/**
	 * 根据类型获取表名
	 * @param entityClass
	 * @return
	 */
	private static String getTableName(Class<?> entityClass){
		return entityClass.getSimpleName();
	}
}
