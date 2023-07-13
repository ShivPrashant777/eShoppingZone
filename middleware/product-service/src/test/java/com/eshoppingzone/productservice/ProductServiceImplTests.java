package com.eshoppingzone.productservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.eshoppingzone.productservice.entity.Product;
import com.eshoppingzone.productservice.exception.ProductNotFoundException;
import com.eshoppingzone.productservice.repository.ProductRepository;
import com.eshoppingzone.productservice.service.ProductServiceImpl;
import com.eshoppingzone.productservice.service.SequenceGeneratorService;

@SpringBootTest
class ProductServiceImplTests {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private SequenceGeneratorService sequenceGenerator;

	@InjectMocks
	private ProductServiceImpl productService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllProductsValidList() {
		Product product1 = new Product(1, "Electronics", "Smartphone", "MobilePhones", new HashMap<Integer, Double>(),
				new HashMap<Integer, String>(), "http://image.com", 399.99,
				"A powerful smartphone with an advanced camera and long-lasting battery.",
				new HashMap<String, String>());
		Product product2 = new Product(2, "Electronics2", "Smartphone2", "MobilePhones2",
				new HashMap<Integer, Double>(), new HashMap<Integer, String>(), "http://image.com", 399.99,
				"A powerful smartphone with an advanced camera and long-lasting battery.",
				new HashMap<String, String>());

		List<Product> mockProducts = Arrays.asList(product1, product2);
		when(productRepository.findAll()).thenReturn(mockProducts);

		List<Product> products = productService.getAllProducts();

		assertEquals(2, products.size());
		assertEquals(product1, products.get(0));
		assertEquals(product2, products.get(1));
	}

	@Test
    void testGetAllProductsWhenNoProductsFound() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        List<Product> products = productService.getAllProducts();

        assertEquals(0, products.size());
    }

	@Test
	void testAddProducts() {
		Product product = new Product();
		product.setProductName("Test Product");

		int generatedId = 1;
		when(sequenceGenerator.generateSequence(Product.SEQUENCE_NAME)).thenReturn(generatedId);

		productService.addProducts(product);

		assertEquals(generatedId, product.getProductId());
		verify(productRepository, times(1)).save(product);
	}

	@Test
	void testGetProductByIdValidId() {
		int productId = 1;
		Product product = new Product();
		product.setProductId(productId);
		when(productRepository.findById(productId)).thenReturn(Optional.of(product));

		Optional<Product> result = productService.getProductById(productId);

		assertEquals(Optional.of(product), result);
	}

	@Test
	void testGetProductByNameValidName() {
		String productName = "Test Product";
		Product product = new Product();
		product.setProductName(productName);
		when(productRepository.findByProductName(productName)).thenReturn(Optional.of(product));

		Optional<Product> result = productService.getProductByName(productName);

		assertEquals(Optional.of(product), result);
	}

	@Test
	void testGetProductByIdInvalidId() {
		int productId = 1;
		when(productRepository.findById(productId)).thenReturn(Optional.empty());

		Optional<Product> result = productService.getProductById(productId);

		assertEquals(Optional.empty(), result);
	}

	@Test
	void testGetProductByNameInvalidName() {
		String productName = "Nonexistent Product";
		when(productRepository.findByProductName(productName)).thenReturn(Optional.empty());

		Optional<Product> result = productService.getProductByName(productName);

		assertEquals(Optional.empty(), result);
	}

	@Test
	void testUpdateProductsValidProduct() {
		int productId = 1;
		String productName = "Test Product";
		String newCategory = "New Category";
		String newDescription = "New Description";
		String newImage = "New Image";
		double newPrice = 10.99;
		String newProductType = "New Type";
		Map<Integer, Double> newRating = new HashMap<Integer, Double>();
		Map<Integer, String> newReview = new HashMap<Integer, String>();
		Map<String, String> newSpecification = new HashMap<String, String>();

		Product existingProduct = new Product();
		existingProduct.setProductId(productId);
		existingProduct.setProductName(productName);

		Product updatedProduct = new Product();
		updatedProduct.setProductId(productId);
		updatedProduct.setCategory(newCategory);
		updatedProduct.setDescription(newDescription);
		updatedProduct.setImage(newImage);
		updatedProduct.setPrice(newPrice);
		updatedProduct.setProductName(productName);
		updatedProduct.setProductType(newProductType);
		updatedProduct.setRating(newRating);
		updatedProduct.setReview(newReview);
		updatedProduct.setSpecification(newSpecification);

		when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
		when(productRepository.save(existingProduct)).thenReturn(existingProduct);

		productService.updateProducts(updatedProduct);

		assertEquals(newCategory, existingProduct.getCategory());
		assertEquals(newDescription, existingProduct.getDescription());
		assertEquals(newImage, existingProduct.getImage());
		assertEquals(newPrice, existingProduct.getPrice());
		assertEquals(productName, existingProduct.getProductName());
		assertEquals(newProductType, existingProduct.getProductType());
		assertEquals(newRating, existingProduct.getRating());
		assertEquals(newReview, existingProduct.getReview());
		assertEquals(newSpecification, existingProduct.getSpecification());
	}

	@Test
	void testUpdateProductsInvalidProduct() {
		int productId = 1;
		Product nonExistingProduct = new Product();
		nonExistingProduct.setProductId(productId);
		nonExistingProduct.setProductName("Nonexistent Product");

		when(productRepository.findById(productId)).thenReturn(Optional.empty());

		assertThrows(ProductNotFoundException.class, () -> productService.updateProducts(nonExistingProduct));
	}

	@Test
	void testDeleteProductByIdValidProduct() {
		int productId = 1;
		Product existingProduct = new Product();
		existingProduct.setProductId(productId);
		existingProduct.setProductName("Existing Product");

		when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

		productService.deleteProductById(productId);

		verify(productRepository, times(1)).deleteById(productId);
	}

	@Test
	void testDeleteProductByIdInvalidProduct() {
		int productId = 1;

		when(productRepository.findById(productId)).thenReturn(Optional.empty());

		assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(productId));
	}

	@Test
	void testGetProductByCategoryValidList() {
		String category = "Test Category";
		List<Product> mockProducts = new ArrayList<>();
		Product product1 = new Product(1, "Electronics", "Smartphone", "MobilePhones", new HashMap<Integer, Double>(),
				new HashMap<Integer, String>(), "http://image.com", 399.99,
				"A powerful smartphone with an advanced camera and long-lasting battery.",
				new HashMap<String, String>());
		Product product2 = new Product(2, "Electronics2", "Smartphone2", "MobilePhones2",
				new HashMap<Integer, Double>(), new HashMap<Integer, String>(), "http://image.com", 399.99,
				"A powerful smartphone with an advanced camera and long-lasting battery.",
				new HashMap<String, String>());
		mockProducts.add(product1);
		mockProducts.add(product2);
		when(productRepository.findByCategory(category)).thenReturn(mockProducts);

		List<Product> products = productService.getProductByCategory(category);

		assertEquals(mockProducts, products);
	}

	@Test
	void testGetProductByTypeValidList() {
		String productType = "Test Type";
		List<Product> mockProducts = new ArrayList<>();
		Product product1 = new Product(1, "Electronics", "Smartphone", "MobilePhones", new HashMap<Integer, Double>(),
				new HashMap<Integer, String>(), "http://image.com", 399.99,
				"A powerful smartphone with an advanced camera and long-lasting battery.",
				new HashMap<String, String>());
		Product product2 = new Product(2, "Electronics2", "Smartphone2", "MobilePhones2",
				new HashMap<Integer, Double>(), new HashMap<Integer, String>(), "http://image.com", 399.99,
				"A powerful smartphone with an advanced camera and long-lasting battery.",
				new HashMap<String, String>());
		mockProducts.add(product1);
		mockProducts.add(product2);
		when(productRepository.findByProductType(productType)).thenReturn(mockProducts);

		List<Product> products = productService.getProductByType(productType);

		assertEquals(mockProducts, products);
	}
}
