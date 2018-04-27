package com.advice.around;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author yuanlai
 * @date 2014-04-05
 */
public class GreetingAroundAdvice implements MethodInterceptor{
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		before();
		Object result = methodInvocation.proceed();
		after();
		return result;
	}

	private void before(){
		System.out.println("Before");
	}

	private void after(){
		System.out.println("After");
	}
}
