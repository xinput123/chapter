package org.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.helper.DatabaseHelper;

import java.lang.reflect.Method;

/**
 * 事务代理： 事务代理切面类
 *    实现Proxy接口，在doProxy方法中完成事务控制的相关逻辑
 * @author yuanlai
 * @date 2014-04-07
 */
public class TransactionProxy implements Proxy{

	private static final Logger logger = LoggerFactory.getLogger(TransactionProxy.class);

	/**
	 * 保证同一线程中事务控制相关逻辑只会执行一次
	 */
	private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>(){
		@Override
		protected Boolean initialValue(){
			return false;
		}
	};

	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result;
		boolean flag = FLAG_HOLDER.get();
		// 通过ProxyChain对象可获取目标方法
		Method method = proxyChain.getTargetMethod();
		// 判断该方法是否带有Transaction注解
		if(!flag && method.isAnnotationPresent(Transaction.class)){
			FLAG_HOLDER.set(true);
			try {
				// 开启事务
				DatabaseHelper.beginTransaction();
				logger.debug("begin transaction");
				// 执行目标方法
				result = proxyChain.doProxyChain();
				// 提交事务
				DatabaseHelper.commitTransaction();
				logger.debug("commit transaction");
			}catch (Exception e){
				// 回滚事务
                DatabaseHelper.rollbackTransaction();
				logger.debug("rollback transaction");
				throw e;
			}finally {
				// 移除本地线程变量中的标志
				FLAG_HOLDER.remove();
			}
		}else {
			result = proxyChain.doProxyChain();
		}
		return result;
	}
}
