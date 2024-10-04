package com.example.crewup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.crewup.dto.ApiResponse;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<String>> customException(CustomException e) {
		ApiResponse<String> response = ApiResponse.fail(e.getErrorCode().getMessage());
		HttpStatus httpStatus = HttpStatus.valueOf(e.getErrorCode().getStatus());

		return ResponseEntity.status(httpStatus).body(response);
	}
}
