package org.smart4j.framework.helper;

import org.smart4j.framework.util.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean助手类
 * 说明： BeanHelper就相当于一个"Bean容器"了，因为 Bean Map中存在了Bean类与Bean实例的映射关系，我们
 * 只需要调用getbean方法，传入一个Bean类，就能获取Bean实例
 * @author yuanlai
 * @date 2014-04-01
 */
public class BeanHelper {

	/**
	 * 定义Bean映射(用于存放Bean类与Bean实例的映射关系)
	 */
	private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<>();

	static {
		Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
		for(Class<?> beanClass : beanClassSet){
			Object obj = ReflectionUtils.newInstance(beanClass);
			BEAN_MAP.put(beanClass, obj);
		}
	}

	/**
	 * 获取 Bean 映射
	 * @return
	 */
	public static Map<Class<?>,Object> getBeanMap(){
		return BEAN_MAP;
	}

	/**
	 * 获取 Bean 映射
	 * @param cls
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> cls){
		if(!BEAN_MAP.containsKey(cls)){
			throw new RuntimeException("can not get bean by class: " + cls);
		}

		return (T) BEAN_MAP.get(cls);
	}

	/**
	 * 设置Bean实例
	 * @param cls
	 * @param obj
	 */
	public static void setBean(Class<?> cls, Object obj){
        BEAN_MAP.put(cls,obj);
	}

}
