package com.eshoppingzone.productservice.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshoppingzone.productservice.entity.Product;
import com.eshoppingzone.productservice.exception.ProductNotFoundException;
import com.eshoppingzone.productservice.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	private static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	private static final String NO_PRODUCT_FOUND_MESSAGE = "No Product Found";

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SequenceGeneratorService sequenceGenerator;

	@Override
	public void addProducts(Product product) {
		int productId = sequenceGenerator.generateSequence(Product.SEQUENCE_NAME);
		product.setProductId(productId);
		log.info("Product Added Successfully");
		productRepository.save(product);
	}

	@Override
	public List<Product> getAllProducts() {
		log.info("Retrieved All Products Successfully");
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> getProductById(int productId) {
		log.info("Product Retrieved Successfully");
		return productRepository.findById(productId);
	}

	@Override
	public Optional<Product> getProductByName(String productName) {
		log.info("Product Retrieved Successfully");
		return productRepository.findByProductName(productName);
	}

	@Override
	public Product updateProducts(Product product) {
		Optional<Product> dbProductOptional = productRepository.findById(product.getProductId());
		if (!dbProductOptional.isPresent()) {
			log.error(NO_PRODUCT_FOUND_MESSAGE);
			throw new ProductNotFoundException(NO_PRODUCT_FOUND_MESSAGE);
		}

		Product dbProduct = dbProductOptional.get();

		if (product.getCategory() != null) {
			dbProduct.setCategory(product.getCategory());
		}
		if (product.getDescription() != null) {
			dbProduct.setDescription(product.getDescription());
		}
		if (product.getImage() != null) {
			dbProduct.setImage(product.getImage());
		}
		if (product.getPrice() != 0) {
			dbProduct.setPrice(product.getPrice());
		}
		if (product.getProductName() != null) {
			dbProduct.setProductName(product.getProductName());
		}
		if (product.getProductType() != null) {
			dbProduct.setProductType(product.getProductType());
		}
		if (product.getRating() != null) {
			dbProduct.setRating(product.getRating());
		}
		if (product.getReview() != null) {
			dbProduct.setReview(product.getReview());
		}
		if (product.getSpecification() != null) {
			dbProduct.setSpecification(product.getSpecification());
		}
		log.info("Product Updated Successfully");
		return productRepository.save(dbProduct);

	}

	@Override
	public void deleteProductById(int productId) {
		Optional<Product> dbProduct = productRepository.findById(productId);
		if (!dbProduct.isPresent()) {
			throw new ProductNotFoundException(NO_PRODUCT_FOUND_MESSAGE);
		}
		log.info("Product Deleted Successfully");
		productRepository.deleteById(productId);
	}

	@Override
	public List<Product> searchProducts(String searchString) {
		log.info("Products Retrieved Successfully");
		return productRepository.findByProductNameContainingIgnoreCase(searchString);
	}

	@Override
	public List<Product> getProductByCategory(String category) {
		log.info("Product Retrieved Successfully");
		return productRepository.findByCategory(category);
	}

	@Override
	public List<Product> getProductByType(String productType) {
		log.info("Product Retrieved Successfully");
		return productRepository.findByProductType(productType);
	}

	@Override
	public List<Product> sortByName() {
		log.info("Product Sorted By Name Retrieved Successfully");
		return productRepository.findByOrderByProductName();
	}

	@Override
	public List<Product> sortByPrice() {
		log.info("Product Sorted By Price Retrieved Successfully");
		return productRepository.findByOrderByPrice();
	}

}
