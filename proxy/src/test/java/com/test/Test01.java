package com.test;

import com.proxy.Hello;
import com.proxy.cglibproxy.CGLibProxy;
import com.proxy.cglibproxy.CGLibProxy1;
import com.proxy.cglibproxy.HelloCGLib;
import com.proxy.dynamicproxy.DynamicProxy;
import com.proxy.dynamicproxy.DynamicProxy1;
import com.proxy.staticproxy.HelloImpl;
import com.proxy.staticproxy.HelloProxy;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * @author yuanlai
 * @date 2014-04-05
 */
public class Test01 {

	@Test
	public void test01(){
		Hello hello = new HelloProxy();
		hello.say("xx");
	}

	/**
	 * 用通用的DynamicProxy类去包装HelloImpl实例，然后调用JDK提供的Proxy类的工厂方法newProxyInstance去动态的创建一个Hello接口的代理类。
	 *
	 * Proxy.newProxyInstance 方法的参数：
	 *   参数 1：ClassLoader 和目标对象的类加载器保持一致
	 *   参数 2：该实现类的所有接口 目标对象实现的接口，因为需要根据接口动态生成对象
	 *   参数 3：InvocationHandler:事件处理器，即动态代理对象
	 */
	@Test
	public void test02(){
		Hello hello = new HelloImpl();
		DynamicProxy1 dynamicProxy = new DynamicProxy1(hello);

		Hello helloProxy = (Hello) Proxy.newProxyInstance(
				hello.getClass().getClassLoader(),
				hello.getClass().getInterfaces(),
				dynamicProxy
		);

		helloProxy.say("1234");
	}

	/**
	 * 对 test02中Proxy.newProxyInstance进行重构
	 */
	@Test
	public void test03(){
		DynamicProxy dynamicProxy = new DynamicProxy(new HelloImpl());

		Hello helloProxy = dynamicProxy.getProxy();

		helloProxy.say("abc");
	}

	/**
	 * CGLib 代理
	 */
	@Test
	public void test04(){
		CGLibProxy1 cgLibProxy = new CGLibProxy1();
		Hello helloProxy = cgLibProxy.getProxy(HelloImpl.class);
		helloProxy.say("cglib");
	}

	/**
	 * 重构test04
	 */
	@Test
	public void test05(){
		HelloCGLib helloProxy = CGLibProxy.getInstance().getProxy(HelloCGLib.class);
        helloProxy.aa("adfa");
	}
}
