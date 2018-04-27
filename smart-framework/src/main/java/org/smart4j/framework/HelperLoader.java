package org.smart4j.framework;

import org.smart4j.framework.helper.AopHelper;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.helper.IocHelper;
import org.smart4j.framework.util.ClassUtils;

/**
 * 初始化框架，加载ClassHelper、BeanHelper、IocHelper、ControllerHelper，加载它们的静态块
 * @author yuanlai
 * @date 2014-04-05
 */
public class HelperLoader {

	/**
	 * AopHelper 要在 IocHelper 之前加载，因为首先需要通过 AopHelper 获取代理对象，然后才能通过 IocHelper 进行依赖注入。
	 */
	public static void init(){
		Class<?>[] classList = {
				ClassHelper.class,
				BeanHelper.class,
				AopHelper.class,
				IocHelper.class,
				ControllerHelper.class
		};

		for(Class<?> cls : classList){
			ClassUtils.loadClass(cls.getName(),false);
		}
	}

}
