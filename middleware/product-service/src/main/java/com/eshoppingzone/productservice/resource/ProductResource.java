package com.eshoppingzone.productservice.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshoppingzone.productservice.entity.Product;
import com.eshoppingzone.productservice.exception.ProductNotFoundException;
import com.eshoppingzone.productservice.exception.UnauthorizedException;
import com.eshoppingzone.productservice.proxy.ProfileServiceProxy;
import com.eshoppingzone.productservice.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product-service/products")
@CrossOrigin(origins = "*")
public class ProductResource {

	private static final String NO_PRODUCT_FOUND_MESSAGE = "No Product Found";
	private static final String ACCESS_DENIED_MESSAGE = "Access Denied";

	@Autowired
	private ProductService productService;

	@Autowired
	private ProfileServiceProxy profileServiceProxy;

	/**
	 * 
	 * @author S Shiv Prashant
	 * @param product
	 * @access Authenticated(Merchant)
	 * @description Add Products
	 */
	@PostMapping("/addProducts")
	public void addProducts(@RequestHeader(HttpHeaders.AUTHORIZATION) final String token,
			@Valid @RequestBody final Product product) {
		if (!profileServiceProxy.validateUserRole(token, "Merchant")) {
			throw new UnauthorizedException(ACCESS_DENIED_MESSAGE);
		}
		productService.addProducts(product);
	}

	/**
	 * 
	 * @author S Shiv Prashant
	 * @access Open
	 * @description Return list of all products
	 */
	@GetMapping("/getAllProducts")
	public ResponseEntity<List<Product>> getAllProducts() {
		return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
	}

	/**
	 * 
	 * @author S Shiv Prashant
	 * @param productId
	 * @access Open
	 * @description Get Product Details By Id
	 */
	@GetMapping("/getProductById/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable final int productId) {
		final Optional<Product> dbProduct = productService.getProductById(productId);
		if (!dbProduct.isPresent()) {
			throw new ProductNotFoundException(NO_PRODUCT_FOUND_MESSAGE);
		}
		return new ResponseEntity<>(dbProduct.get(), HttpStatus.OK);
	}

	/**
	 * 
	 * @author S Shiv Prashant
	 * @param searchString
	 * @access Open
	 * @description Search Products
	 */
	@GetMapping("/searchProducts/{searchString}")
	public ResponseEntity<List<Product>> searchProducts(@PathVariable final String searchString) {
		return new ResponseEntity<>(productService.searchProducts(searchString), HttpStatus.OK);
	}

	/**
	 * 
	 * @author S Shiv Prashant
	 * @access Open
	 * @description Sort Products By Name
	 */
	@GetMapping("/sortByName")
	public ResponseEntity<List<Product>> sortByName() {
		return new ResponseEntity<>(productService.sortByName(), HttpStatus.OK);
	}

	/**
	 * 
	 * @author S Shiv Prashant
	 * @access Open
	 * @description Sort Products By Price
	 */
	@GetMapping("/sortByPrice")
	public ResponseEntity<List<Product>> sortByPrice() {
		return new ResponseEntity<>(productService.sortByPrice(), HttpStatus.OK);
	}

	/**
	 * 
	 * @author S Shiv Prashant
	 * @param productType
	 * @access Open
	 * @description Get Product By Product Type
	 */
	@GetMapping("/getProductByType/{productType}")
	public ResponseEntity<List<Product>> getProductByType(@PathVariable final String productType) {
		return new ResponseEntity<>(productService.getProductByType(productType), HttpStatus.OK);
	}

	/**
	 * 
	 * @author S Shiv Prashant
	 * @param productName
	 * @access Open
	 * @description Get Product By Product Name
	 */
	@GetMapping("/getProductByName/{productName}")
	public ResponseEntity<Product> getProductByName(@PathVariable final String productName) {
		final Optional<Product> dbProduct = productService.getProductByName(productName);
		if (!dbProduct.isPresent()) {
			throw new ProductNotFoundException(NO_PRODUCT_FOUND_MESSAGE);
		}
		return new ResponseEntity<>(dbProduct.get(), HttpStatus.OK);
	}

	/**
	 * 
	 * @author S Shiv Prashant
	 * @param category
	 * @access Open
	 * @description Get Product By Product Category
	 */
	@GetMapping("/getProductByCategory/{category}")
	public ResponseEntity<List<Product>> getProductByCategory(@PathVariable final String category) {
		return new ResponseEntity<>(productService.getProductByCategory(category), HttpStatus.OK);
	}

	/**
	 * 
	 * @author S Shiv Prashant
	 * @param product
	 * @access Authenticated(Merchant)
	 * @description Update Product Details
	 */
	@PutMapping("/updateProducts")
	public ResponseEntity<Product> updateProducts(@RequestHeader(HttpHeaders.AUTHORIZATION) final String token,
			@RequestBody final Product product) {
		if (!profileServiceProxy.validateUserRole(token, "Merchant")) {
			throw new UnauthorizedException(ACCESS_DENIED_MESSAGE);
		}
		return new ResponseEntity<>(productService.updateProducts(product), HttpStatus.OK);
	}

	/**
	 * 
	 * @author S Shiv Prashant
	 * @param productId
	 * @access Authenticated(Merchant)
	 * @description Delete Product
	 */
	@DeleteMapping("/deleteProduct/{productId}")
	public void deleteProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) final String token,
			@PathVariable final int productId) {
		if (!profileServiceProxy.validateUserRole(token, "Merchant")) {
			throw new UnauthorizedException(ACCESS_DENIED_MESSAGE);
		}
		productService.deleteProductById(productId);
	}

}
