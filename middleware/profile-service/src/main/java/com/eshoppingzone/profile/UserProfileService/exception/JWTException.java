package com.eshoppingzone.profile.UserProfileService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JWTException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7062704607632099924L;

	public JWTException(String message) {
		super(message);
	}
}
