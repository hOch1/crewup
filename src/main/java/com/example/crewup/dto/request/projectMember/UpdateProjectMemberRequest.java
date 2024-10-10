package com.example.crewup.dto.request.projectMember;

import com.example.crewup.entity.project.Position;

import jakarta.validation.constraints.NotBlank;

public record UpdateProjectMemberRequest(
	@NotBlank
	Position position
) {
}
