package com.example.crewup.entity.project;

import com.example.crewup.dto.request.project.CreateProjectRequest;
import com.example.crewup.entity.BaseTimeEntity;
import com.example.crewup.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    private Member leader;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    public static Project of(CreateProjectRequest createProjectRequest, Member member){
        return Project.builder()
                .title(createProjectRequest.title())
                .description(createProjectRequest.description())
                .needPosition(createProjectRequest.needPosition())
                .category(createProjectRequest.category())
                .status(Status.RECRUITING)
                .leader(member)
                .build();
    }
}
