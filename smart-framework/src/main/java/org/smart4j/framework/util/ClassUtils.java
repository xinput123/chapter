package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 * @author yuanlai
 * @date 2014-04-01
 */
public class ClassUtils {

	private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);

	/**
	 * 获取类加载器
	 * 实现： 只需要获取当前线程中的ClassLoader即可
	 * @return
	 */
	public static ClassLoader getClassLoader(){
		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * 加载类
	 * 实现： 需要提供类名与是否初始化的标志，这里指的初始化指的是是否执行类的静态代码块
	 * @param className
	 * @param isInitialized
	 * @return
	 */
	public static Class<?> loadClass(String className, boolean isInitialized){
		Class<?> cls;
		try {
			cls = Class.forName(className,isInitialized,getClassLoader());
		}catch (ClassNotFoundException e){
			logger.error("load class failure", e);
			throw new RuntimeException(e);
		}
		return cls;
	}

	/**
	 * 获取指定包名下的所有类
	 * 实现： 根据包名并将其转换为文件路径，读取class文件或jar包，获取指定的类名去加载类。
	 * @param packageName
	 * @return
	 */
	public static Set<Class<?>> getClassSet(String packageName){
		Set<Class<?>> classSet = new HashSet<>();
		try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".","/"));
            while (urls.hasMoreElements()){
            	URL url = urls.nextElement();
            	if(null!=url){
            		String protocal = url.getProtocol();
            		if("file".equals(protocal)){
            			String packagePath = url.getPath().replaceAll("%20"," ");
            			addClass(classSet,packagePath,packageName);
		            }else if("jar".equals(protocal)){
			            JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
			            if(null!=jarURLConnection){
				            JarFile jarFile = jarURLConnection.getJarFile();
				            if(null!=jarFile){
				            	Enumeration<JarEntry> jarEntries = jarFile.entries();
				            	while (jarEntries.hasMoreElements()){
				            		JarEntry jarEntry = jarEntries.nextElement();
				            		String jarEntryName = jarEntry.getName();
				            		if(jarEntryName.endsWith(".class")){
				            			String className = jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).replaceAll("/",".");
				            			doAddClass(classSet, className);
						            }
					            }
				            }
			            }
		            }
	            }
            }
		}catch (Exception e){
            logger.error("get class set failure", e);
            throw new RuntimeException(e);
		}
		return classSet;
	}

	private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName){
		File[] files = new File(packagePath).listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
			}
		});

		for(File file : files){
			String fileName = file.getName();
			if(file.isFile()){
				String className = fileName.substring(0,fileName.lastIndexOf("."));
				if(StringUtils.isNotEmpty(packageName)){
					className = packageName + className;
				}
                doAddClass(classSet,className);
			}else {
				String subPackagePath = fileName;
				if(StringUtils.isNotEmpty(packageName)){
					subPackagePath = packagePath + "." + subPackagePath;
				}
				String subPackageName = fileName;
				if(StringUtils.isNotEmpty(packageName)){
					subPackageName = packageName + "." + subPackageName;
				}
				addClass(classSet,subPackagePath,subPackageName);
			}
		}
	}

	private static void doAddClass(Set<Class<?>> classSet, String className){
		Class<?> cls = loadClass(className,false);
		classSet.add(cls);
	}


}
