package com.sopt.bbangzip.domain.badge;

import com.sopt.bbangzip.domain.user.entity.User;

public interface BadgeCondition {
    boolean isEligible(User user); // 조건 판단 메서드
}