package com.example.crewup.repository.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.crewup.dto.request.project.Filter;
import com.example.crewup.entity.project.Project;

public interface ProjectRepositoryCustom {

	Page<Project> findByProjectByFilter(Filter filter, Pageable pageable);
}
