package com.example.crewup.dto.request.auth;

import jakarta.validation.constraints.NotBlank;

public record SigninRequest(
	@NotBlank
	String email,

	@NotBlank
	String password
) {
}
