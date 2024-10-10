package com.example.crewup.entity.project;

import com.example.crewup.dto.request.projectMember.UpdateProjectMemberRequest;
import com.example.crewup.entity.BaseTimeEntity;
import com.example.crewup.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ProjectMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name = "is_leader", columnDefinition = "boolean default false")
    private boolean isLeader;

    public void update(UpdateProjectMemberRequest request) {
        this.position = request.position();
    }

    public void updateLeader(ProjectMember newLeader) {
        this.isLeader = false;
        newLeader.isLeader = true;
    }
}
