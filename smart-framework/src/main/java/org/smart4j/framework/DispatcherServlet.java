package org.smart4j.framework;

import com.alibaba.fastjson.JSON;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.*;
import org.smart4j.framework.util.ArrayUtils;
import org.smart4j.framework.util.CodecUtils;
import org.smart4j.framework.util.ReflectionUtils;
import org.smart4j.framework.util.StreamUtils;
import org.smart4j.framework.util.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 * @author yuanlai
 * @date 2014-04-05
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		// 初始化相关Helper类
		HelperLoader.init();

		// 获取ServletContext对象(用于注册Servlet)
		ServletContext servletContext = config.getServletContext();

		// 注册处理JSP的Servlet
		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
		jspServlet.addMapping(ConfigHelper.getAppJspPath()+"*");

		// 祖册处理静态资源的默认Servlet
		ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
		defaultServlet.addMapping(ConfigHelper.getAppAssetPath());

		UploadHelper.init(servletContext);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取请求方法和请求路径
		String requestMethod = request.getMethod().toLowerCase();
		String requestPath = request.getPathInfo();

		if("/favicon.ico".equals(requestPath)){
			return;
		}

		// 获取 Action 处理器
		Handler handler = ControllerHelper.getHandler(requestMethod,requestPath);
		if(null==handler){
			return;
		}

		// 获取Controller类及其Bean实例
		Class<?> controllerClass = handler.getControllerClass();
		Object controllerBean = BeanHelper.getBean(controllerClass);

		Param param =
				UploadHelper.isMultipart(request) ? UploadHelper.createParam(request) : RequestHelper.createParam(request);

		Object result;
		Method actionMethod = handler.getActionMethod();
		if(param.isEmpty()){
			result = ReflectionUtils.invokeMethod(controllerBean,actionMethod);
		}else {
		    result = ReflectionUtils.invokeMethod(controllerBean,actionMethod,param);
		}

		if(request instanceof View){
			handleViewResult((View)result, request,response);
		}else {
			handleDataResult((Data)result,response);
		}
	}

	private void handleViewResult(View view, HttpServletRequest request,
								  HttpServletResponse response) throws IOException,ServletException{
        String path = view.getPath();
		if(StringUtils.isNotEmpty(path)){
			if(path.startsWith("/")){
				response.sendRedirect(request.getContextPath() + path);
			}else {
				Map<String,Object> model = view.getModel();
				for(Map.Entry<String,Object> entry : model.entrySet()){
					request.setAttribute(entry.getKey(),entry.getValue());
				}
				request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request,response);
			}
		}
	}

	private void handleDataResult(Data data, HttpServletResponse response) throws IOException{
		Object model = data.getModel();
		if(null!=model){
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			String json = JSON.toJSONString(model);
			writer.write(json);
			writer.flush();
			writer.close();
		}
	}


}
