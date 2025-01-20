package com.sopt.bbangzip.domain.badge.api.dto.response;

import java.util.List;

public record BadgeResponse(
        String badgeName,
        String badgeImage,
        List<String> hashTags
) {
}