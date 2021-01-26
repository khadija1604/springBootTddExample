package com.spingtesting.producttdd.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.spingtesting.producttdd.domain.Product;
import com.spingtesting.producttdd.exception.ProductNotFoundException;
import com.spingtesting.producttdd.repository.ProductRepository;

public class ProductServiceTest {
	
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductService productService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void getProductDetails_returns_productDetails() {
		when(productRepository.findByName("robot")).thenReturn(Optional.of(new Product("robot", "AI")));
		Product product=productService.getProductDetails("robot");
		verify(productRepository).findByName("robot");
		assertNotNull(product);
		assertEquals("robot", product.getName());
		assertEquals("AI", product.getType());
		
	}
	
	@Test 
	  void  saveProductOrUpdate_will_return_saved_product(){
		Product product= new Product("robot", "AI");
		when(productRepository.save(product)).thenReturn(product);
		Product savedProduct= productService.saveProduct(product);
		verify(productRepository).save(product);
		assertNotNull(savedProduct);
		assertEquals("robot", savedProduct.getName());
		assertEquals("AI", savedProduct.getType());
		
	   }
    
	@Test
	void getProductDetails_ThrowsProductNotFoundException() {
        when(productRepository.findByName("robot")).thenThrow(new ProductNotFoundException());
		assertThrows(ProductNotFoundException.class, ()-> productService.getProductDetails("robot"));
		
	}
	
	@Test
	void getAllProducts_should_return_products_list(){
		when(productRepository.findAll()).thenReturn(Arrays.asList(new Product("robot", "AI")));
		Collection<Product> actualProducts= productService.getProducts();
		verify(productRepository).findAll();
		assertEquals(1, actualProducts.size());
	}
}
