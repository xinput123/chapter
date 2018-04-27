package com.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * DynamicProxy实现了InvocationHandler接口，那么必须实现该接口的invoke方法
 *
 * @author yuanlai
 * @date 2014-04-05
 */
public class DynamicProxy implements InvocationHandler {

	private Object target;

	public DynamicProxy(Object target){
		this.target = target;
	}

	@SuppressWarnings("unchecked")
	public <T> T getProxy(){
		return (T) Proxy.newProxyInstance(
				target.getClass().getClassLoader(),
				target.getClass().getInterfaces(),
				this
		);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		befor();
		Object result = method.invoke(target,args);
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
