package com.example.crewup.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	MEMBER_NOT_FOUND("해당 회원을 찾을 수 없습니다.", 404),

	ALREADY_EXISTS_EMAIL("이미 가입된 이메일입니다.", 400),
	ALREADY_EXISTS_NICKNAME("이미 사용중인 닉네임 입니다.", 400);

	private final String message;
	private final int status;

	ErrorCode(String message, int status) {
		this.message = message;
		this.status = status;
	}
}
