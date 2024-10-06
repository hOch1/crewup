package com.example.crewup.entity.member;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.crewup.dto.request.auth.SignupRequest;
import com.example.crewup.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name" ,nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "provider")
    private String provider;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    public static Member of(SignupRequest signupRequest, PasswordEncoder passwordEncoder) {
        return Member.builder()
            .email(signupRequest.email())
            .name(signupRequest.name())
            .nickname(signupRequest.nickname())
            .password(passwordEncoder.encode(signupRequest.password()))
            .role(Role.ROLE_USER)
            .build();
    }

}
