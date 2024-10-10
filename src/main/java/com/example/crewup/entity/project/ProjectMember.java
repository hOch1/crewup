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

    /**
     * 포지션 변경
     * @param request   변경할 포지션
     */
    public void update(UpdateProjectMemberRequest request) {
        this.position = request.position();
    }

    /**
     * 프로젝트 리더 변경
     * @param newLeader 새로운 리더
     */
    public void updateLeader(ProjectMember newLeader) {
        this.isLeader = false;
        newLeader.isLeader = true;
    }
}
