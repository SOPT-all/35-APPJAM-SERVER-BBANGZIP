package com.sopt.bbangzip.domain.badge;

import java.util.List;

public interface Badge {
    BadgeCondition getCondition();
    String getName(); // 뱃지 이름 반환
    int getReward(); // 리워드 반환
    List<String> getHashTags(); // 뱃지 관련 해시태그 반환
    String getImage(); // 뱃지 이미지 반환
}