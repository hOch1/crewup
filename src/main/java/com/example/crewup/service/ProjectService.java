package com.example.crewup.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crewup.dto.request.project.CreateProjectRequest;
import com.example.crewup.dto.request.project.Filter;
import com.example.crewup.dto.request.project.UpdateProjectRequest;
import com.example.crewup.dto.response.project.ProjectResponse;
import com.example.crewup.entity.member.Member;
import com.example.crewup.entity.project.Project;
import com.example.crewup.exception.CustomException;
import com.example.crewup.exception.ErrorCode;
import com.example.crewup.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

	private final ProjectRepository projectRepository;

	@Transactional
	public boolean createProject(CreateProjectRequest createProjectRequest, Member member) {
		projectRepository.save(createProjectRequest.toEntity(member));

		return true;
	}

	public ProjectResponse getProject(Long projectId) {
		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

		if (project.isDeleted())
			throw new CustomException(ErrorCode.PROJECT_IS_DELETED);

		return ProjectResponse.from(project);
	}

	public Page<ProjectResponse> getProjectsByFilter(Filter filter, Pageable pageable) {
		Page<Project> projects = projectRepository.findByProjectByFilter(filter, pageable);

		return projects.map(ProjectResponse::from);
	}

	@Transactional
	public boolean updateProject(Long projectId, UpdateProjectRequest updateProjectRequest, Member member) {
		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

		if (!project.isLeader(member))
			throw new CustomException(ErrorCode.ACCESS_DENIED);

		project.update(updateProjectRequest);

		return true;
	}

	@Transactional
	public boolean completeProject(Long projectId, Member member) {
		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

		if (!project.isLeader(member))
			throw new CustomException(ErrorCode.ACCESS_DENIED);

		project.complete();

		return true;
	}

	@Transactional
	public boolean deleteProject(Long projectId, Member member) {
		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

		if (!project.isLeader(member))
			throw new CustomException(ErrorCode.ACCESS_DENIED);

		project.delete();

		return true;
	}

	public List<ProjectResponse> getMyProjects(Member member) {
		List<Project> projects = projectRepository.findByProjectByMy(member);

		return projects.stream().map(ProjectResponse::from).toList();
	}
}
