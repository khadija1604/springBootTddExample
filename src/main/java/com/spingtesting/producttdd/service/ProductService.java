package com.spingtesting.producttdd.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.spingtesting.producttdd.domain.Product;
import com.spingtesting.producttdd.exception.ProductNotFoundException;
import com.spingtesting.producttdd.repository.ProductRepository;

@Service
public class ProductService {
    
	@Autowired
	private ProductRepository productRepository;
	
	@Cacheable("products")
	public Product getProductDetails(String name) {
		Product product= null;
		Optional<Product> optionalPruduct= productRepository.findByName(name);
		if(optionalPruduct.isPresent()) {
			product= optionalPruduct.get();
		}else {
			throw new ProductNotFoundException();
		}
		return product;
	}

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}
 
	public Collection<Product> getProducts() {
		return productRepository.findAll();
	}

}
