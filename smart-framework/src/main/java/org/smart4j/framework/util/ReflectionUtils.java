package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * @author yuanlai
 * @date 2014-04-01
 */
public class ReflectionUtils {

	private static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

	/**
	 * 创建实例
	 * @param cls
	 * @return
	 */
	public static Object newInstance(Class<?> cls){
		Object instance;
		try {
			instance = cls.newInstance();
		}catch (Exception e){
			logger.error("new Instance failure", e);
			throw new RuntimeException(e);
		}

		return instance;
	}

	/**
	 * 调用方法
	 * @param obj
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(Object obj, Method method, Object... args){
		Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        }catch (Exception e){
	        logger.error("invoke Method failure", e);
	        throw new RuntimeException(e);
        }
		return result;
	}

	public static void setField(Object obj, Field field, Object value){
		try {
			field.setAccessible(true);
			field.set(obj,value);
		}catch (Exception e){
			logger.error("set field failure", e);
			throw new RuntimeException(e);
		}
	}
}
