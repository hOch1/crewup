package com.example.crewup.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.crewup.entity.member.Member;
import com.example.crewup.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

		return new PrincipalDetails(member, null);
	}
}