package com.store.app.product;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.store.app.product.repository.ProductRepository;
import com.store.app.product.service.ProductServiceImp;

@SpringBootTest
public class ProductServicesMockTest {

	@Mock
	private ProductRepository repository;
	
	private ProductServiceImp service;
	
	@BeforeEach
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
	}
	
	
}
