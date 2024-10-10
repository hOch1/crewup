package com.example.crewup.dto.request.projectMember;

import com.example.crewup.entity.member.Member;
import com.example.crewup.entity.project.Position;
import com.example.crewup.entity.project.Project;
import com.example.crewup.entity.project.ProjectMember;

import jakarta.validation.constraints.NotBlank;

public record ProjectMemberRequest(
	@NotBlank
	Position position
) {
	public ProjectMember toEntity(Project project, Member member) {
		return ProjectMember.builder()
				.member(member)
				.project(project)
				.position(position)
				.build();
	}
}
