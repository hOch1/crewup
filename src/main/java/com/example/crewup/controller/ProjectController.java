package com.example.crewup.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crewup.config.security.PrincipalDetails;
import com.example.crewup.dto.CustomApiResponse;
import com.example.crewup.dto.request.project.CreateProjectRequest;
import com.example.crewup.dto.request.project.Filter;
import com.example.crewup.dto.response.project.ProjectResponse;
import com.example.crewup.service.ProjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Project", description = "프로젝트 API")
public class ProjectController {

	private final ProjectService projectService;

	@PostMapping("/project")
	@Operation(summary = "프로젝트 생성", description = "프로젝트를 생성합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "프로젝트 생성 성공")
	})
	public ResponseEntity<CustomApiResponse<Boolean>> createProject(
		@RequestBody @Valid CreateProjectRequest createProjectRequest,
		@AuthenticationPrincipal PrincipalDetails principalDetails) {

		return ResponseEntity.ok(
			CustomApiResponse.success(projectService.createProject(createProjectRequest, principalDetails.member())));
	}

	@GetMapping("/projects")
	@Operation(summary = "프로젝트 목록 조회", description = "프로젝트 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "프로젝트 목록 조회 성공")
	})
	public ResponseEntity<CustomApiResponse<Page<ProjectResponse>>> getProjectsByFilter(
		@RequestParam("filter") Filter filter,
		@RequestParam("page") int page) {

		Pageable pageable = PageRequest.of(page, 10);
		return ResponseEntity.ok(CustomApiResponse.success(projectService.getProjectsByFilter(filter, pageable)));
	}

	@GetMapping("/project/{projectId}")
	@Operation(summary = "프로젝트 상세 조회", description = "프로젝트 상세를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "프로젝트 상세 조회 성공"),
		@ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없습니다.")
	})
	public ResponseEntity<CustomApiResponse<ProjectResponse>> getProject(
		@PathVariable Long projectId) {

		return ResponseEntity.ok(CustomApiResponse.success(projectService.getProject(projectId)));
	}

	@GetMapping("/projects/my")
	@Operation(summary = "내 프로젝트 목록 조회", description = "내 프로젝트 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "내 프로젝트 목록 조회 성공", content = @Content(
			schema = @Schema(implementation = CustomApiResponse.class, oneOf = ProjectResponse.class)))
	})
	public ResponseEntity<CustomApiResponse<List<ProjectResponse>>> getMyProjects(
		@AuthenticationPrincipal PrincipalDetails principalDetails) {

		return ResponseEntity.ok(CustomApiResponse.success(projectService.getMyProjects(principalDetails.member())));
	}
}
