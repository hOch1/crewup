package com.example.crewup.dto.response.member;

import java.util.List;

import com.example.crewup.entity.member.Profile;

import lombok.Builder;

@Builder
public record ProfileResponse(
	String bio,
	String profileImage,
	List<LinkResponse> links
) {
	public static ProfileResponse from(Profile profile) {
		return ProfileResponse.builder()
			.bio(profile.getBio())
			.profileImage(profile.getProfileImage())
			.links(LinkResponse.from(profile.getProfileLinks()))
			.build();
	}
}
