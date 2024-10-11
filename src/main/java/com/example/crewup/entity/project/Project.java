package com.example.crewup.entity.project;

import com.example.crewup.dto.request.project.UpdateProjectRequest;
import com.example.crewup.entity.BaseTimeEntity;
import com.example.crewup.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@Getter
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @ElementCollection(targetClass = Position.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "project_positions",
            joinColumns = @JoinColumn(name = "project_id")
    )
    private List<Position> needPosition;

    @ElementCollection(targetClass = Category.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "project_categories",
            joinColumns = @JoinColumn(name = "project_id")
    )
    @Column(name = "category")
    private List<Category> category;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProjectMember> projectMembers;

    /**
     * 프로젝트 멤버 추가
     * @param projectMember 추가할 프로젝트 멤버
     */
    public void addProjectMember(ProjectMember projectMember) {
        if (this.projectMembers == null)
            this.projectMembers = new ArrayList<>();

        this.projectMembers.add(projectMember);
    }

    /**
     * 리더 설정
     * @param member    leader 설정할 member
     */
    public void setLeader(Member member) {
        ProjectMember projectMember = ProjectMember.builder()
            .project(this)
            .member(member)
            .isLeader(true)
            .build();

        this.addProjectMember(projectMember);
    }

    /**
     * 리더 여부 확인
     * @param member    leader 여부를 확인할 member
     * @return boolean  member가 리더일때 true
     */
    public boolean isLeader(Member member) {
        return projectMembers.stream()
            .anyMatch(pm -> pm.getMember().getId().equals(member.getId()) && pm.isLeader());
    }

    /**
     * 프로젝트 수정
     * @param request   프로젝트 수정 요청
     */
    public void update(UpdateProjectRequest request) {
        if (request.title() != null)
            this.title = request.title();
        if (request.description() != null)
            this.description = request.description();
        if (request.needPosition() != null)
            this.needPosition = request.needPosition();
        if (request.category() != null)
            this.category = request.category();
    }

    /**
     * 프로젝트 삭제 (soft delete)
     */
    public void delete() {
        this.isDeleted = true;
    }

    /**
     * 프로젝트 완료 상태로 변경
     */
    public void complete() {
        this.status = Status.COMPLETE;
    }
}
