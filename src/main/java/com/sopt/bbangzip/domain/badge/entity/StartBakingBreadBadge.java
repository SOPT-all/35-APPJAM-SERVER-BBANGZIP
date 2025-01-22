package com.sopt.bbangzip.domain.badge.entity;

import com.sopt.bbangzip.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartBakingBreadBadge implements Badge{
    @Override
    public BadgeCondition getCondition(){
        // 맨 처음 학습을 완료한 필드가 아직 null 이면 해당 뱃지 획득 가능
        return user -> user.getFirstStudyCompletedAt() == null && user.getTodayStudyCompleteCount() > 0;
    }

    @Override
    public String getName() {
        return "빵 굽기 시작";
    }

    @Override
    public int getReward() {
        return 100;
    }

    @Override
    public List<String> getHashTags() {
        return List.of("#오늘은 무슨 빵을 구울까", "#빵 냄새 솔솔", "#노릇노릇");
    }

    @Override
    public String getImage() {
        return "https://github.com/user-attachments/assets/e3c755e2-8f5b-4b08-80e7-5da4c0ed111f";
    }

    @Override
    public String getCategory() {
        return "시작이 빵이다";
    }

    @Override
    public Boolean isLocked(User user) {
        // 유저가 조건을 만족하면 잠금 해제
        return !getCondition().isEligible(user);
    }

    @Override
    public String getAchievementCondition(){
        return "최초로 '학습완료'를 수행한 경우";
    }
}