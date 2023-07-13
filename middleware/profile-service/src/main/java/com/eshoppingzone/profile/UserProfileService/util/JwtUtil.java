package com.eshoppingzone.profile.UserProfileService.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.eshoppingzone.profile.UserProfileService.exception.JWTException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
	private static Logger log = LoggerFactory.getLogger(JwtUtil.class);

	@Value("${jwt.secret}")
	private String jwtSecret;

	public String generateToken(Authentication authenticationObj) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", authenticationObj.getPrincipal());

		String role = authenticationObj.getAuthorities().stream().findFirst().orElse(null).toString();
		claims.put("role", role);
		return createToken(claims);
	}

	public String createToken(Map<String, Object> claims) {
		String jwt = "";
		try {
			SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
			jwt = Jwts.builder().setClaims(claims).setIssuer("eShoppingZone").setSubject("JWT Token")
					.setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + 900000000)).signWith(key)
					.compact();
			log.info("Token Created Successfully");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return jwt;
	}

	public void validateToken(final String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			log.info("Token Validated Successfully");
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new JWTException(e.getMessage());
		}
	}

	public Boolean isTokenExpired(String token) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		return claims.getExpiration().before(new Date());
	}

	public String getUsernameFromToken(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			return String.valueOf(claims.get("username"));
		} catch (Exception e) {
			throw new JWTException(e.getMessage());
		}
	}

	public String getRoleFromToken(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			return String.valueOf(claims.get("role"));
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid Token Received");
		}
	}

	public boolean validateUserRole(String token, String role) {
		String dbRole = getRoleFromToken(token);
		if (dbRole.equals(role)) {
			log.info("Access Granted");
			return true;
		}
		log.error("Access Denied");
		return false;
	}
}
