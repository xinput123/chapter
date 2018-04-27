package org.smart4j.framework.proxy;

/**
 * 代理接口
 * @author yuanlai
 * @date 2014-04-05
 */
public interface Proxy {

	/**
	 * 执行链式代理
	 *   将多个代理通过一个链子串起来，一个个地去执行，执行顺序取决于添加到链上的先后顺序。
	 * @param proxyChain
	 * @return
	 * @throws Throwable
	 */
	Object doProxy(ProxyChain proxyChain) throws Throwable;

}
