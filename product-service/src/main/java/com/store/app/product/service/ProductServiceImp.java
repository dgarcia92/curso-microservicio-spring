package com.store.app.product.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.product.entity.Category;
import com.store.app.product.entity.Product;
import com.store.app.product.repository.ProductRepository;

@Service
public class ProductServiceImp implements IProductService{

	
	@Autowired
	private ProductRepository productRepository;
	
	
	@Override
	public List<Product> listAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public Product getProduct(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public Product createProduct(Product product) {
		product.setStatus("CREATED");
		product.setCreatedAt(new Date());
		
		return productRepository.save(product);
		
	}

	@Override
	public Product updateProduct(Product product) {
		
		Product productDB = getProduct( product.getId());
		
		if( null == productDB)
		{
			return null;
		}
		
		productDB.setName( product.getName());
		productDB.setDescription( product.getDescription());
		productDB.setCategory(product.getCategory());
		productDB.setPrice(product.getPrice());
		
		return productRepository.save(productDB);
		
	}

	@Override
	public Product deleteProduct(Long id) {
		Product productDB = getProduct(id);
		
		if( null == productDB)
		{
			return null;
		}
		
		productDB.setStatus("DELETED");
		return productRepository.save(productDB);
	}

	
	@Override
	public List<Product> findByCategory(Category category) {
		return productRepository.findByCategory(category);
	}

	@Override
	public Product updateStock(Long id, Double quantity) {
		Product productDB = getProduct( id);
		
		if( null == productDB)
		{
			return null;
		}
		
		productDB.setStock( productDB.getStock() + quantity );
		return productRepository.save(productDB);
		
	}

	
	
}
