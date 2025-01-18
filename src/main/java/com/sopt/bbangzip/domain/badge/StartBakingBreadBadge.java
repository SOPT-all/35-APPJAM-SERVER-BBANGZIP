package com.sopt.bbangzip.domain.badge;

import com.sopt.bbangzip.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartBakingBreadBadge implements Badge{
    @Override
    public BadgeCondition getCondition(){
        // 맨 처음 학습을 완료한 필드가 아직 null 이면 해당 뱃지 획득 가능
        return user -> user.getFirstStudyCompletedAt() == null;
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
        return "https://example.com/images/1";
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
}