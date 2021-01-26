package com.spingtesting.producttdd.fixture;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;

import com.spingtesting.producttdd.domain.Product;
import com.spingtesting.producttdd.repository.ProductRepository;

public class IntegrationTestFixture {
 
	@Autowired
	ProductRepository productRepository;
	
	@BeforeAll
	void init() {
	  productRepository.save(new Product("Battery", "Electronic"));	
	  productRepository.save(new Product("Book", "Culture"));	
	}
}
