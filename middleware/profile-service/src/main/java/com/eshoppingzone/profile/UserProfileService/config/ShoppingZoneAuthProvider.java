package com.eshoppingzone.profile.UserProfileService.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eshoppingzone.profile.UserProfileService.pojo.UserProfile;
import com.eshoppingzone.profile.UserProfileService.service.ProfileService;

@Service
public class ShoppingZoneAuthProvider implements AuthenticationProvider {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ProfileService profileService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		UserProfile authUser = profileService.getByUsername(username);

		if (authUser == null) {
			throw new BadCredentialsException("User details not found for user " + username);
		}

		if (passwordEncoder.matches(password, authUser.getPassword())) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			String authority = authUser.getRole();
			authorities.add(new SimpleGrantedAuthority(authority));
			return new UsernamePasswordAuthenticationToken(username, password, authorities);
		}
		throw new BadCredentialsException("Invalid Credentials");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
