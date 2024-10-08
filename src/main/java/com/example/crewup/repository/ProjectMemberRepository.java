package com.example.crewup.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crewup.entity.project.ProjectMember;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

	List<ProjectMember> findByProjectId(Long projectId);
	Optional<ProjectMember> findByProjectIdAndMemberId(Long projectId, Long memberId);

}
