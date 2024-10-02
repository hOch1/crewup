package com.example.crewup.dto.response;

import lombok.Builder;

public record JwtResponse(
	String accessToken,
	String refreshToken,
	String tokenType
) {

	@Builder
	public JwtResponse(String accessToken, String refreshToken, String tokenType) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.tokenType = tokenType;
	}
}
