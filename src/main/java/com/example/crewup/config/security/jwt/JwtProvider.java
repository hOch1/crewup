package com.example.crewup.config.security.jwt;

import java.util.Base64;
import java.util.Date;

import com.example.crewup.config.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.crewup.dto.response.auth.JwtResponse;
import com.example.crewup.entity.member.Member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class JwtProvider {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.access.expiration}")
	private long accessTokenExpiration;

	@Value("${jwt.refresh.expiration}")
	private long refreshTokenExpiration;

	private final CustomUserDetailsService userDetailsService;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public JwtResponse createToken(Member member) {
		Claims claims = Jwts.claims().setSubject(member.getEmail());
		claims.put("role", member.getRole());

		Date now = new Date();
		Date accessTokenExpiration = new Date(now.getTime() + this.accessTokenExpiration);
		Date refreshTokenExpiration = new Date(now.getTime() + this.refreshTokenExpiration);

		String accessToken = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(accessTokenExpiration)
				.signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes()))
				.compact();

		String refreshToken = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(refreshTokenExpiration)
				.signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes()))
				.compact();

		return JwtResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.tokenType("Bearer")
			.build();
	}

	public Authentication getAuthentication(String token) {
		String email = getSubject(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getSubject(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}
}
