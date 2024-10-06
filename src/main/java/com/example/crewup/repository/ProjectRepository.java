package com.example.crewup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crewup.entity.member.Member;
import com.example.crewup.entity.project.Project;
import com.example.crewup.repository.querydsl.ProjectRepositoryCustom;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {

	List<Project> findByLeader(Member member);
}
