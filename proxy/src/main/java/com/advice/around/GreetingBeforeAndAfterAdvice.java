package com.advice.around;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author yuanlai
 * @date 2014-04-05
 */
public class GreetingBeforeAndAfterAdvice implements MethodBeforeAdvice,AfterReturningAdvice {

	@Override
	public void before(Method method, Object[] objects, Object o) throws Throwable {
		System.out.println("Before");
	}

	@Override
	public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
		System.out.println("After");
	}
}
