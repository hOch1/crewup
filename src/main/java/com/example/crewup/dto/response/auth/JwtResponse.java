package com.example.crewup.dto.response.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "JWT 응답")
public record JwtResponse(
	@Schema(description = "액세스 토큰")
	String accessToken,

	@Schema(description = "리프레시 토큰")
	String refreshToken,

	@Schema(description = "토큰 타입")
	String tokenType
) {

}
