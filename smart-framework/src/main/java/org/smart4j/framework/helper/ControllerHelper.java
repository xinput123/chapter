package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;
import org.smart4j.framework.util.ArrayUtils;
import org.smart4j.framework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 * 处理逻辑：
 *     通过ClassHelper，我们可以获取所有定义了Controller注解的类，可以通过反射获取该类中所有带有Action注解的方法，
 *     获取Action注解中的请求表达式，进而获取请求方法与请求路径，封装了一个请求对象(Request)与处理对象(Handler)，最
 *     后将Request与Handler建立一个映射关系，放入一个ActionMap中，并提供一个可根据请求方法与请求路径获取处理对象的方法。
 * @author yuanlai
 * @date 2014-04-02
 */
public class ControllerHelper {

	/**
	 * 用于存放请求与处理器的映射关系
	 */
	private static final Map<Request,Handler> ACTION_MAP = new HashMap<>();

	static {
		// 获取所有的Controller类
		Set<Class<?>> controllerClassSet  = ClassHelper.getControllerClassSet();

		if(CollectionUtils.isNotEmpty(controllerClassSet)){
			// 遍历这些Controller类
			for(Class<?> controllerClass : controllerClassSet){
				// 获取Controller类中定义方法
				Method[] methods = controllerClass.getDeclaredMethods();
				if(ArrayUtils.isNotEmpty(methods)){
					// 遍历这些Controller类中的方法
					for(Method method : methods){
						// 判断当前方法是否带有Action注解
						if(method.isAnnotationPresent(Action.class)){
							// 从Action注解中获取URL映射规则
							Action action = method.getAnnotation(Action.class);
							String mapping = action.value();
							// 验证URL映射规则
                            if(mapping.matches("\\w+:/\\w*")){
                            	String[] array = mapping.split(":");
                            	if(ArrayUtils.isNotEmpty(array) && 2==array.length){
                            		// 获取请求方法与请求路径
		                            String requestMethod = array[0];
		                            String requestPath = array[1];

		                            Request request = new Request(requestMethod,requestPath);
		                            Handler handler = new Handler(controllerClass,method);

		                            // 初始化 ACTION_MAP
		                            ACTION_MAP.put(request,handler);
	                            }
                            }
						}
					}
				}
			}
		}
	}


	public static Handler getHandler(String requestMethod, String requestPath){
		Request request = new Request(requestMethod,requestPath);
		return ACTION_MAP.get(request);
	}
}
