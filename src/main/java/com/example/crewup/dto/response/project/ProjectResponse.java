package com.example.crewup.dto.response.project;

import java.util.List;

import com.example.crewup.entity.project.Category;
import com.example.crewup.entity.project.Position;
import com.example.crewup.entity.project.Project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "프로젝트 응답")
public record ProjectResponse(
	@Schema(description = "프로젝트 ID")
	Long id,
	@Schema(description = "프로젝트 제목")
	String title,
	@Schema(description = "프로젝트 설명")
	String description,
	@Schema(description = "프로젝트 상태")
	String status,
	@Schema(description = "프로젝트 리더 ID")
	Long leader_id,
	@Schema(description = "프로젝트 카테고리")
	List<Category> category,
	@Schema(description = "필요한 포지션")
	List<Position> needPosition
) {
	public static ProjectResponse of(Project project) {
		return ProjectResponse.builder()
				.id(project.getId())
				.title(project.getTitle())
				.description(project.getDescription())
				.status(project.getStatus().name())
				.leader_id(project.getLeader().getId())
				.category(project.getCategory())
				.needPosition(project.getNeedPosition())
				.build();
	}
}
