package com.eshoppingzone.cartservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "profile-service")
public interface ProfileServiceProxy {
	@PostMapping("profile-service/auth/validateUserRole/{token}/{role}")
	boolean validateUserRole(@PathVariable String token, @PathVariable String role);
}
