package com.example.crewup.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crewup.entity.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

	boolean existsByEmail(String email);
	boolean existsByNickname(String nickname);
}
