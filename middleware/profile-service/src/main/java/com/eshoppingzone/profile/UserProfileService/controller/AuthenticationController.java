package com.eshoppingzone.profile.UserProfileService.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshoppingzone.profile.UserProfileService.pojo.AuthRequest;
import com.eshoppingzone.profile.UserProfileService.pojo.JsonResponse;
import com.eshoppingzone.profile.UserProfileService.pojo.UserProfile;
import com.eshoppingzone.profile.UserProfileService.service.ProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("profile-service/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
	@Autowired
	public ProfileService profileService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/add-profile")
	public JsonResponse addNewCustomerProfile(@Valid @RequestBody UserProfile userProfile) throws InterruptedException {
		String username = userProfile.getUsername();
		String password = userProfile.getPassword();
		String hashPassword = passwordEncoder.encode(password);
		userProfile.setPassword(hashPassword);
		UserProfile addNewProfile = profileService.addNewProfile(userProfile);

		return login(new AuthRequest(username, password));
	}

	@PostMapping("/login")
	public JsonResponse login(@RequestBody AuthRequest authRequest) {
		System.out.println(authRequest.getUsername() + authRequest.getPassword());
		String username = authRequest.getUsername();
		String password = authRequest.getPassword();
		try {
			Authentication authenticateObj = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			// Generate JWT Token
			String token = profileService.generateJwtToken(authenticateObj);
			Collection<? extends GrantedAuthority> authorities = authenticateObj.getAuthorities();
			List<String> roles = new ArrayList<>();
			authorities.stream().map(GrantedAuthority::getAuthority).forEach(roles::add);

			UserProfile authUser = profileService.getByUsername(username);
			return new JsonResponse(token, username, authUser.getProfileId(), roles);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid credentials");
		}
	}

	@GetMapping("/validateToken")
	public void validateJwtToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		profileService.validateJwtToken(token);
	}

	@PostMapping("/validateUserRole/{token}/{role}")
	public boolean validateUserRole(@PathVariable String token, @PathVariable String role) {
		return profileService.validateUserRole(token, role);
	}
}
