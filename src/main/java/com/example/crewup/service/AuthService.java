package com.example.crewup.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crewup.config.security.jwt.JwtProvider;
import com.example.crewup.dto.request.auth.SigninRequest;
import com.example.crewup.dto.request.auth.SignupRequest;
import com.example.crewup.dto.response.JwtResponse;
import com.example.crewup.entity.member.Member;
import com.example.crewup.repository.MemberRepository;

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
		memberRepository.save(Member.of(signupRequest, passwordEncoder));

		return true;
	}

	public JwtResponse signIn(SigninRequest signinRequest) {
		Member member = memberRepository.findByEmail(signinRequest.email())
			.orElseThrow(() -> new IllegalArgumentException("가입되지 않은 회원 입니다."));

		if (!passwordEncoder.matches(signinRequest.password(), member.getPassword()))
			throw new IllegalArgumentException("잘못된 비밀번호입니다.");


		return jwtProvider.createToken(member);
	}

	public JwtResponse reissue(Member member) {
		return jwtProvider.createToken(member);
	}
}
