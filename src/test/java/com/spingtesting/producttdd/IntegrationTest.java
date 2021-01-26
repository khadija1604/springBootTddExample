package com.spingtesting.producttdd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.spingtesting.producttdd.domain.Product;
import com.spingtesting.producttdd.fixture.IntegrationTestFixture;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest extends IntegrationTestFixture{
	
	@LocalServerPort
	int port;

	
	@Autowired
	private RestTemplate restTemplate;
	
    HttpHeaders headers = new HttpHeaders();
    
    private HttpEntity entity;
    
    @BeforeEach
    void init() {
	   entity = new HttpEntity<>(headers);
    }
    
	@Test 
	void saveProduct() throws Exception{
		Product product = new Product("robot", "khadija"); 
		ResponseEntity<Product> response= restTemplate.postForEntity("http://localhost:" +port+"/products",
				product, Product.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	
	@Test
	void getProductDetails() throws Exception {
	     ResponseEntity<Product> response =restTemplate.exchange("http://localhost:" +port+"/products/robot",
	    		 HttpMethod.GET,entity, Product.class);
	     assertEquals(HttpStatus.OK, response.getStatusCode());
	     assertEquals("robot", response.getBody().getName());
	     assertEquals("khadija", response.getBody().getType());
	}
	
	@Test
	void getAllProducts() {
		 ResponseEntity<List<Product>> response =restTemplate.
				 exchange("http://localhost:" +port+"/products",
						 HttpMethod.GET,
						 entity, 
						 new ParameterizedTypeReference<List<Product>>() {
		});
		 List<Product> products= response.getBody();
		 assertThat(products.size() == 2);
		 assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void updateProduct() {
		Product product = new Product("Book", "Science"); 
		entity= new HttpEntity<>(product, headers);
		ResponseEntity<Product> response= restTemplate.exchange("http://localhost:" +port+"/products",
				                                                HttpMethod.PUT, 
				                                                entity, 
				                                                Product.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	@Disabled
	void getProductDetails_notFound() throws Exception {
	     ResponseEntity<Product> response =restTemplate.exchange("http://localhost:" +port+"/products/test",HttpMethod.GET,entity, Product.class);
	     assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());     
	}
	

}
