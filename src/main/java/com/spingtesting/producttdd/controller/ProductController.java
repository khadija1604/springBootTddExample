package com.spingtesting.producttdd.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.spingtesting.producttdd.domain.Product;
import com.spingtesting.producttdd.exception.ProductNotFoundException;
import com.spingtesting.producttdd.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
	ResponseEntity<Collection<Product>> getAllProducts(){
		Collection<Product> products= productService.getProducts();
		return new ResponseEntity<Collection<Product>>(products,HttpStatus.OK);
	}
	
    @GetMapping("/{name}")
    ResponseEntity<Product> getProductDetails(@PathVariable String name){
    Product product = productService.getProductDetails(name);
	return new ResponseEntity<Product>(product,HttpStatus.OK);
	
    }
    
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<Product> saveProduct(@RequestBody Product product){
    	Product savedProduct = productService.saveProduct(product);
    	return new ResponseEntity<Product>(savedProduct,HttpStatus.CREATED);
    	
     }
    
    @PutMapping
    ResponseEntity<Product> updateProduct(@RequestBody Product product){
    	Product updated = productService.saveProduct(product);
    	return new ResponseEntity<>(updated,HttpStatus.CREATED);
    }
    
 @ExceptionHandler
 @ResponseStatus(code = HttpStatus.NOT_FOUND)
 private void ProductNotFoundHandler(ProductNotFoundException ex) {
	 
 }

}
