package com.store.app.customer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.app.customer.ErrorMessage;
import com.store.app.customer.entity.Customer;
import com.store.app.customer.entity.Region;
import com.store.app.customer.service.CustomerServiceImp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerServiceImp service;
	
	
	// ------------------------- All customers ----------------------
	
	@GetMapping
	public ResponseEntity<List<Customer>> listAllCustomer(@RequestParam(name = "regionID", required = false) Long regionId)
	{
		
		List<Customer> customers = new ArrayList<>();
		
		if(null == regionId)
		{
			customers = service.findCustomerAll();
		}
		else
		{
			Region region = new Region();
			region.setId(regionId);
			customers = service.findCustomersByRegion(region);
			
			if(null == customers)
			{
				log.error("Customers whit Region id {} not found.", regionId);
				return ResponseEntity.notFound().build();
			}
		}
		
		return ResponseEntity.ok(customers);
	}
	
	
	// ------------------------- Single customer ----------------------
	
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id)
	{
		log.info("Fetching Customer whit id {}", id);
		
		Customer customer = service.getCustomer(id);
		
		if(null == customer)
		{
			log.error("Customers whit id {} not found.", id);
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(customer);
	}
	
	
	// ------------------------- Create customer ----------------------
	
	@PostMapping
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result)
	{
		log.info("Creating Customer {}", customer);
		
		if(result.hasErrors())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
		}
		
		Customer customerDb = service.createCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(customerDb);
	}
	
	
	
	// ------------------------- Update customer ----------------------
	
	@PutMapping("/{id}")
	private ResponseEntity<?> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer)
	{
		log.info("Updating Customer whith id {}", id);
		
		Customer currentCustomer = service.getCustomer(id);
		
		if( null == currentCustomer)
		{
			log.error("Unable to update, Customer whith id {} not found ", id);
			return ResponseEntity.notFound().build();
		}
		
		customer.setId(id);
		currentCustomer = service.updateCustomer(customer);
		return ResponseEntity.ok(currentCustomer);
	}
	
	
	
	// ------------------------- Delete customer ----------------------
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long id)
	{
		log.info("Fetching and Deleting Customer whith id {}", id);
		
		Customer customer = service.getCustomer(id);
		
		if(null == customer)
		{
			log.error("Unable to delete, Customer whith id {} not found ", id);
			return ResponseEntity.notFound().build();
		}
		
		customer = service.deleteCustomer(customer);
		return ResponseEntity.ok(customer);
	}
	
	

	
	private String formatMessage(BindingResult result)
	{
		List<Map<String, String>> errors = result.getFieldErrors().stream().map( err -> {
			Map<String, String> error = new HashMap<>();
			error.put(err.getField(), err.getDefaultMessage());
			return error;
		}).collect(Collectors.toList());
		
		
		ErrorMessage errorMessage = ErrorMessage.builder().code("01").menssages(errors).build();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		
		try
		{
			jsonString = mapper.writeValueAsString(errorMessage);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println(jsonString);
		return jsonString;
	}
}
