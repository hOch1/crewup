package com.example.crewup.config.security.jwt;

import java.io.IOException;

import com.example.crewup.exception.CustomException;
import com.example.crewup.exception.ErrorCode;

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
			request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN);
		} catch (MalformedJwtException e) {
			request.setAttribute("exception", ErrorCode.MALFORMED_TOKEN);
		} catch (UnsupportedJwtException e) {
			request.setAttribute("exception", ErrorCode.UNSUPPORTED_TOKEN);
		} catch (JwtException | IllegalArgumentException e) {
			request.setAttribute("exception", ErrorCode.INVALID_TOKEN);
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
}
