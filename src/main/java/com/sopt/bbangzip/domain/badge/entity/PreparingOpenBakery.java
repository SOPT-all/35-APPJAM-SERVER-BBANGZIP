package com.sopt.bbangzip.domain.badge.entity;

import com.sopt.bbangzip.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PreparingOpenBakery implements Badge {
    @Override
    public BadgeCondition getCondition() {
        return user -> user.getFirstCreateStudyCount() == 0; // Study 생성 횟수가 0인 경우
    }

    @Override
    public String getName() {
        return "빵집 오픈 준비 중";
    }

    @Override
    public int getReward() {
        return 50;
    }

    @Override
    public List<String> getHashTags() {
        return List.of("#오늘은 무슨 빵을 구울까", "#빵 냄새 솔솔", "#노릇노릇");
    }

    @Override
    public String getImage() {
        return "https://github.com/user-attachments/assets/4ade00bf-4384-4d0c-99d1-a0bb6e1b9a94";
    }

    @Override
    public String getCategory() {
        return "시작이 빵이다";
    }

    @Override
    public Boolean isLocked(User user) {
        return !getCondition().isEligible(user);
    }

    @Override
    public String getAchievementCondition() {
        return "최초로 '학습 완료'를 수행한 경우";
    }
}
