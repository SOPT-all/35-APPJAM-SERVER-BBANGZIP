package com.sopt.bbangzip.domain.badge;

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
}