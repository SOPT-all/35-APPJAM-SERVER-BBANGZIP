package com.sopt.bbangzip.domain.userBadge.entity;

import com.sopt.bbangzip.common.constants.entity.UserBadgeTableConstants;
import com.sopt.bbangzip.domain.badge.entity.Badge;
import com.sopt.bbangzip.domain.user.entity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = UserBadgeTableConstants.TABLE_USER_BADGE)
public class UserBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = UserBadgeTableConstants.COLUMN_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = UserBadgeTableConstants.COLUMN_USER_ID, nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = UserBadgeTableConstants.COLUMN_BADGE_ID, nullable = false)
    private Badge badge;

    @Column(name = UserBadgeTableConstants.COLUMN_CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
