package com.example.crewup.dto.response.member;

import com.example.crewup.entity.member.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "멤버 응답")
public record MemberResponse(
	@Schema(description = "멤버 ID")
	Long id,
	@Schema(description = "멤버 이름")
	String name,
	@Schema(description = "멤버 이메일")
	String email,
	@Schema(description = "멤버 닉네임")
	String nickname
) {
	public static MemberResponse from(Member member) {
		return MemberResponse.builder()
				.id(member.getId())
				.name(member.getName())
				.email(member.getEmail())
				.nickname(member.getNickname())
				.build();
	}
}
