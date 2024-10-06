package com.example.crewup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crewup.dto.ApiResponse;
import com.example.crewup.dto.request.auth.SigninRequest;
import com.example.crewup.dto.request.auth.SignupRequest;
import com.example.crewup.dto.response.auth.JwtResponse;
import com.example.crewup.entity.member.Member;
import com.example.crewup.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/sign-up")
	public ResponseEntity<ApiResponse<Boolean>> signUp(
		@RequestBody @Valid SignupRequest signupRequest) {

		return ResponseEntity.ok(ApiResponse.success(authService.signUp(signupRequest)));
	}

	@PostMapping("/sign-in")
	public ResponseEntity<ApiResponse<JwtResponse>> signIn(
		@RequestBody @Valid SigninRequest signinRequest) {

		return ResponseEntity.ok(ApiResponse.success(authService.signIn(signinRequest)));
	}

	@PostMapping("/reissue")
	public ResponseEntity<ApiResponse<JwtResponse>> reissue(
		@AuthenticationPrincipal Member member){

		return ResponseEntity.ok(ApiResponse.success(authService.reissue(member)));
	}
}
