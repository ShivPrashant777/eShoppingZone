package com.eshoppingzone.profile.UserProfileService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProfileNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2755637465156832049L;

	public ProfileNotFoundException(String message) {
		super(message);
	}
}
