package com.sopt.bbangzip.domain.badge.api.dto.response;

import java.util.List;

public record BadgeResponse(
        String name,
        String image,
        List<String> hashTags
) {
}