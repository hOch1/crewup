package com.example.crewup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crewup.dto.request.auth.SigninRequest;
import com.example.crewup.dto.request.auth.SignupRequest;
import com.example.crewup.dto.response.JwtResponse;
import com.example.crewup.entity.member.Member;
import com.example.crewup.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/sign-up")
	public ResponseEntity<Boolean> signUp(@RequestBody SignupRequest signupRequest) {
		return ResponseEntity.ok(authService.signUp(signupRequest));
	}

	@PostMapping("/sign-in")
	public ResponseEntity<JwtResponse> signIn(@RequestBody SigninRequest signinRequest) {
		return ResponseEntity.ok(authService.signIn(signinRequest));
	}

	@PostMapping("/reissue")
	public ResponseEntity<JwtResponse> reissue(@AuthenticationPrincipal Member member){
		return ResponseEntity.ok(authService.reissue(member));
	}
}
