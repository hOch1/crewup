package com.example.crewup.dto.request.member;

import com.example.crewup.entity.member.LinkType;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프로필 수정 요청")
public record UpdateProfileRequest(
	@Schema(description = "자기소개", nullable = true)
	String bio,
	@Schema(description = "링크", nullable = true)
	String link,
	@Schema(description = "링크 타입", nullable = true)
	LinkType linkType,
	@Schema(description = "프로필 이미지", nullable = true)
	String profileImage
) {

}
