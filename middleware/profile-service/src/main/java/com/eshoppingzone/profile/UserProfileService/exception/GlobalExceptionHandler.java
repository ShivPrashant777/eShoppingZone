package com.eshoppingzone.profile.UserProfileService.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.security.SignatureException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(ProfileNotFoundException.class)
	protected ResponseEntity<Object> handleProfileException(RuntimeException ex, WebRequest request) {
		String message = ex.getMessage();
		Map<String, String> response = createResponse(message, HttpStatus.NOT_FOUND);
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(JWTException.class)
	protected ResponseEntity<Object> handleJWTException(RuntimeException ex, WebRequest request) {
		String message = ex.getMessage();
		Map<String, String> response = createResponse(message, HttpStatus.UNAUTHORIZED);
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}

	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<Object> handleBadCredentialsException(RuntimeException ex, WebRequest request) {
		String message = ex.getMessage();
		Map<String, String> response = createResponse(message, HttpStatus.BAD_REQUEST);
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(SignatureException.class)
	protected ResponseEntity<Object> handleSignatureException(RuntimeException ex, WebRequest request) {
		String message = ex.getMessage();
		Map<String, String> response = createResponse(message, HttpStatus.UNAUTHORIZED);
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, Map<String, String>> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		response.put("errors", errors);
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<Object> handleGenericException(RuntimeException ex, WebRequest request) {
		String message = ex.getMessage();
		Map<String, String> response = createResponse(message, HttpStatus.INTERNAL_SERVER_ERROR);
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	public Map<String, String> createResponse(String message, HttpStatus status) {
		Map<String, String> response = new HashMap<>();
		response.put("message", message);
		response.put("status", status.toString());
		return response;
	}

}
