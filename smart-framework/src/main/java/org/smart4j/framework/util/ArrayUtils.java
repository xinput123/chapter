package org.smart4j.framework.util;

/**
 * 数组工具类
 * @author yuanlai
 * @date 2014-04-01
 */
public final class ArrayUtils {

	/**
	 * 判断数组是否为空
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(Object[] array){
		return null==array ? true : array.length==0 ? true : false;
	}

	/**
	 * 判断数组是否非空
	 * @param array
	 * @return
	 */
	public static boolean isNotEmpty(Object[] array){
		return !isEmpty(array);
	}
}
