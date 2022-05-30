package com.store.app.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.app.product.entity.Category;
import com.store.app.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	public List<Product> findByCategory(Category category);
}
