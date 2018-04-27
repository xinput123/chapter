package org.smart4j.chapter2;

import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuanlai
 * @date 2014-03-27
 */
public class CustomerServletTest {

	private final CustomerService customerService;

	public CustomerServletTest(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Before
	public void init(){
		// TODO 初始化数据库

	}

	@Test
	public void getCustomerListTest() throws Exception{
		CustomerService a = new CustomerService();
		List<Customer> customers = a.listCustomer();
		Assert.assertEquals(2,customers.size());
	}

	@Test
	public void getCustomerTest() throws Exception{
		long id = 1;
		Customer customer = customerService.getCustomer(id);
		Assert.assertNotNull(customer);
	}

	@Test
	public void updateCustomerTest() throws Exception{
		long id = 1;
		Map<String,Object> map = new HashMap<>();
		map.put("contact","Eric");
		boolean result = customerService.updateCustomer(id,map);
		Assert.assertTrue(result);
	}

	@Test
	public void deleteCustomerTest() throws Exception{
		long id = 1;
		boolean result = customerService.deleteCustomer(id);
		Assert.assertTrue(result);
	}


}
