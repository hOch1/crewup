package com.example.crewup.controller;

import java.util.List;

import org.springframework.data.domain.Page;
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
import com.example.crewup.dto.ApiResponse;
import com.example.crewup.dto.request.project.CreateProjectRequest;
import com.example.crewup.dto.request.project.Filter;
import com.example.crewup.dto.response.project.ProjectResponse;
import com.example.crewup.service.ProjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProjectController {

	private final ProjectService projectService;

	@PostMapping("/project")
	public ResponseEntity<ApiResponse<Boolean>> createProject(
		@RequestBody @Valid CreateProjectRequest createProjectRequest,
		@AuthenticationPrincipal PrincipalDetails principalDetails) {

		return ResponseEntity.ok(ApiResponse.success(projectService.createProject(createProjectRequest, principalDetails.member())));
	}

	@GetMapping("/projects")
	public ResponseEntity<ApiResponse<Page<ProjectResponse>>> getProjectsByFilter(
		@RequestParam("filter") Filter filter) {

		return ResponseEntity.ok(ApiResponse.success(projectService.getProjectsByFilter(filter)));
	}

	@GetMapping("/project/{projectId}")
	public ResponseEntity<ApiResponse<ProjectResponse>> getProject(
		@PathVariable Long projectId) {

		return ResponseEntity.ok(ApiResponse.success(projectService.getProject(projectId)));
	}

	@GetMapping("/projects/my")
	public ResponseEntity<ApiResponse<List<ProjectResponse>>> getMyProjects(
		@AuthenticationPrincipal PrincipalDetails principalDetails) {

		return ResponseEntity.ok(ApiResponse.success(projectService.getMyProjects(principalDetails.member())));
	}
}
