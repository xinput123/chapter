package org.smart4j.chapter1.test02;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author yuanlai
 * @date 2014-04-07
 */
public class DBUtil {
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/demo";
	private static final String username = "root";
	private static final String password = "123456";

	private static Connection conn = null;

	public static Connection getConnection(){
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,username,password);
		}catch (Exception e){
			e.printStackTrace();
		}

		return conn;
	}

	public static void closeCConnection(){
		try {
			if(null!=conn){
				conn.close();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
