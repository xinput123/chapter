package org.smart4j.chapter4.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.proxy.AspectProxy;

import java.lang.reflect.Method;

/**
 * 拦截Controller 所有方法
 * @author yuanlai
 * @date 2014-04-06
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy{

	private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

	private long begin;

	@Override
	public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
		logger.debug("------begin------");
		logger.debug(String.format("class: %s", cls.getName()));
		logger.debug(String.format("method: %s", method.getName()));
		begin = System.currentTimeMillis();
	}

	@Override
	public void after(Class<?> cls, Method method, Object[] params) throws Throwable {
		logger.debug(String.format("time:%dms",System.currentTimeMillis()-begin));
		logger.debug("------END------");
	}
}
