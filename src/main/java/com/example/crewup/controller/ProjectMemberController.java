package com.example.crewup.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crewup.config.security.PrincipalDetails;
import com.example.crewup.dto.CustomApiResponse;
import com.example.crewup.dto.request.projectMember.ProjectMemberRequest;
import com.example.crewup.dto.request.projectMember.UpdateProjectMemberRequest;
import com.example.crewup.dto.response.project.ProjectMemberResponse;
import com.example.crewup.service.ProjectMemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/project/{projectId}")
@RequiredArgsConstructor
@Tag(name = "ProjectMember", description = "프로젝트 멤버 API")
public class ProjectMemberController {

	private final ProjectMemberService projectMemberService;

	@PostMapping("/member")
	@Operation(summary = "프로젝트 멤버 추가", description = "프로젝트에 멤버를 추가합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "프로젝트 멤버 추가 성공")
	})
	public ResponseEntity<CustomApiResponse<Boolean>> addMemberToProject(
		@PathVariable Long projectId,
		@RequestBody @Valid ProjectMemberRequest request,
		@AuthenticationPrincipal PrincipalDetails principalDetails) {

		return ResponseEntity.ok(CustomApiResponse.success(projectMemberService.addMemberToProject(projectId, principalDetails.member(), request)));
	}

	@GetMapping("/members")
	@Operation(summary = "프로젝트 멤버 조회", description = "프로젝트의 멤버를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "프로젝트 멤버 조회 성공")
	})
	public ResponseEntity<CustomApiResponse<List<ProjectMemberResponse>>> getMembersOfProject(
		@PathVariable Long projectId) {

		return ResponseEntity.ok(CustomApiResponse.success(projectMemberService.getMembersOfProject(projectId)));
	}

	@PatchMapping("/member/{memberId}")
	@Operation(summary = "프로젝트 멤버 수정", description = "프로젝트의 멤버를 수정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "프로젝트 멤버 수정 성공")
	})
	public ResponseEntity<CustomApiResponse<Boolean>> updateMemberOfProject(
		@PathVariable Long projectId,
		@PathVariable Long memberId,
		@RequestBody @Valid UpdateProjectMemberRequest request,
		@AuthenticationPrincipal PrincipalDetails principalDetails) {

		return ResponseEntity.ok(CustomApiResponse.success(projectMemberService.updateMemberOfProject(projectId, memberId, principalDetails.member(), request)));
	}

	@PatchMapping("/member/{memberId}/leader")
	@Operation(summary = "프로젝트 리더 변경", description = "프로젝트의 리더를 변경합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "프로젝트 리더 변경 성공")
	})
	public ResponseEntity<CustomApiResponse<Boolean>> updateLeaderOfProject(
		@PathVariable Long projectId,
		@PathVariable Long memberId,
		@AuthenticationPrincipal PrincipalDetails principalDetails) {

		return ResponseEntity.ok(CustomApiResponse.success(projectMemberService.updateLeaderOfProject(projectId, memberId, principalDetails.member())));
	}

	@DeleteMapping("/member/{memberId}")
	@Operation(summary = "프로젝트 멤버 삭제", description = "프로젝트의 멤버를 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "프로젝트 멤버 삭제 성공")
	})
	public ResponseEntity<CustomApiResponse<Boolean>> deleteMemberOfProject(
		@PathVariable Long projectId,
		@PathVariable Long memberId,
		@AuthenticationPrincipal PrincipalDetails principalDetails) {

		return ResponseEntity.ok(CustomApiResponse.success(projectMemberService.deleteMemberOfProject(projectId, memberId, principalDetails.member())));
	}
}
