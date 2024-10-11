package com.example.crewup.dto.response.member;

import java.util.List;

import com.example.crewup.entity.member.LinkType;
import com.example.crewup.entity.member.ProfileLink;

public record LinkResponse(
	String link,
	LinkType linkType
) {
	public static List<LinkResponse> from(List<ProfileLink> links) {
		return links.stream()
			.map(link -> new LinkResponse(link.getLink(), link.getLinkType()))
			.toList();
	}
}
