package com.sopt.bbangzip.domain.badge.api.dto.response;

import java.util.List;

public record BadgeDetailResponse(
        String badgeName,
        String badgeImage,
        List<String> hashTags,
        String achievementCondition,
        int reward,
        boolean badgeIsLocked
) {
}
