package com.sopt.bbangzip.domain.user.service;

import org.springframework.stereotype.Component;

@Component
public class UserLevelCalculator {

    private static final int LEVEL_1_POINT = 200;
    private static final int LEVEL_2_POINT = 300;
    private static final int LEVEL_3_POINT = 400;

    /**
     * 유저의 포인트를 기준으로 레벨 계산 및 현재 레벨의 최대 포인트 반환
     *
     * @param point 유저의 현재 포인트
     * @return LevelInfo 객체 (현재 레벨, 현재 레벨의 최대 포인트)
     */
    public LevelInfo calculateLevelInfo(int point) {
        // Lv 1 조건: 0 ~ 199
        if (point < LEVEL_1_POINT) {
            return new LevelInfo(1, LEVEL_1_POINT);
        }

        // Lv 2 조건: 200 ~ 299
        if (point < LEVEL_2_POINT) {
            return new LevelInfo(2, LEVEL_2_POINT);
        }

        // Lv 3 조건: 300 ~ 399
        if (point < LEVEL_3_POINT) {
            return new LevelInfo(3, LEVEL_3_POINT);
        }

        // Lv 4 이상: 400+
        return new LevelInfo(4, Integer.MAX_VALUE); // 최대 레벨은 제한이 없음
    }

    /**
     * 레벨과 해당 레벨의 최대 포인트를 함께 반환하는 클래스
     */
    public static class LevelInfo {
        private final int level;
        private final int maxReward;

        public LevelInfo(int level, int maxReward) {
            this.level = level;
            this.maxReward = maxReward;
        }

        public int getLevel() {
            return level;
        }

        public int getMaxReward() {
            return maxReward;
        }
    }
}
