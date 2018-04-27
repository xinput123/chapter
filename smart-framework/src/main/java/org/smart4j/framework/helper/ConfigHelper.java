package org.smart4j.framework.helper;

import org.smart4j.framework.ConfigConstant;
import org.smart4j.framework.util.PropsUtils;

import java.util.Properties;

/**
 * 属性文件助手类
 * @author yuanlai
 * @date 2014-04-01
 */
public class ConfigHelper {

	private static final Properties CONFIG_PROPS = PropsUtils.loadProps(ConfigConstant.COMFIG_FILE);

	/**
	 * 获取JDBC驱动
	 * @return
	 */
	public static String getJdbcDriver(){
		return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
	}

	/**
	 * 获取JDBC URL
	 * @return
	 */
	public static String getJdbcUrl(){
		return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
	}

	/**
	 * 获取JDBC 用户名
	 * @return
	 */
	public static String getJdbcUsername(){
		return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
	}

	/**
	 * 获取JDBC 密码
	 * @return
	 */
	public static String getJdbcPassword(){
		return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
	}

	/**
	 * 获取应用基础包名
	 * @return
	 */
	public static String getAppBasePackage(){
		return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
	}

	/**
	 * 获取应用 JSP 路径
	 * @return
	 */
	public static String getAppJspPath(){
		return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH,"WEB-INF/view/");
	}

	/**
	 * 获取应用静态资源路径
	 * @return
	 */
	public static String getAppAssetPath(){
		return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH,"/asset/");
	}

	/**
	 * 获取应用文件上传限制
	 * @return
     */
	public static int getAppUploadLimit(){
		return PropsUtils.getInt(CONFIG_PROPS, ConfigConstant.APP_UPLOAD_LIMIT,10);
	}
}
