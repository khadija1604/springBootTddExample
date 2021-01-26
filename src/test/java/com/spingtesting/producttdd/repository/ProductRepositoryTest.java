package com.spingtesting.producttdd.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.spingtesting.producttdd.domain.Product;

@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@Test
	void findByName() {
		productRepository.save(new Product("robot", "digi"));
		Optional<Product> productopt=productRepository.findByName("robot");
		assertThat(productopt.isPresent()).isTrue();
		Product product= productopt.get();
		assertEquals(1, product.getId());
		assertEquals("robot", product.getName());
		assertEquals("digi", product.getType());
	}
	
	@Test
	void findByName_notFound() {
		Optional<Product> productopt=productRepository.findByName("robot");
		assertThat(productopt.isPresent()).isFalse();
	}

}
