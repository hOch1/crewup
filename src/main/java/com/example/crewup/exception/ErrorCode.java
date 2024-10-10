package com.example.crewup.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	MEMBER_NOT_FOUND("해당 회원을 찾을 수 없습니다.", 404),
	PROJECT_NOT_FOUND("해당 프로젝트를 찾을 수 없습니다.", 404),
	PROJECT_MEMBER_NOT_FOUND("해당 프로젝트의 멤버를 찾을 수 없습니다.", 404),

	PROJECT_IS_DELETED("삭제된 프로젝트 입니다.", 410),

	ALREADY_EXISTS_EMAIL("이미 가입된 이메일입니다.", 400),
	ALREADY_EXISTS_NICKNAME("이미 사용중인 닉네임 입니다.", 400),
	PASSWORD_NOT_MATCHED("비밀번호가 맞지않습니다.", 400),

	EXPIRED_TOKEN("만료된 토큰입니다.", 401),
	INVALID_TOKEN("유효하지 않은 토큰입니다.", 401),
	MALFORMED_TOKEN("잘못된 형식의 토큰입니다.", 401),
	UNSUPPORTED_TOKEN("지원하지 않는 토큰입니다.", 401),

	ACCESS_DENIED("접근 권한이 없습니다.", 403),
	;

	private final String message;
	private final int status;

	ErrorCode(String message, int status) {
		this.message = message;
		this.status = status;
	}
}
