package com.example.crewup.dto.request.member;

import java.util.List;

import com.example.crewup.entity.member.LinkType;
import com.example.crewup.entity.member.Member;
import com.example.crewup.entity.member.Profile;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프로필 수정 요청")
public record UpdateProfileRequest(
	@Schema(description = "자기소개", nullable = true)
	String bio,

	@Schema(description = "프로필 이미지", nullable = true)
	String profileImage,

	@Schema(description = "링크 목록", nullable = true)
	List<UpdateLinkRequest> linkRequests
) {
	public Profile toEntity(Member member) {
		return Profile.builder()
			.bio(bio())
			.profileImage(profileImage())
			.member(member)
			.build();
	}
}
