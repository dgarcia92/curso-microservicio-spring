package com.store.app.product.service;

import java.util.List;

import com.store.app.product.entity.Category;
import com.store.app.product.entity.Product;

public interface IProductService {

	public List<Product> listAllProduct();
	public Product getProduct( Long id);
	
	public Product createProduct( Product product);
	public Product updateProduct( Product product);
	public Product deleteProduct( Long id);
	public List<Product> findByCategory( Category category);
	public Product updateStock(Long id, Double quantity);
	
}
