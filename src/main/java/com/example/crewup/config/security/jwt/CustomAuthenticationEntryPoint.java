package com.example.crewup.config.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.crewup.dto.CustomApiResponse;
import com.example.crewup.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper = new ObjectMapper();
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {

		ErrorCode code = (ErrorCode) request.getAttribute("exception");

		if (code == null)
			code = ErrorCode.ACCESS_DENIED;

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.setStatus(code.getStatus());

		CustomApiResponse<String> customApiResponse = CustomApiResponse.fail(code.getMessage());
		response.getWriter().write(objectMapper.writeValueAsString(customApiResponse));
	}
}
