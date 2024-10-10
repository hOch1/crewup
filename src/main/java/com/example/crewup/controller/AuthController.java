package com.example.crewup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crewup.dto.CustomApiResponse;
import com.example.crewup.dto.request.auth.SigninRequest;
import com.example.crewup.dto.request.auth.SignupRequest;
import com.example.crewup.dto.response.auth.JwtResponse;
import com.example.crewup.entity.member.Member;
import com.example.crewup.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name="Auth", description = "회원가입, 로그인, 토큰 재발급 API")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/sign-up")
	@Operation(summary = "일반 회원가입", description = "일반 회원가입을 진행합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원가입 성공"),
		@ApiResponse(responseCode = "400", description = "이미 존재하는 이메일 입니다."),
		@ApiResponse(responseCode = "400", description = "이미 존재하는 닉네임입니다.")
	})
	public ResponseEntity<CustomApiResponse<Boolean>> signUp(
		@RequestBody @Valid SignupRequest signupRequest) {

		return ResponseEntity.ok(CustomApiResponse.success(authService.signUp(signupRequest)));
	}

	@PostMapping("/sign-in")
	@Operation(summary = "일반 로그인", description = "일반 로그인을 진행합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그인 성공"),
		@ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다."),
		@ApiResponse(responseCode = "400", description = "비밀번호가 일치하지 않습니다.")
	})
	public ResponseEntity<CustomApiResponse<JwtResponse>> signIn(
		@RequestBody @Valid SigninRequest signinRequest) {

		return ResponseEntity.ok(CustomApiResponse.success(authService.signIn(signinRequest)));
	}

	@PostMapping("/reissue")
	@Operation(summary = "토큰 재발급", description = "토큰을 재발급합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "토큰 재발급 성공")
	})
	public ResponseEntity<CustomApiResponse<JwtResponse>> reissue(
		@AuthenticationPrincipal Member member){

		return ResponseEntity.ok(CustomApiResponse.success(authService.reissue(member)));
	}
}
