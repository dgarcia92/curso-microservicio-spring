package com.store.app.product;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.store.app.product.entity.Category;
import com.store.app.product.entity.Product;
import com.store.app.product.repository.ProductRepository;


@DataJpaTest
public class ProductRepositoryMockTest {

	@Autowired
	private ProductRepository repository;
	
	@Test
	public void whenFindByCategory_thenReturnListProduct()
	{
		
		Product prod01 = Product.builder()
				.name("computer")
				.category( Category.builder().id(1L).build())
				.description("")
				.stock( Double.parseDouble("10") )
				.price(  Double.parseDouble("100"))
				.status("Created")
				.createdAt( new Date()).build();
		
		
		repository.save( prod01 );
		
		
		List<Product> founds = repository.findByCategory( prod01.getCategory() );
		
		Assertions.assertThat( founds.size()).isEqualTo(2);
		
		
	}
}
