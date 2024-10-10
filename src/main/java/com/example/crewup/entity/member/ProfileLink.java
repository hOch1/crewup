package com.example.crewup.entity.member;

import com.example.crewup.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ProfileLink extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_link_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "link_type", nullable = false)
	private LinkType linkType;

	@Column(name = "link", nullable = false)
	private String link;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "profile_id")
	private Profile profile;

}
