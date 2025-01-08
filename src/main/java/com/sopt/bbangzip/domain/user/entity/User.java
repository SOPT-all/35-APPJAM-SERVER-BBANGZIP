package com.sopt.bbangzip.domain.user.entity;

import com.sopt.bbangzip.common.constants.entity.UserTableConstants;
import com.sopt.bbangzip.domain.badge.entity.Badge;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = UserTableConstants.TABLE_USER)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = UserTableConstants.COLUMN_ID)
    private Long id;

    @Column(name = UserTableConstants.COLUMN_PLATFORM_USER_ID, nullable = false)
    private Long platformUserId;

    @Column(name = UserTableConstants.COLUMN_PLATFORM, nullable = false)
    private String platform;

    @Column(name = UserTableConstants.COLUMN_NICKNAME)
    private String nickname;

    @Column(name = UserTableConstants.COLUMN_YEAR)
    private Integer year;

    @Column(name = UserTableConstants.COLUMN_SEMESTER)
    private Integer semester;

    @Column(name = UserTableConstants.COLUMN_IS_ONBOARDING_COMPLETE, nullable = false)
    private Boolean isOnboardingComplete;

    @Column(name = UserTableConstants.COLUMN_USER_LEVEL)
    private Long userLevel;

    @Column(name = UserTableConstants.COLUMN_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = UserTableConstants.COLUMN_UPDATED_AT)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Badge> badges;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects;


}
