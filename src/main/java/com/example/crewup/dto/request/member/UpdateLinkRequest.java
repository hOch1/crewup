package com.example.crewup.dto.request.member;

import com.example.crewup.entity.member.LinkType;
import com.example.crewup.entity.member.Profile;
import com.example.crewup.entity.member.ProfileLink;

public record UpdateLinkRequest(
	String link,
	LinkType linkType
) {

	public ProfileLink toEntity(Profile profile) {
		return ProfileLink.builder()
			.link(link())
			.linkType(linkType())
			.profile(profile)
			.build();
	}
}
