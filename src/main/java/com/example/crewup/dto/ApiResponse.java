package com.example.crewup.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	private T data;
	private String message;

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(data, null);
	}

	public static <T> ApiResponse<T> fail(String message) {
		return new ApiResponse<>(null, message);
	}
}
