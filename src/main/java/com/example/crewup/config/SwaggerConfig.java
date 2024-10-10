package com.example.crewup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		// JWT 보안 스키마 이름 정의
		String securitySchemeName = "JWT";

		// SecurityRequirement 설정: JWT 인증을 모든 엔드포인트에 적용
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(securitySchemeName);

		SecurityScheme securityScheme = new SecurityScheme()
			.name(securitySchemeName)
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT");

		return new OpenAPI()
			.components(new Components().addSecuritySchemes(securitySchemeName, securityScheme))
			.info(apiInfo())
			.addSecurityItem(securityRequirement);
	}

	// API 메타데이터 설정: API 이름, 설명, 버전 등 정의
	private Info apiInfo() {
		return new Info()
			.title("Crew Up API")
			.description("Crew Up API Reference for Developers")
			.version("1.0");
	}
}
