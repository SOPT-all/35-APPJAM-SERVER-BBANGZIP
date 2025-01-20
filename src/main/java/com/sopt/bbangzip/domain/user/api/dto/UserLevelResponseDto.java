package com.sopt.bbangzip.domain.user.api.dto;

public record UserLevelResponseDto(
        int level,       // 사용자 레벨
        int badgeCounts, // 뱃지 개수
        int reward,       // 보상 포인트
        int maxReward      // 현재 레벨의 최대 포인트
) {}
