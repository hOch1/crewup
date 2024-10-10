package com.example.crewup.dto.request.auth;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.crewup.entity.member.Member;
import com.example.crewup.entity.member.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequest(

	@NotBlank(message = "이메일을 입력해주세요.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	String email,

	@NotBlank(message = "이름을 입력해주세요.")
	String name,

	@NotBlank(message = "닉네임을 입력해주세요.")
	String nickname,

	@NotBlank(message = "비밀번호를 입력해주세요.")
	String password
) {
	public Member toEntity(PasswordEncoder passwordEncoder){
		return Member.builder()
			.email(email())
			.name(name())
			.nickname(nickname())
			.password(passwordEncoder.encode(password()))
			.role(Role.ROLE_USER)
			.build();
	}
}
