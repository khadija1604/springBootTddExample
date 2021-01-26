package com.spingtesting.producttdd.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spingtesting.producttdd.domain.Product;
import com.spingtesting.producttdd.exception.ProductNotFoundException;
import com.spingtesting.producttdd.service.ProductService;



@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	 ProductService productService;
	
	
	@Test
	void get_ProductDetails() throws Exception {
		when(productService.getProductDetails(Mockito.anyString())).thenReturn(new Product("robot","AI"));
		mockMvc.perform(get("/products/robot")).
		andExpect(status().isOk()).
		andExpect(jsonPath("$").isMap()).
		andExpect(jsonPath("name").value("robot")).
		andExpect(jsonPath("type").value("AI"));
	}
	
	@Test
	void getProductDetails_notFound() throws Exception {
		 when(productService.getProductDetails(Mockito.anyString())).thenThrow(new ProductNotFoundException());
		 mockMvc.perform(get("/products/robot")).
		andExpect(status().isNotFound());     
	}
	
	@Test
	void saveProduct() throws Exception{
		Product product = new Product("robot","AI");
		when(productService.saveProduct(Mockito.any(Product.class))).thenReturn(product);
		mockMvc.perform(post("/products").content(asJsonString(product)).contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON))
		                                      .andExpect(status().isCreated())
		                                      .andExpect(jsonPath("name").value("robot"))
		                                      .andExpect(jsonPath("type").value("AI"));
		                                       
	}
	
	@Test
	void updateProduct() throws Exception{
		Product product = new Product("robot","Philosphy");
		product.setId(new Long(1003));
        given(productService.saveProduct(Mockito.any())).willReturn(product);
		mockMvc.perform(put("/products").
				content(asJsonString(new Product("robot","Philosphy"))).
				contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON)).
		        andExpect(status().isCreated())
		       .andExpect(jsonPath("$").isMap()).andDo(print());
		}
	
	@Test
	void getAllProducts() throws Exception{
		when(productService.getProducts()).
		thenReturn(Arrays.asList(new Product("Battery", "Electronic"),new Product("Book", "Culture")));
		mockMvc.perform(get("/products")).
		andExpect(status().isOk()).
		andExpect(jsonPath("$").isArray()).andDo(print());
		
	}
	
	@Test
	void BadRequest() throws Exception {
		Product product = new Product("robot","AI");
		when(productService.saveProduct(Mockito.any(Product.class))).thenReturn(product);
		mockMvc.perform(post("/products").content(asJsonString(new String("product"))).contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isBadRequest());
	}
	
	  public static String asJsonString(final Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }

}
