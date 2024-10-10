package com.example.crewup.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crewup.dto.request.projectMember.ProjectMemberRequest;
import com.example.crewup.dto.request.projectMember.UpdateProjectMemberRequest;
import com.example.crewup.dto.response.project.ProjectMemberResponse;
import com.example.crewup.entity.member.Member;
import com.example.crewup.entity.project.Project;
import com.example.crewup.entity.project.ProjectMember;
import com.example.crewup.exception.CustomException;
import com.example.crewup.exception.ErrorCode;
import com.example.crewup.repository.project.ProjectMemberRepository;
import com.example.crewup.repository.project.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectMemberService {

	private final ProjectMemberRepository projectMemberRepository;
	private final ProjectRepository projectRepository;

	@Transactional
	public boolean addMemberToProject(Long projectId, Member member, ProjectMemberRequest request) {
		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

		projectMemberRepository.save(request.toEntity(project, member));

		return true;
	}

	public List<ProjectMemberResponse> getMembersOfProject(Long projectId) {
		List<ProjectMember> projectMembers = projectMemberRepository.findByProjectId(projectId);

		return projectMembers.stream().map(ProjectMemberResponse::from).toList();
	}

	@Transactional
	public Boolean updateMemberOfProject(Long projectId, Long memberId, Member member, UpdateProjectMemberRequest request) {
		ProjectMember projectMember = projectMemberRepository.findByProjectIdAndMemberId(projectId, memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.PROJECT_MEMBER_NOT_FOUND));

		ProjectMember leader = projectMemberRepository.findByProjectIdAndMemberId(projectId, member.getId())
			.orElseThrow(() -> new CustomException(ErrorCode.PROJECT_MEMBER_NOT_FOUND));

		if (!projectMember.getMember().equals(member) || !leader.isLeader())
			throw new CustomException(ErrorCode.ACCESS_DENIED);

		projectMember.update(request);
		return true;
	}

	@Transactional
	public Boolean updateLeaderOfProject(Long projectId, Long memberId, Member member) {
		ProjectMember projectMember = projectMemberRepository.findByProjectIdAndMemberId(projectId, memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.PROJECT_MEMBER_NOT_FOUND));

		ProjectMember leader = projectMemberRepository.findByProjectIdAndMemberId(projectId, member.getId())
			.orElseThrow(() -> new CustomException(ErrorCode.PROJECT_MEMBER_NOT_FOUND));

		if (!leader.isLeader())
			throw new CustomException(ErrorCode.ACCESS_DENIED);

		leader.updateLeader(projectMember);

		return true;
	}

	@Transactional
	public Boolean deleteMemberOfProject(Long projectId, Long memberId, Member member) {
		ProjectMember projectMember = projectMemberRepository.findByProjectIdAndMemberId(projectId, memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.PROJECT_MEMBER_NOT_FOUND));

		ProjectMember leader = projectMemberRepository.findByProjectIdAndMemberId(projectId, member.getId())
			.orElseThrow(() -> new CustomException(ErrorCode.PROJECT_MEMBER_NOT_FOUND));

		if (!projectMember.getMember().equals(member) || !leader.isLeader())
			throw new CustomException(ErrorCode.ACCESS_DENIED);

		projectMemberRepository.delete(projectMember);
		return true;
	}
}
