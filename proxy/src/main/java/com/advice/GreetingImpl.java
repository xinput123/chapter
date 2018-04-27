package com.advice;

/**
 * @author yuanlai
 * @date 2014-04-05
 */
public class GreetingImpl implements Greeting {
	@Override
	public void say(String name) {
        befor();
		System.out.println("Hello! " + name);
        after();
	}

	private void befor(){
		System.out.println("before");
	}

	private void after(){
		System.out.println("after");
	}
}
