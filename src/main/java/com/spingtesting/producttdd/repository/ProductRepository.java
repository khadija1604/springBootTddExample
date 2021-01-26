package com.spingtesting.producttdd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spingtesting.producttdd.domain.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {

	Optional<Product> findByName(String string);

}
