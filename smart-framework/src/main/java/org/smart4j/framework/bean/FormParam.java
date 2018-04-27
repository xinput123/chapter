package org.smart4j.framework.bean;

/**
 * 封装表单参数
 * Created by HP on 2014-04-08.
 */
public class FormParam {
    /**
     * 文件表单的字段名
     */
    private String fieldName;

    /**
     * 值
     */
    private Object fieldValue;

    public FormParam(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
