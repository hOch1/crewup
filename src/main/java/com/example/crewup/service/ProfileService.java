package com.example.crewup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crewup.dto.request.member.UpdateProfileRequest;
import com.example.crewup.dto.response.member.ProfileResponse;
import com.example.crewup.entity.member.Member;
import com.example.crewup.repository.member.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

	private final ProfileRepository profileRepository;

	@Transactional
	public Boolean updateProfile(Member member, UpdateProfileRequest updateProfileRequest) {

		return null;
	}

	public ProfileResponse getMyProfile(Member member) {

		return null;
	}

	public ProfileResponse getProfile(Long memberId) {

		return null;
	}
}
