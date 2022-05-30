package com.store.app.customer.service;

import java.util.List;

import com.store.app.customer.entity.Customer;
import com.store.app.customer.entity.Region;

public interface ICustomerService {

	public List<Customer> findCustomerAll();
	public List<Customer> findCustomersByRegion(Region region);
	
	public Customer createCustomer(Customer customer);
	public Customer updateCustomer(Customer customer);
	public Customer deleteCustomer(Customer customer);
	public Customer getCustomer(Long id);
}
