package com.example.crewup.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crewup.dto.request.member.UpdateLinkRequest;
import com.example.crewup.dto.request.member.UpdateProfileRequest;
import com.example.crewup.dto.response.member.ProfileResponse;
import com.example.crewup.entity.member.Member;
import com.example.crewup.entity.member.Profile;
import com.example.crewup.exception.CustomException;
import com.example.crewup.exception.ErrorCode;
import com.example.crewup.repository.member.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

	private final ProfileRepository profileRepository;

	@Transactional
	public Boolean updateProfile(Member member, UpdateProfileRequest updateProfileRequest) {
		Profile profile = profileRepository.findByMemberId(member.getId())
			.orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));

		List<UpdateLinkRequest> linkRequests = updateProfileRequest.linkRequests();
		if (linkRequests != null && !linkRequests.isEmpty()) {
			profile.clearLinks();

			updateProfileRequest.linkRequests().forEach(linkRequest ->
				profile.addLink(linkRequest.toEntity(profile)));
		}

		profile.update(updateProfileRequest);
		return true;
	}

	public ProfileResponse getMyProfile(Member member) {
		Profile profile = profileRepository.findByMemberId(member.getId())
			.orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));

		return ProfileResponse.from(profile);
	}

	public ProfileResponse getProfile(Long memberId) {
		Profile profile = profileRepository.findByMemberId(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));

		return ProfileResponse.from(profile);
	}
}
