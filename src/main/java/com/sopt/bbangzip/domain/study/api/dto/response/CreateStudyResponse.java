package com.sopt.bbangzip.domain.study.api.dto.response;

import com.sopt.bbangzip.domain.badge.api.dto.response.BadgeResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateStudyResponse(
        List<BadgeResponse> badges
) {
}