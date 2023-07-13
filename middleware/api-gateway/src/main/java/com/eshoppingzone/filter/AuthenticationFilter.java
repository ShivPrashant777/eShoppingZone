package com.eshoppingzone.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import com.eshoppingzone.util.JwtUtil;
import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.MalformedJwtException;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
	@Autowired
	RouteValidator routeValidator;

	@Autowired
	JwtUtil jwtUtil;

	public AuthenticationFilter() {
		super(Config.class);
	}

	public static class Config {
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			if (routeValidator.isSecured.test(exchange.getRequest())) {
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					System.out.println("Missing Authorization Header");
					throw new RuntimeException("Missing Authorization Header");
				}

				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}
				try {
					jwtUtil.validateToken(authHeader);
				} catch (Exception e) {
					System.out.println("Unauthorized access");
					throw new MalformedJwtException("Invalid Jwt");
				}
			}
			return chain.filter(exchange);
		});
	}

}
