package com.sopt.bbangzip.domain.user.entity;

import com.sopt.bbangzip.common.constants.entity.UserTableConstants;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = UserTableConstants.TABLE_USER)
@NoArgsConstructor(access= AccessLevel.PROTECTED)
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

    @Column(name = UserTableConstants.COLUMN_IS_ONBOARDING_COMPLETE, nullable = false)
    private Boolean isOnboardingComplete;

    @Column(name = UserTableConstants.COLUMN_USER_LEVEL, nullable = false)
    private Long userLevel;

    @Column(name = UserTableConstants.COLUMN_CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = UserTableConstants.COLUMN_UPDATED_AT)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserSubject> userSubjects;
}