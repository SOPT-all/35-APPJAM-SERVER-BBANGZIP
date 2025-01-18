package com.sopt.bbangzip.domain.user.service;

import org.springframework.stereotype.Component;

@Component
public class UserLevelCalculator {

     // 레벨별 도달 포인트 설정
    private static final int LEVEL_1_POINT = 200;
    private static final int LEVEL_2_POINT = 300;
    private static final int LEVEL_3_POINT = 400;

    // 유저의 포인트를 기준으로 레벨 계산
    public int calculateLevel(int point) {
        int totalPoints = 0;
        int level = 1;

        // 레벨 1 달성 조건
        totalPoints += LEVEL_1_POINT;
        if (point <= totalPoints) {
            return level;
        }

        // 레벨 2 달성 조건
        level++;
        totalPoints += LEVEL_2_POINT;
        if (point <= totalPoints) {
            return level;
        }

        // 레벨 3 달성 조건
        level++;
        totalPoints += LEVEL_3_POINT;
        if (point <= totalPoints) {
            return level;
        }

        // 최대 레벨(레벨 3 이상) 설정
        return level + 1; // 레벨 4 이상일 경우를 위해 추가
    }
}
