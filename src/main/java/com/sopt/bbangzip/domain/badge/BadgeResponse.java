package com.sopt.bbangzip.domain.badge;

import java.util.List;

public record BadgeResponse(
        String name,
        String image,
        List<String> hashTags
) {
}