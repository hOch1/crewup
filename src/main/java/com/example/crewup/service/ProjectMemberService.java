package com.example.crewup.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crewup.dto.response.project.ProjectMemberResponse;
import com.example.crewup.entity.member.Member;
import com.example.crewup.entity.project.Project;
import com.example.crewup.entity.project.ProjectMember;
import com.example.crewup.exception.CustomException;
import com.example.crewup.exception.ErrorCode;
import com.example.crewup.repository.ProjectMemberRepository;
import com.example.crewup.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectMemberService {

	private final ProjectMemberRepository projectMemberRepository;
	private final ProjectRepository projectRepository;

	@Transactional
	public boolean addMemberToProject(Long projectId, Member member) {
		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

		projectMemberRepository.save(ProjectMember.builder()
				.project(project)
				.member(member)
				.build());

		return true;
	}

	public List<ProjectMemberResponse> getMembersOfProject(Long projectId) {
		List<ProjectMember> projectMembers = projectMemberRepository.findByProjectId(projectId);

		return projectMembers.stream().map(ProjectMemberResponse::from).toList();
	}
}
