package com.example.crewup.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crewup.config.security.jwt.JwtProvider;
import com.example.crewup.dto.request.auth.SigninRequest;
import com.example.crewup.dto.request.auth.SignupRequest;
import com.example.crewup.dto.response.auth.JwtResponse;
import com.example.crewup.entity.member.Member;
import com.example.crewup.exception.CustomException;
import com.example.crewup.exception.ErrorCode;
import com.example.crewup.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	@Transactional
	public Boolean signUp(SignupRequest signupRequest) {
		if (memberRepository.existsByEmail(signupRequest.email()))
			throw new CustomException(ErrorCode.ALREADY_EXISTS_EMAIL);
		if (memberRepository.existsByNickname(signupRequest.nickname()))
			throw new CustomException(ErrorCode.ALREADY_EXISTS_NICKNAME);

		memberRepository.save(signupRequest.toEntity(passwordEncoder));
		return true;
	}

	public JwtResponse signIn(SigninRequest signinRequest) {
		Member member = memberRepository.findByEmail(signinRequest.email())
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

		if (!passwordEncoder.matches(signinRequest.password(), member.getPassword()))
			throw new CustomException(ErrorCode.PASSWORD_NOT_MATCHED);

		return jwtProvider.createToken(member);
	}

	public JwtResponse reissue(Member member) {
		return jwtProvider.createToken(member);
	}
}
