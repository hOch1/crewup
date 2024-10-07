package com.example.crewup.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Custom API 응답 포맷")
public record CustomApiResponse<T> (

	@Schema(description = "응답 데이터", nullable = true)
	T data,

	@Schema(description = "응답 메시지 (실패 했을경우)", nullable = true)
	String message,

	@Schema(description = "응답 시간")
	LocalDateTime timestamp

) {
	public static <T> CustomApiResponse<T> success(T data) {
		return new CustomApiResponse<>(data, null, LocalDateTime.now());
	}

	public static <T> CustomApiResponse<T> fail(String message) {
		return new CustomApiResponse<>(null, message, LocalDateTime.now());
	}
}
