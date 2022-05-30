package com.store.app.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
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
import com.store.app.product.entity.Category;
import com.store.app.product.entity.Product;
import com.store.app.product.service.ProductServiceImp;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductServiceImp service;
	
	@GetMapping
	public ResponseEntity<List<Product>> listProduct(@RequestParam( name = "categoryId", required = false) Long categoryID)
	{
		List<Product> products = new ArrayList<>();
		
		if(null == categoryID)
		{
			products = service.listAllProduct();
			
			if(products.isEmpty())
			{
				return ResponseEntity.noContent().build();
			}
		}
		else
		{
			products = service.findByCategory(Category.builder().id(categoryID).build());
			
			if(products.isEmpty())
			{
				return ResponseEntity.notFound().build();
			}
		}
		
	
		return ResponseEntity.ok(products);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable("id") Long id)
	{
		Product product = service.getProduct(id);
		
		if(null == product)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok( product );
	}
	
	
	@PostMapping()
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result)
	{
		if(result.hasErrors())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
		}
		
		Product productCreate = service.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product)
	{
		product.setId(id);
		Product productDB = service.updateProduct(product);
		
		if( productDB == null)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(productDB);
	}
	
	
	
	@PutMapping("/{id}/stock")
	public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id, @RequestParam(name = "quantity", required = true) Double quantity)
	{
		Product productDB = service.updateStock(id, quantity);
		
		if( productDB == null)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(productDB);
	}
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Product> deleteProducto(@PathVariable("id") Long id)
	{
		
		Product productDelete = service.deleteProduct(id);
		
		if(productDelete == null)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok( productDelete);
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
	
		return jsonString;
	}
	
}
