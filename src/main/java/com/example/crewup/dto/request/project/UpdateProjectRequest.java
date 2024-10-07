package com.example.crewup.dto.request.project;

import java.util.List;

import com.example.crewup.entity.project.Category;
import com.example.crewup.entity.project.Position;
import com.example.crewup.entity.project.Status;

public record UpdateProjectRequest(
	String title,
	String description,
	List<Position> needPosition,
	List<Category> category,
	Status status
) {
}
