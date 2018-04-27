package com.proxy.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLib 动态代理
 *   动态代理的实现对于类必须要有实现的接口，而对于没有实现任何接口的类，动态代理就搞不定了。而CGLib动态代理可以
 *
 * @author yuanlai
 * @date 2014-04-05
 */
public class CGLibProxy1 implements MethodInterceptor {

	public <T> T getProxy(Class<T> cls){
		return (T) Enhancer.create(cls,this);
	}

	/**
	 * 实现CGLib提供的 MethodInterceptor 实现类，并填充 intercept 方法。CGLib给我们提供的是方法级别的代理，也可以理解为
	 * 为对方法的拦截(方法拦截器)。
	 * @param obj
	 * @param method
	 * @param objs
	 * @param proxy
	 * @return
	 * @throws Throwable
	 */
	@Override
	public Object intercept(Object obj, Method method, Object[] objs, MethodProxy proxy) throws Throwable {
        befor();
        Object result = proxy.invokeSuper(obj,objs);
        after();
		return result;
	}

	private void befor(){
		System.out.println("before");
	}

	private void after(){
		System.out.println("after");
	}
}
