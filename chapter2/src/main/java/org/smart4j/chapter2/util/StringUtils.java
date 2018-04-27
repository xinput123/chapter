package org.smart4j.chapter2.util;

/**
 * 字符串工具类
 * @author yuanlai
 * @date 2014-03-27
 */
public final class StringUtils {

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
}
