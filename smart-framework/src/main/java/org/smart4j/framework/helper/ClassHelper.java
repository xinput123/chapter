package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手类
 * @author yuanlai
 * @date 2014-04-01
 */
public class ClassHelper {

	/**
	 * 定义类集合
	 */
	private static final Set<Class<?>> CLASS_SET;

	static {
		String baskPackage = ConfigHelper.getAppBasePackage();
		CLASS_SET = ClassUtils.getClassSet(baskPackage);
	}

	/**
	 * 获取应用包名下的所有类
	 * @return
	 */
	public static Set<Class<?>> getClassSet(){
		return CLASS_SET;
	}

	/**
	 * 获取应用包名下的所有Service类
	 * @return
	 */
	public static Set<Class<?>> getServiceClassSet(){
		Set<Class<?>> classSet = new HashSet<>();
		for(Class<?> cls : CLASS_SET){
			if(cls.isAnnotationPresent(Service.class)){
				classSet.add(cls);
			}
		}

		return classSet;
	}

	/**
	 * 获取应用包名下所有Controller类
	 * @return
	 */
	public static Set<Class<?>> getControllerClassSet(){
		Set<Class<?>> classSet = new HashSet<>();
		for(Class<?> cls : CLASS_SET){
			if(cls.isAnnotationPresent(Controller.class)){
				classSet.add(cls);
			}
		}

		return classSet;
	}

	/**
	 * 获取应用包名下所有bean类(包括：Service、Controller)
	 * @return
	 */
	public static Set<Class<?>> getBeanClassSet(){
		Set<Class<?>> classSet = new HashSet<>();
		getBeanClassSet().addAll(getServiceClassSet());
        getBeanClassSet().addAll(getControllerClassSet());
		return classSet;
	}

	/**
	 * 获取应用包名下某父类(或接口)的所有子类(或实现类)
	 * @param superClass
	 * @return
	 */
	public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
		Set<Class<?>> classSet = new HashSet<>();
		for(Class<?> cls : CLASS_SET){
			if(superClass.isAssignableFrom(cls) && !superClass.equals(cls)){
				classSet.add(cls);
			}
		}

		return classSet;
	}

	/**
	 * 获取应用包名下带有某注解的所有类
	 * @param annotationClass
	 * @return
	 */
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass ){
		Set<Class<?>> classSet = new HashSet<>();
		for(Class<?> cls : classSet){
			if(cls.isAnnotationPresent(annotationClass)){
				classSet.add(cls);
			}
		}

		return classSet;
	}
}
