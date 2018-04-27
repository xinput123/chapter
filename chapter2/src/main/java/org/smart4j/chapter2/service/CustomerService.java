package org.smart4j.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DatabaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.PropsUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 提供客户数据服务
 * @author yuanlai
 * @date 2014-03-27
 */
public class CustomerService {

	private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);

	/**
	 * 获取客户列表
	 * @return
	 */
	public List<Customer> listCustomer(){
		String sql = "select * from customer";
		return DatabaseHelper.queryEntityList(Customer.class,sql);
	}

	/**
	 * 获取客户
	 * @param id
	 * @return
	 */
	public Customer getCustomer(long id){
		// TODO
		return null;
	}

	/**
	 * 创建客户
	 * @param map
	 * @return
	 */
	public boolean createCustomer(Map<String,Object> map){
        return DatabaseHelper.insertEntity(Customer.class,map);
	}

	/**
	 * 更新客户
	 * @param map
	 * @return
	 */
	public boolean updateCustomer(long id,Map<String,Object> map){
		return DatabaseHelper.updateEntity(Customer.class, id, map);
	}

	/**
	 * 删除客户
	 * @param id
	 * @return
	 */
	public boolean deleteCustomer(long id){
		return DatabaseHelper.deleteEntity(Customer.class, id);
	}
}
