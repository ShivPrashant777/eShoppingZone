package com.eshoppingzone.productservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eshoppingzone.productservice.entity.Product;

public interface ProductRepository extends MongoRepository<Product, Integer> {
	Optional<Product> findByProductName(String productName);

	List<Product> findByProductNameContainingIgnoreCase(String searchString);

	List<Product> findByCategory(String category);

	List<Product> findByProductType(String productType);

	List<Product> findByOrderByProductName();

	List<Product> findByOrderByPrice();
}
