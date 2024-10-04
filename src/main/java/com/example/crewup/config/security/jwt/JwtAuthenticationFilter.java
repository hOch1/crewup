package com.example.crewup.config.security.jwt;

import java.io.IOException;
import java.security.SignatureException;

import com.example.crewup.dto.ApiResponse;
import com.example.crewup.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try{
			String token = resolveToken(request);

			if (StringUtils.hasText(token) && jwtProvider.validateToken(token)){
				Authentication authentication = jwtProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (ExpiredJwtException e) {
			setErrorResponse(response, ErrorCode.EXPIRED_TOKEN);
			return;
		} catch (MalformedJwtException e) {
			setErrorResponse(response, ErrorCode.MALFORMED_TOKEN);
			return;
		} catch (UnsupportedJwtException e) {
			setErrorResponse(response, ErrorCode.UNSUPPORTED_TOKEN);
			return;
		} catch (JwtException | IllegalArgumentException e) {
			setErrorResponse(response, ErrorCode.INVALID_TOKEN);
			return;
		}
		filterChain.doFilter(request, response);

	}

	private String resolveToken(HttpServletRequest request){
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
			return bearerToken.substring(7);
		}
		return null;
	}

	private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.setStatus(errorCode.getStatus());

		ApiResponse<String> apiResponse = ApiResponse.fail(errorCode.getMessage());
		response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
	}


}
