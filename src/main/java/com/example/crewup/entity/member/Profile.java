package com.example.crewup.entity.member;

import java.util.ArrayList;
import java.util.List;

import com.example.crewup.dto.request.member.UpdateProfileRequest;
import com.example.crewup.entity.BaseTimeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Profile extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_id")
	private Long id;

	@Column(name = "bio")
	private String bio;

	@Column(name = "profile_image")
	private String profileImage;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProfileLink> profileLinks = new ArrayList<>();

	/**
	 * 기본 프로필 생성
	 * @return 기본 프로필
	 */
	public static Profile defaultProfile() {
		return Profile.builder()
			.bio("자기소개를 작성해주세요.")
			.build();
	}

	/**
	 * 프로필 링크 추가
	 * @param profileLink 프로필 링크
	 */
	public void addLink(ProfileLink profileLink) {
		this.profileLinks.add(profileLink);
	}

	/**
	 * 프로필 링크 삭제
	 * profile update시 사용
	 */
	public void clearLinks() {
		this.profileLinks.forEach(ProfileLink::removeProfile);
		this.profileLinks.clear();
	}

	/**
	 * 프로필 수정
	 * @param request 프로필 수정 요청
	 */
	public void update(UpdateProfileRequest request) {
		if (request.bio() != null)
			this.bio = request.bio();

		if (request.profileImage() != null)
			this.profileImage = request.profileImage();

	}

	//-- 연관관계 편의 메서드 --//
	public void setMember(Member member) {
		this.member = member;
	}
}
