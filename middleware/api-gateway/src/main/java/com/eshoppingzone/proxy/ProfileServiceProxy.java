package com.eshoppingzone.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "profile-service")
public interface ProfileServiceProxy {
	@GetMapping("/profiles/validateToken")
	public Boolean validateJwtToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
