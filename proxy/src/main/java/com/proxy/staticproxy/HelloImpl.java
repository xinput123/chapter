package com.proxy.staticproxy;

import com.proxy.Hello;

/**
 * @author yuanlai
 * @date 2014-04-05
 */
public class HelloImpl implements Hello {
	@Override
	public void say(String name) {
		System.out.println("Hello! " + name);
	}
}
