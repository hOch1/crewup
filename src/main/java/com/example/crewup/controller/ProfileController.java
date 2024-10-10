package com.example.crewup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crewup.config.security.PrincipalDetails;
import com.example.crewup.dto.CustomApiResponse;
import com.example.crewup.dto.request.member.UpdateProfileRequest;
import com.example.crewup.dto.response.member.ProfileResponse;
import com.example.crewup.service.ProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Tag(name = "Member", description = "회원 API")
public class ProfileController {

	private final ProfileService profileService;

	@PostMapping("/profile")
	@Operation(summary = "프로필 수정", description = "프로필을 수정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "프로필 수정 성공"),
	})
	public ResponseEntity<CustomApiResponse<Boolean>> updateProfile(
		@AuthenticationPrincipal PrincipalDetails principalDetails,
		@RequestBody UpdateProfileRequest updateProfileRequest){

		return ResponseEntity.ok(CustomApiResponse.success(profileService.updateProfile(principalDetails.member(), updateProfileRequest)));
	}

	@GetMapping("/profile/me")
	@Operation(summary = "내 프로필 조회", description = "내 프로필을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "내 프로필 조회 성공"),
	})
	public ResponseEntity<CustomApiResponse<ProfileResponse>> getMyProfile(
		@AuthenticationPrincipal PrincipalDetails principalDetails){

		return ResponseEntity.ok(CustomApiResponse.success(profileService.getMyProfile(principalDetails.member())));
	}

	@GetMapping("/profile/{memberId}")
	@Operation(summary = "프로필 조회", description = "프로필을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
	})
	public ResponseEntity<CustomApiResponse<ProfileResponse>> getProfile(
		@PathVariable Long memberId){

		return ResponseEntity.ok(CustomApiResponse.success(profileService.getProfile(memberId)));
	}
}
