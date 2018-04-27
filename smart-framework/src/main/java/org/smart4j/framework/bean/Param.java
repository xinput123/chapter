package org.smart4j.framework.bean;

import org.smart4j.framework.util.CastUtils;
import org.smart4j.framework.util.CollectionUtils;
import org.smart4j.framework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数对象
 * @author yuanlai
 * @date 2014-04-05
 */
public class Param {
	/**
	 * 表单参数
	 */
	private List<FormParam> formParamList;

	/**
	 * 文件参数
	 */
	private List<FileParam> fileParamList;

	public Param(List<FormParam> formParamList) {
		this.formParamList = formParamList;
	}

	public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
		this.formParamList = formParamList;
		this.fileParamList = fileParamList;
	}

	/**
	 * 获取请求参数映射
	 * @return
     */
	public Map<String,Object> getFieldMap(){
        Map<String,Object> fieldMap = new HashMap<>();
		if(CollectionUtils.isNotEmpty(formParamList)){
			for(FormParam formParam : formParamList){
				String fieldName = formParam.getFieldName();
				Object fieldValue = formParam.getFieldValue();
				if(fieldMap.containsKey(fieldName)){
					fieldValue = fieldMap.get(fieldMap) + StringUtils.SEPARATOR + fieldValue;
				}
				fieldMap.put(fieldName,fieldValue);
			}
		}

		return fieldMap;
	}

	/**
	 * 获取上传文件映射
	 * @return
     */
	public Map<String,List<FileParam>> getFileMap(){
		Map<String,List<FileParam>> fileMap = new HashMap<>();
		if(CollectionUtils.isNotEmpty(fileParamList)){
			for(FileParam fileParam : fileParamList){
				String fieldName = fileParam.getFieldName();
				List<FileParam> fileParamList =
						fileMap.containsKey(fieldName) ? fileMap.get(fieldName) : new ArrayList<>();
                fileParamList.add(fileParam);
				fileMap.put(fieldName,fileParamList);
			}
		}
        return fileMap;
	}

	/**
	 * 验证参数是否为空
	 * @return
     */
	public boolean isEmpty(){
		return CollectionUtils.isEmpty(formParamList) && CollectionUtils.isEmpty(fileParamList);
	}

	/**
	 * 获取所有上传文件
	 * @param fieldName
	 * @return
     */
	public List<FileParam> getFileList(String fieldName){
		return getFileMap().get(fieldName);
	}

	/**
	 * 获取唯一上传文件
	 * @param fieldName
	 * @return
     */
	public FileParam getFile(String fieldName){
		List<FileParam> fileParamList = getFileList(fieldName);
		if(CollectionUtils.isNotEmpty(fileParamList) && fileParamList.size()==1 ){
			return fileParamList.get(0);
		}

		return null;
	}

	/**
	 * 获取参数名获取 String 型参数值
	 * @param name
	 * @return
     */
	public String getString(String name){
		return CastUtils.castString(getFieldMap().get(name));
	}

	/**
	 * 获取参数名获取 double 型参数值
	 * @param name
	 * @return
	 */
	public double getDouble(String name){
		return CastUtils.castDouble(getFieldMap().get(name));
	}

	/**
	 * 获取参数名获取long型参数值
	 * @param name
	 * @return
	 */
	public long getLong(String name){
		return CastUtils.castLong(getFieldMap().get(name));
	}

	/**
	 * 获取参数名获取 int 型参数值
	 * @param name
	 * @return
	 */
	public int getInt(String name){
		return CastUtils.castInt(getFieldMap().get(name));
	}

	/**
	 * 获取参数名获取 boolean 型参数值
	 * @param name
	 * @return
	 */
	public boolean getBoolean(String name){
		return CastUtils.castBoolean(getFieldMap().get(name));
	}

}
