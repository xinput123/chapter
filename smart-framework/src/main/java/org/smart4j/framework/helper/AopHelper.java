package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.ProxyManager;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 方法拦截助手类
 * @author yuanlai
 * @date 2014-04-06
 */
public class AopHelper {

	private static final Logger logger = LoggerFactory.getLogger(AopHelper.class);

	static {
		try {
			Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
			for(Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()){
				Class<?> targetClass = targetEntry.getClass();
				List<Proxy> proxyList = targetEntry.getValue();
				Object proxy = ProxyManager.createProxy(targetClass,proxyList);
				BeanHelper.setBean(targetClass,proxy);
			}
		}catch (Exception e){
			logger.error("aop failure", e);
		}
	}

	/**
	 * 获取代理类及其目标类集合之间的映射关系，一个代理类可对应一个或多个目标类。这里的代理指的是切面类。
	 * @return
	 * @throws Exception
	 */
	private static Map<Class<?>,Set<Class<?>>> createProxyMap() throws Exception{
		Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<>();

		addAspectProxy(proxyMap);
		addTransactionProxy(proxyMap);

		return proxyMap;
	}

	/**
	 * 添加普通私有代理
	 * @param proxyMap
	 * @throws Exception
     */
	private static void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
		Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
		for(Class<?> proxyClass : proxyClassSet){
			if(proxyClass.isAnnotationPresent(Aspect.class)){
				Aspect aspect = proxyClass.getAnnotation(Aspect.class);
				Set<Class<?>> targetClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
				proxyMap.put(proxyClass, targetClassSet);
			}
		}
	}

	/**
	 * 添加事务代理
	 * @param proxyMap
     */
	private static void addTransactionProxy(Map<Class<?>,Set<Class<?>>> proxyMap){
		Set<Class<?>> serviceClassSet = ClassHelper.getServiceClassSet();
		proxyMap.put(Transaction.class,serviceClassSet);
	}

	/**
	 * 获取Aspet注解中设置的注解类，若该注解类不是Aspect类，则可调用 ClassHelper.getClassSetByAnnotation 方法获取相关类，
	 * 并把这些类放入目标集合中，最终返回这个集合。
	 * @param aspect
	 * @return
	 * @throws Exception
	 */
	private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception{
		Set<Class<?>> targetClassSet = new HashSet<>();
		Class<? extends Annotation> annotation = aspect.value();
		if(null!=annotation && !annotation.equals(Aspect.class)){
			targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
		}

		return targetClassSet;
	}

	/**
	 * 根据代理类与目标类集合之间的关系，根据这个关系分析出目标类与代理列表之间的映射关系
	 * @param proxyMap
	 * @return
	 * @throws Exception
	 */
	private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();

		for(Map.Entry<Class<?>,Set<Class<?>>> proxyEntry : proxyMap.entrySet()){
            Class<?> proxyClass = proxyEntry.getKey();
			Set<Class<?>> targetClassSet = proxyEntry.getValue();
			for(Class<?> targetClass : targetClassSet){
				Proxy proxy = (Proxy) proxyClass.newInstance();
				if(targetMap.containsKey(targetClass)){
					targetMap.get(targetClass).add(proxy);
				}else {
					List<Proxy> proxyList = new ArrayList<>();
					proxyList.add(proxy);
					targetMap.put(targetClass,proxyList);
				}
			}
		}

		return targetMap;
	}
}
