package com.sopt.bbangzip.domain.badge.entity;

import com.sopt.bbangzip.common.constants.entity.BadgeTableConstants;
import com.sopt.bbangzip.domain.userBadge.entity.UserBadge;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = BadgeTableConstants.TABLE_BADGE)
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = BadgeTableConstants.COLUMN_ID)
    private Long id;

    @Column(name = BadgeTableConstants.COLUMN_BADGE_NAME, nullable = false)
    private String badgeName;

    @Column(name = BadgeTableConstants.COLUMN_REWARD, nullable = false)
    private Long reward;

    @Column(name = BadgeTableConstants.COLUMN_HASH_TAGS, nullable = false)
    private String hashTags;

    @Column(name = BadgeTableConstants.COLUMN_BADGE_IMAGE, nullable = false)
    private String badgeImage;

    @Column(name = BadgeTableConstants.COLUMN_ACHIEVEMENT_CONDITION, nullable = false)
    private String achievementCondition;


    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserBadge> userBadges;
}
