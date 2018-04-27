package org.smart4j.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器
 *   提供一个创建代理对象的方法啊，输入一个目标类和一组proxy接口实现，输出一个代理对象。
 * @author yuanlai
 * @date 2014-04-06
 */
public class ProxyManager {

	@SuppressWarnings("unchecked")
	public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList){
		// 使用CGLib提供的Enhance#create方法来创建代理对象，将intercept的参数传入ProxyChain的构造器即可。
		return (T) Enhancer.create(targetClass, new MethodInterceptor() {
			@Override
			public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
				return new ProxyChain(targetClass,targetObject,targetMethod,methodProxy,methodParams,proxyList);
			}
		});
	}
}
