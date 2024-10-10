package com.example.crewup.dto.response.project;

import com.example.crewup.dto.response.member.MemberResponse;
import com.example.crewup.entity.project.ProjectMember;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "프로젝트 멤버 응답")
public record ProjectMemberResponse(

	@Schema(description = "프로젝트 멤버 ID")
	Long id,
	@Schema(description = "멤버")
	MemberResponse member,
	@Schema(description = "리더 여부")
	boolean isLeader

) {
	public static ProjectMemberResponse from(ProjectMember projectMember) {
		return ProjectMemberResponse.builder()
				.id(projectMember.getId())
				.member(MemberResponse.from(projectMember.getMember()))
				.isLeader(projectMember.isLeader())
				.build();
	}
}
