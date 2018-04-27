package com.advice.after;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * 后置增强类
 * @author yuanlai
 * @date 2014-04-05
 */
public class GreetingAfterAdvice implements AfterReturningAdvice{

	@Override
	public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
		System.out.println("After");
	}

}
