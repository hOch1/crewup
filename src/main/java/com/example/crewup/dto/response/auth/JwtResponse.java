package com.example.crewup.dto.response.auth;

import lombok.Builder;

@Builder
public record JwtResponse(
	String accessToken,
	String refreshToken,
	String tokenType
) {

}
