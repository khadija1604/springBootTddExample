package com.spingtesting.producttdd.cache;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.spingtesting.producttdd.domain.Product;
import com.spingtesting.producttdd.repository.ProductRepository;
import com.spingtesting.producttdd.service.ProductService;

@SpringBootTest
public class CacheTest {
    
	@Autowired 
	private ProductService productService;
	
	@MockBean
	private ProductRepository productRepository;
	
	@Test
	void getProductDetails_cache() {
		when(productRepository.findByName("robot")).thenReturn(Optional.of(new Product("robot", "robota")));
		Product product=productService.getProductDetails("robot");
		assertAll(()->product.getName().equals("robot"),()->product.getType().equals("robota"));
		productService.getProductDetails("robot");
		verify(productRepository, times(1)).findByName("robot");
	}
}
