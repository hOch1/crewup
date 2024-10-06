package com.example.crewup.dto.response.project;

import java.util.List;

import com.example.crewup.entity.project.Category;
import com.example.crewup.entity.project.Position;
import com.example.crewup.entity.project.Project;

import lombok.Builder;

@Builder
public record ProjectResponse(
	Long id,
	String title,
	String description,
	String status,
	Long leader_id,
	List<Category> category,
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
