package com.eshoppingzone.cartservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eshoppingzone.cartservice.entity.Product;

@FeignClient(name = "product-service")
public interface ProductServiceProxy {
	@GetMapping("/product-service/products/getProductById/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable final int productId);
}
