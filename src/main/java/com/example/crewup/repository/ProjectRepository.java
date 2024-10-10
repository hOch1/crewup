package com.example.crewup.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crewup.entity.project.Project;
import com.example.crewup.repository.querydsl.ProjectRepositoryCustom;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {

}
