package com.sopt.bbangzip.domain.badge;

import com.sopt.bbangzip.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PreparingOpeningBakery implements Badge{
    @Override
    public BadgeCondition getCondition() {
        // 맨 처음 학습을 '추가한' 필드가 아직 null 이면 해당 뱃지 획득 가능
        return user -> user.getHasPreparingOpeningBakery() == null;
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
        return List.of("#일일 빵집 오픈 알바생", "#가만히 있으면 빵도 못 간다");
    }

    @Override
    public String getImage() {
        return "https://example.com/images/4";
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
    public String getAchievementCondition() {
        return "최초로 '공부 할 내용'을 추가한 경우";
    }
}