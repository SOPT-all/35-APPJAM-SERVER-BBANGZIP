package com.sopt.bbangzip.domain.user.api.dto;

public record UserLevelResponseDto(
        int level,       // 사용자 레벨
        int badgeCounts, // 뱃지 개수
        int reward       // 보상 포인트
) {}
