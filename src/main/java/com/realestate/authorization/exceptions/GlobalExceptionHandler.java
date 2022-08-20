package com.realestate.authorization.exceptions;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetails, status);
	}

	@ExceptionHandler(UserAlredyExistsException.class)
	public ResponseEntity<ExceptionDetails> handleUserAlredyExistsException(UserAlredyExistsException ex) {
		ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetails, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<Object> handleGlobalException(AuthorizationException ex, WebRequest request) {
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ExceptionDetails> handleFeignStatusException(FeignException ex,
			HttpServletResponse response) {
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
