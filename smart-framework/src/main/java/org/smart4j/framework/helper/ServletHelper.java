package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet 助手类，封装Request 和Response
 * Created by HP on 2014-04-09.
 */
public class ServletHelper {

    private static final Logger logger = LoggerFactory.getLogger(ServletHelper.class);

    /**
     * 使每个线程独自拥有一份 ServletHelper 实例
     */
    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_THREAD_LOCAL = new ThreadLocal<>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 初始化
     * @param request
     * @param response
     */
    public static void init(HttpServletRequest request, HttpServletResponse response){
        SERVLET_HELPER_THREAD_LOCAL.set(new ServletHelper(request, response));
    }

    /**
     * 销毁
     */
    public static void destory(){
        SERVLET_HELPER_THREAD_LOCAL.remove();
    }

    /**
     * 获取result对象
     * @return
     */
    public static HttpServletRequest getRequest(){
        return SERVLET_HELPER_THREAD_LOCAL.get().request;
    }

    /**
     * 获取 response 对象
     * @return
     */
    public static HttpServletResponse getResponse(){
        return SERVLET_HELPER_THREAD_LOCAL.get().response;
    }

    /**
     * 获取Session对象
     * @return
     */
    private static HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * 获取ServletContext对象
     * @return
     */
    private static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }

    /**
     * 将属性放入Request中
     * @param key
     * @param value
     */
    public static void setRequestAttribute(String key, Object value){
        getRequest().setAttribute(key, value);
    }

    /**
     * 从Request中获取属性
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getRequestAttribute(String key){
        return (T) getRequest().getAttribute(key);
    }

    /**
     * 从Request中移除属性
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static void removeRequestAttribute(String key){
        getRequest().removeAttribute(key);
    }

    /**
     * 发送重定向响应
     * @param location
     */
    public static void sendRedirect(String location){
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        }catch (Exception e){
            logger.error("redirect failure", e);
        }
    }

    /**
     * 将属性放入Session中
     * @param key
     * @param value
     */
    public static void setSessionAttribute(String key, Object value){
        getSession().setAttribute(key, value);
    }

    /**
     * 从Session中获取属性
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttribute(String key){
        return (T) getSession().getAttribute(key);
    }

    /**
     * 从Session 中移除属性
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static void removeSessionAttribute(String key){
        getSession().removeAttribute(key);
    }

    /**
     * 使Session失效
     */
    public static void invalidateSession(){
        getRequest().getSession().invalidate();
    }
}
