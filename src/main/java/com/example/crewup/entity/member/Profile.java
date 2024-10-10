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
import lombok.NoArgsConstructor;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	public void update(UpdateProfileRequest request, List<ProfileLink> profileLink) {
		this.toBuilder()
			.bio(request.bio() != null ? request.bio() : this.bio)
			.profileImage(request.profileImage() != null ? request.profileImage() : this.profileImage)
			.profileLinks(profileLink != null ? profileLink : this.profileLinks)
			.build();
	}
}
