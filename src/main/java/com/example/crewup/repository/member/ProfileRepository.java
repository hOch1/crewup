package com.example.crewup.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crewup.entity.member.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
