package org.smart4j.framework.util;

/**
 * 字符串工具类
 * @author yuanlai
 * @date 2014-03-27
 */
public final class StringUtils {

	/**
	 * 字符串分隔符
	 */
	public static final String SEPARATOR = String.valueOf((char)29);

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
        if(null!=str){
        	str = str.trim();
        }

        return StringUtils.isEmpty(str);
	}

	/**
	 * 判断字符串是否非空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}

	/**
	 * 根据指定字符串切割数据
	 * @param message
	 * @param split
	 * @return
	 */
	public static String[] splitString(String message, String split){
        if(StringUtils.isEmpty(message)){
        	return null;
        }
        if(StringUtils.isEmpty(split)){
        	return new String[]{message};
        }

        return message.split(split);
	}
}
