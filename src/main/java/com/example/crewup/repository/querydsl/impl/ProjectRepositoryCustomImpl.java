package com.example.crewup.repository.querydsl.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.crewup.dto.request.project.Filter;
import com.example.crewup.entity.member.Member;
import com.example.crewup.entity.project.Project;
import com.example.crewup.entity.project.QProject;
import com.example.crewup.entity.project.QProjectMember;
import com.example.crewup.entity.project.Status;
import com.example.crewup.repository.querydsl.ProjectRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

	private final JPQLQueryFactory queryFactory;

	@Override
	public Page<Project> findByProjectByFilter(Filter filter, Pageable pageable) {
		QProject project = QProject.project;

		List<Project> projects = queryFactory
			.selectFrom(project)
			.where(
				project.isDeleted.eq(false)
				.and(getFilterExpression(filter, project))
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(project.createdAt.desc())
			.fetch();

		return new PageImpl<>(projects, pageable, projects.size());
	}

	@Override
	public List<Project> findByProjectByMy(Member member) {
		QProject project = QProject.project;
		QProjectMember projectMember = QProjectMember.projectMember;

		return queryFactory
			.selectFrom(project)
			.join(project.projectMembers, projectMember)
			.where(
				project.isDeleted.eq(false)
				.and(projectMember.member.eq(member))
			)
			.fetch();
	}

	private BooleanExpression getFilterExpression(Filter filter, QProject project) {
		return switch (filter) {
			case ALL -> null;
			case RECRUITING -> project.status.eq(Status.RECRUITING);
			case COMPLETE -> project.status.eq(Status.COMPLETE);
		};
	}
}
