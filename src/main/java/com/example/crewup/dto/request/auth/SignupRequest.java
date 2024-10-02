package com.example.crewup.dto.request.auth;

public record SignupRequest(
	String email,
	String name,
	String nickname,
	String password
) {

}
