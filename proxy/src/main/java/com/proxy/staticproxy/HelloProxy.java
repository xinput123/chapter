package com.proxy.staticproxy;

import com.proxy.Hello;

/**
 * HelloProxy类实现了Hello接口(和HelloImpl实现相同的接口)，并且在构造方法中new出来一个HelloImpl类的实例。
 * 这样我们就可以在HelloProxy的say方法里面去调用HelloImpl的say方法了。
 * @author yuanlai
 * @date 2014-04-05
 */
public class HelloProxy implements Hello {

	private Hello hello;

	public HelloProxy(){
		hello = new HelloImpl();
	}

	@Override
	public void say(String name) {
        befor();
        hello.say("hello! " + name);
        after();
	}

	private void befor(){
		System.out.println("before");
	}

	private void after(){
		System.out.println("after");
	}
}
