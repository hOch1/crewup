package com.example.crewup.repository.project.querydsl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.crewup.dto.request.project.Filter;
import com.example.crewup.entity.member.Member;
import com.example.crewup.entity.project.Project;

public interface ProjectRepositoryCustom {

	Page<Project> findByProjectByFilter(Filter filter, Pageable pageable);
	List<Project> findByProjectByMy(Member member);
}
