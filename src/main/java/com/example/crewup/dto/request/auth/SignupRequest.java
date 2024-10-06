package com.example.crewup.dto.request.auth;

import jakarta.validation.constraints.NotBlank;

public record SignupRequest(

	@NotBlank
	String email,

	@NotBlank
	String name,

	@NotBlank
	String nickname,

	@NotBlank
	String password
) {

}
