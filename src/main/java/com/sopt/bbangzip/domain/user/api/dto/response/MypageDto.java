package com.sopt.bbangzip.domain.user.api.dto.response;

import java.util.List;

public record MypageDto(
        int level,       // 사용자 레벨
        int badgeCounts, // 뱃지 개수
        int reward,       // 보상 포인트
        int maxReward,      // 현재 레벨의 최대 포인트
        List<LevelDetail> levelDetails
) {
    public record LevelDetail(
            int level,               // 레벨
            String levelName,
            String levelDescription, // 레벨 설명
            String levelImage,       // 레벨 이미지 URL
            boolean levelIsLocked   // 잠금 여부
    )
    {}
}

