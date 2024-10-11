package com.example.crewup.dto.request.projectMember;

import com.example.crewup.entity.member.Member;
import com.example.crewup.entity.project.Position;
import com.example.crewup.entity.project.Project;
import com.example.crewup.entity.project.ProjectMember;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "프로젝트 멤버 추가 요청")
public record ProjectMemberRequest(

	@NotBlank
	@Schema(description = "프로젝트 멤버의 포지션", example = "FRONTEND")
	Position position
) {
	public ProjectMember toEntity(Project project, Member member) {
		return ProjectMember.builder()
				.member(member)
				.project(project)
				.position(position)
				.isLeader(false)
				.build();
	}
}
