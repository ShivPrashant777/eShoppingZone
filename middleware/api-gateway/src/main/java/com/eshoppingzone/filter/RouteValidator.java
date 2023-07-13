package com.eshoppingzone.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {
	public static final List<String> openApiEndpoints = List.of("/profile-service/auth/add-profile",
			"/profile-service/profiles/getAll", "/profile-service/auth/login", "/profile-service/auth/validateToken",
			"/product-service/products/getAllProducts", "product-service/products/getProductById",
			"product-service/products/searchProducts", "product-service/products/sortByName",
			"product-service/products/sortByPrice");

	public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream()
			.noneMatch(uri -> request.getURI().getPath().contains(uri));
}
