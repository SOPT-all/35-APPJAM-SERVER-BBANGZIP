package com.sopt.bbangzip.domain.badge;

import com.sopt.bbangzip.domain.user.entity.User;

import java.util.List;

public interface Badge {
    BadgeCondition getCondition();
    String getName(); // 뱃지 이름 반환
    int getReward(); // 리워드 반환
    List<String> getHashTags(); // 뱃지 관련 해시태그 반환
    String getImage(); // 뱃지 이미지 반환
    String getCategory(); // 뱃지 카테고리 반환
    Boolean isLocked(User user); // 잠금 여부 반환
}