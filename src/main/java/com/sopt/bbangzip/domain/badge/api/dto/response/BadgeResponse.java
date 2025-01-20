package com.sopt.bbangzip.domain.badge.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record BadgeResponse(
        String badgeName,
        String badgeImage,
        List<String> hashTags
) {
}