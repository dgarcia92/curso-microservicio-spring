package com.store.app.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.store.app.customer.entity.Customer;
import com.store.app.customer.entity.Region;

public interface CustomerRepository  extends JpaRepository<Customer, Long>{

	
	public Customer findByNumberID(String numberID);
	public List<Customer> findByLastName(String lastName);
	public List<Customer> findByRegion(Region region);
	
}
