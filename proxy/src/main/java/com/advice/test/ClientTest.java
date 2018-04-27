package com.advice.test;

import com.advice.Greeting;
import com.advice.GreetingImpl;
import com.advice.after.GreetingAfterAdvice;
import com.advice.before.GreetingBeforeAdvice;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

/**
 * @author yuanlai
 * @date 2014-04-05
 */
public class ClientTest {

	@Test
	public void test01(){
		// 创建代理工厂
		ProxyFactory proxyFactory = new ProxyFactory();
		// 射入目标类对象
		proxyFactory.setTarget(new GreetingImpl());
		// 添加前置增强
		proxyFactory.addAdvice(new GreetingBeforeAdvice());
		// 添加后置增强
		proxyFactory.addAdvice(new GreetingAfterAdvice());

		// 从代理工厂中获取代理
		Greeting greeting = (Greeting)proxyFactory.getProxy();
		// 调用代理的方法
		greeting.say("123");

	}
}
