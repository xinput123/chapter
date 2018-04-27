package org.smart4j.framework.helper;


import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.ArrayUtils;
import org.smart4j.framework.util.CollectionUtils;
import org.smart4j.framework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 *
 * 这是一个简单的IOC框架，这里Ioc框架中管理的对象都是单例的，由于Ioc框架底层还是从BeanHelper中获取的Bean Map的，而Bean Map
 * 中的对象都是事先创建好并放入这个bean容器的，所有的对象都是单例的。
 * @author yuanlai
 * @date 2014-04-01
 */
public class IocHelper {

	static {
		// 获取所有的Bean类与Bean实例之间的映射关系(简称 Bean Map)
		Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
		if( CollectionUtils.isNotEmpty(beanMap) ){
			// 遍历Bean Map
			for(Map.Entry<Class<?>, Object> beanEntry: beanMap.entrySet() ){
				// 从BeanMap中获取Bean类与Bean实例
				Class<?> beanClass = beanEntry.getKey();
				Object beanInstance = beanEntry.getValue();

				// 获取Bean 类定义的所有成员变量(简称Bean field)
				Field[] beanFields = beanClass.getDeclaredFields();

				if(ArrayUtils.isNotEmpty(beanFields)){
                    //遍历Bean Field
					for(Field beanField : beanFields){
						// 判断当前Bean Field是否带有Inject注解
						if(beanField.isAnnotationPresent(Inject.class)){
							// 在Bean Map中获取Bean Field对应的实例
							Class<?> beanFieldClass = beanField.getType();
							Object beanFieldInstance = beanMap.get(beanFieldClass);
							if(null!= beanFieldInstance){
								// 通过反射初始化 BeanField的值
								ReflectionUtils.setField(beanInstance,beanField,beanFieldInstance);
							}
						}
					}
				}
			}
		}
	}
}
