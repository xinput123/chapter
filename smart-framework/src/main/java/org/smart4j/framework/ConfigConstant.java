package org.smart4j.framework;

/**
 * 提供相关配置项常量
 * @author yuanlai
 * @date 2014-04-01
 */
public interface ConfigConstant {

	String COMFIG_FILE = "smart.properties";

	String JDBC_DRIVER = "smart.frameword.jdbc.driver";
	String JDBC_URL = "smart.frameword.jdbc.url";
	String JDBC_USERNAME = "smart.frameword.jdbc.username";
	String JDBC_PASSWORD = "smart.frameword.jdbc.password";

	String APP_BASE_PACKAGE = "smart.frameword.app.base_package";
	String APP_JSP_PATH = "smart.frameword.app.jsp_path";
	String APP_ASSET_PATH = "smart.frameword.app.asset_path";

	String APP_UPLOAD_LIMIT = "smart.frameword.app.upload_limit";
}
