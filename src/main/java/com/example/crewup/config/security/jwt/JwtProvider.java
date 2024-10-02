package com.example.crewup.config.security.jwt;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.crewup.dto.response.auth.JwtResponse;
import com.example.crewup.entity.member.Member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;

@Component
public class JwtProvider {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.access.expiration}")
	private long accessTokenExpiration;

	@Value("${jwt.refresh.expiration}")
	private long refreshTokenExpiration;

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

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			System.out.println("Invalid JWT token: " + e.getMessage());
		}
		return false;
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
