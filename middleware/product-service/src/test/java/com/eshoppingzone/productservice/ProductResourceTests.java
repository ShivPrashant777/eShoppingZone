package com.eshoppingzone.productservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.eshoppingzone.productservice.entity.Product;
import com.eshoppingzone.productservice.exception.ProductNotFoundException;
import com.eshoppingzone.productservice.exception.UnauthorizedException;
import com.eshoppingzone.productservice.proxy.ProfileServiceProxy;
import com.eshoppingzone.productservice.resource.ProductResource;
import com.eshoppingzone.productservice.service.ProductService;

@SpringBootTest
public class ProductResourceTests {

	@Mock
	private ProductService productService;

	@Mock
	private ProfileServiceProxy profileServiceProxy;

	@InjectMocks
	private ProductResource productResource;

	@Test
	void testGetAllProducts() {
		List<Product> mockProducts = new ArrayList<>();
		Product tempProduct = new Product();
		tempProduct.setProductId(1);
		tempProduct.setProductName("Phone");
		mockProducts.add(tempProduct);
		when(productService.getAllProducts()).thenReturn(mockProducts);

		ResponseEntity<List<Product>> response = productResource.getAllProducts();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockProducts, response.getBody());
	}

	@Test
	void testAddProductsValidToken() {
		String token = "validToken";
		Product product = new Product();

		when(profileServiceProxy.validateUserRole(token, "Merchant")).thenReturn(true);

		productResource.addProducts(token, product);

		verify(productService, times(1)).addProducts(product);
	}

	@Test
	void testAddProductsInvalidToken() {
		String token = "invalidToken";
		Product product = new Product();

		when(profileServiceProxy.validateUserRole(token, "Merchant")).thenReturn(false);

		assertThrows(UnauthorizedException.class, () -> productResource.addProducts(token, product));
	}

	@Test
	void testGetProductById() {
		int productId = 1;
		Product mockProduct = new Product();

		when(productService.getProductById(productId)).thenReturn(Optional.of(mockProduct));

		ResponseEntity<Product> response = productResource.getProductById(productId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockProduct, response.getBody());
	}

	@Test
	void testGetProductByIdInvalidProduct() {
		int nonExistingProductId = 999;

		when(productService.getProductById(nonExistingProductId)).thenReturn(Optional.empty());

		assertThrows(ProductNotFoundException.class, () -> productResource.getProductById(nonExistingProductId));
	}

	@Test
	void searchProductsValidList() {
		String searchString = "keyword";
		List<Product> mockProductList = new ArrayList<>();

		when(productService.searchProducts(searchString)).thenReturn(mockProductList);

		ResponseEntity<List<Product>> response = productResource.searchProducts(searchString);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockProductList, response.getBody());
	}

	@Test
	void getProductByTypeValidList() {
		String productType = "type";
		List<Product> mockProductList = new ArrayList<>();

		when(productService.getProductByType(productType)).thenReturn(mockProductList);

		ResponseEntity<List<Product>> response = productResource.getProductByType(productType);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockProductList, response.getBody());
	}

	@Test
	void getProductByNameValidProduct() {
		String productName = "name";
		Product mockProduct = new Product();

		when(productService.getProductByName(productName)).thenReturn(Optional.of(mockProduct));

		ResponseEntity<Product> response = productResource.getProductByName(productName);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockProduct, response.getBody());
	}

	@Test
	void getProductByNameInvalidProduct() {
		String nonExistingProductName = "nonExistingName";

		when(productService.getProductByName(nonExistingProductName)).thenReturn(Optional.empty());

		assertThrows(ProductNotFoundException.class, () -> productResource.getProductByName(nonExistingProductName));
	}

	@Test
	void getProductByCategoryValidList() {
		String category = "category";
		List<Product> mockProductList = new ArrayList<>();

		when(productService.getProductByCategory(category)).thenReturn(mockProductList);

		ResponseEntity<List<Product>> response = productResource.getProductByCategory(category);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockProductList, response.getBody());
	}

	@Test
	void updateProductsValidToken() {
		String token = "validToken";
		Product product = new Product();
		Product updatedProduct = new Product();

		when(profileServiceProxy.validateUserRole(token, "Merchant")).thenReturn(true);
		when(productService.updateProducts(product)).thenReturn(updatedProduct);

		ResponseEntity<Product> response = productResource.updateProducts(token, product);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedProduct, response.getBody());
	}

	@Test
	void updateProductsInvalidToken() {
		String token = "invalidToken";
		Product product = new Product();

		when(profileServiceProxy.validateUserRole(token, "Merchant")).thenReturn(false);

		assertThrows(UnauthorizedException.class, () -> productResource.updateProducts(token, product));
	}

	@Test
	void deleteProductValidToken() {
		String token = "validToken";
		int productId = 1;

		when(profileServiceProxy.validateUserRole(token, "Merchant")).thenReturn(true);

		productResource.deleteProduct(token, productId);

		verify(productService, times(1)).deleteProductById(productId);
	}

	@Test
	void deleteProductInvalidToken() {
		String token = "invalidToken";
		int productId = 1;

		when(profileServiceProxy.validateUserRole(token, "Merchant")).thenReturn(false);

		assertThrows(UnauthorizedException.class, () -> productResource.deleteProduct(token, productId));
	}
}
