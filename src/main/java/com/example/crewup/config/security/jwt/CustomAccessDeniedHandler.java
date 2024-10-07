package com.example.crewup.config.security.jwt;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.example.crewup.dto.CustomApiResponse;
import com.example.crewup.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.setStatus(ErrorCode.ACCESS_DENIED.getStatus());

		CustomApiResponse<String> customApiResponse = CustomApiResponse.fail(ErrorCode.ACCESS_DENIED.getMessage());
		response.getWriter().write(objectMapper.writeValueAsString(customApiResponse));
	}
}
