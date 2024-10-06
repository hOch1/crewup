package com.example.crewup.dto.request.project;

import java.util.List;

import com.example.crewup.entity.project.Category;
import com.example.crewup.entity.project.Position;

import jakarta.validation.constraints.NotBlank;

public record CreateProjectRequest(

	@NotBlank
	String title,

	@NotBlank
	String description,

	@NotBlank
	List<Position> needPosition,

	@NotBlank
	List<Category> category
) {
}
