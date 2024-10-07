package com.example.crewup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.crewup.dto.CustomApiResponse;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<CustomApiResponse<String>> customException(CustomException e) {
		CustomApiResponse<String> response = CustomApiResponse.fail(e.getErrorCode().getMessage());
		HttpStatus httpStatus = HttpStatus.valueOf(e.getErrorCode().getStatus());

		return ResponseEntity.status(httpStatus).body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CustomApiResponse<String>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		CustomApiResponse<String> response = CustomApiResponse.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());


		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
}
