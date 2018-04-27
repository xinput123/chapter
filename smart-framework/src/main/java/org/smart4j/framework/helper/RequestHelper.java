package org.smart4j.framework.helper;

import org.smart4j.framework.bean.FormParam;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.util.ArrayUtils;
import org.smart4j.framework.util.CodecUtils;
import org.smart4j.framework.util.StreamUtils;
import org.smart4j.framework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 请求助手类
 * Created by HP on 2014-04-09.
 */
public class RequestHelper {

    /**
     * 创建请求对象
     * @param request
     * @return
     * @throws IOException
     */
    public static Param createParam(HttpServletRequest request)throws IOException{
        List<FormParam> formParamList = new ArrayList<>();
        formParamList.addAll(parseParameterNames(request));
        formParamList.addAll(parseInputStream(request));

        return new Param(formParamList);
    }

    private static List<FormParam> parseParameterNames(HttpServletRequest request){
        List<FormParam> formParams = new ArrayList<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()){
            String fieldName = paramNames.nextElement();
            String[] fieldValues = request.getParameterValues(fieldName);
            if(ArrayUtils.isNotEmpty(fieldValues)){
                Object fieldValue;
                if(fieldValues.length == 1){
                    fieldValue = fieldValues[0];
                }else {
                    StringBuilder sb = new StringBuilder("");
                    for(int i=0; i<fieldValues.length; i++){
                        sb.append(fieldValues[i]);
                        if(i != fieldValues.length - 1 ){
                            sb.append(StringUtils.SEPARATOR);
                        }
                    }
                    fieldValue = sb.toString();
                }
                formParams.add(new FormParam(fieldName,fieldValue));
            }
        }
        return formParams;
    }

    private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException{
        List<FormParam> formParams = new ArrayList<>();
        String body = CodecUtils.decodeURL(StreamUtils.getString(request.getInputStream()));
        if(StringUtils.isNotEmpty(body)){
            String[] kvs = StringUtils.splitString(body,"&");
            if(ArrayUtils.isNotEmpty(kvs)){
                for(String kv : kvs){
                    String[] array = StringUtils.splitString(kv,"=");
                    if(ArrayUtils.isNotEmpty(array) && array.length==2){
                        String fieldName = array[0];
                        String fieldValue = array[1];
                        formParams.add(new FormParam(fieldName,fieldValue));
                    }
                }
            }
        }
        return formParams;
    }
}
