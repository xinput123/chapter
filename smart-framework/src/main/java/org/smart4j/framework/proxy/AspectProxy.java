package org.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 * @author yuanlai
 * @date 2014-04-06
 */
public abstract class AspectProxy implements Proxy {

	private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

	@Override
	public final Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;

		Class<?> cls = proxyChain.getTargetClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();

		begin();
		try {
			if(intercept(cls,method,params)){
				before(cls,method,params);
				result = proxyChain.doProxyChain();
				after(cls,method,params);
			}else {
				result = proxyChain.doProxyChain();
			}
		}catch (Exception e){
			logger.error("proxy failure", e);
			error(cls,method,params);
			throw e;
		}finally {
			end();
		}

		return result;
	}

	public void begin(){

	}

	public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable{
		return true;
	}

	public void before(Class<?> cls, Method method, Object[] params) throws Throwable{

	}

	public void after(Class<?> cls, Method method, Object[] params) throws Throwable{

	}

	public void error(Class<?> cls, Method method, Object[] params) throws Throwable{

	}

	public void end(){

	}
}
