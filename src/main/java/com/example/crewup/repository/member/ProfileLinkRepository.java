package com.example.crewup.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crewup.entity.member.ProfileLink;

public interface ProfileLinkRepository extends JpaRepository<ProfileLink, Long> {
}
