package com.sopt.bbangzip.domain.badge.entity;

import com.sopt.bbangzip.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MassBakingBreadBadge implements Badge{
    @Override
    public BadgeCondition getCondition(){
        return user -> user.getTodayStudyCompleteCount() >= 3;
    }

    @Override
    public String getName() {
        return "빵 대량 생산";
    }

    @Override
    public int getReward() {
        return 50;
    }

    @Override
    public List<String> getHashTags() {
        return List.of("#사장님은 열일 중", "#오늘 빵 몇 개 구울 거야", "#백만 개");
    }

    @Override
    public String getImage() {
        return "https://github.com/user-attachments/assets/4ade00bf-4384-4d0c-99d1-a0bb6e1b9a94";
    }


    @Override
    public String getCategory() {
        return "미룬이 탈출"; // 뱃지 카테고리
    }

    @Override
    public Boolean isLocked(User user) {
        // 유저가 조건을 만족하면 잠금 해제
        return !getCondition().isEligible(user);
    }

    @Override
    public String getAchievementCondition(){
        return "24시간 내 '학습 완료'를 3회 완료한 경우";
    }
}