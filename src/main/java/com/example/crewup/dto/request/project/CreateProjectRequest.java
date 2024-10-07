package com.example.crewup.dto.request.project;

import java.util.List;

import com.example.crewup.entity.project.Category;
import com.example.crewup.entity.project.Position;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CreateProjectRequest(

	@NotBlank(message = "프로젝트 제목을 입력해주세요.")
	String title,

	@NotBlank(message = "프로젝트 설명을 입력해주세요.")
	String description,

	@NotEmpty(message = "프로젝트 포지션를 입력해주세요.")
	List<Position> needPosition,

	@NotEmpty(message = "프로젝트 카테고리를 입력해주세요.")
	List<Category> category
) {
}
